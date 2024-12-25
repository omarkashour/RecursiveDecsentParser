import com.sun.source.tree.ReturnTree;

import javax.naming.NamingException;
import javax.print.DocFlavor;
import java.awt.image.ByteLookupTable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    static final String filePath = "./testing.txt";
    static File file;
    static Scanner sc;
    static Tokenizer tokenizer;
    static String file_content = "";
    static Token currentToken;

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        tokenizer = new Tokenizer(file_content);
        tokenizer.tokenize();
        for (Token t : tokenizer.tokens) {
            System.out.println(t);
        }
        nextToken();
        lib_decl();
        declarations();
        while (!currentToken.getType().equals(Token.NEWB)) {
            function_decl();
        }
        block();
        if (!currentToken.getType().equals(Token.EXIT)) {
            error("exit");
        }
        System.out.println("Parsing completed successfully");

    }

    public static void nextToken() {
        currentToken = tokenizer.getNextToken();
    }

    private static void readFile() throws FileNotFoundException {
        file = new File(filePath);
        sc = new Scanner(file);
        while (sc.hasNext()) {
            file_content += sc.nextLine() + "\n";
        }
    }

    public static void error(String expected) {
        System.out.println("Error: expected token '" + expected + "' but parsed '" + currentToken.getContent() + "'" + " at line " + currentToken.lineNum);
        System.exit(1);

    }

    public static void lib_decl() {
        if (currentToken.getType().equals(Token.HASHTAG)) {
            nextToken();
            if (currentToken.getType().equals(Token.INCLUDE)) {
                nextToken();
                if (currentToken.getType().equals(Token.LESS)) {
                    nextToken();
                    file_name();
                    if (currentToken.getType().equals(Token.GREATER)) {
                        nextToken();
                        if (currentToken.getType().equals(Token.SEMICOLON)) {
                            nextToken();
                            lib_decl();
                        }
                    } else {
                        error(">");
                    }
                } else {
                    error("<");
                }
            }
        } else if (!currentToken.getType().equals(Token.CONST) && !currentToken.getType().equals(Token.VAR) && !currentToken.getType().equals(Token.FUNCTION)) {
            error("# or const or var or function");
        }
    }

    private static void file_name() {
        while (currentToken.getType().equals(Token.NAME) || currentToken.getType().equals(Token.PERIOD)) {
            if (currentToken.getType().equals(Token.GREATER)) {
                break;
            }
            nextToken();
        }
    }

    public static void declarations() {
        const_decl();
        var_decl();
    }

    public static void const_decl() { // this is correct but its not working yet because "value()" is not implemented yet
        if(currentToken.getType().equals(Token.CONST)) {
            nextToken();
            data_type();
            const_name();
            if(currentToken.getType().equals(Token.EQUAL)){
                nextToken();
                value();
                if(currentToken.getType().equals(Token.SEMICOLON)){
                    nextToken();
                    const_decl();
                }
            }

        }else if(!currentToken.getType().equals(Token.VAR) && !currentToken.getType().equals(Token.FUNCTION) && !currentToken.getType().equals(Token.NEWB)) {
            error("var or function or newb");
        }
    }

    private static void const_name() {
        name();
    }
    private static void function_name() {
        name();
    }

    private static void var_name() {
        name();
    }


    public static void var_decl() {
        if(currentToken.getType().equals(Token.VAR)) {
            nextToken();
            data_type();
            name_list();
            if(currentToken.getType().equals(Token.SEMICOLON)){
                nextToken();
                var_decl();

            }
            else{
                error(";");
            }
        }
        else if(!currentToken.getType().equals(Token.FUNCTION) && !currentToken.getType().equals(Token.NEWB)) {
            error("var or function or newb");
        }
    }

    public static void name_list() {
        if (currentToken.getType().equals(Token.NAME)) {
            nextToken();
            more_names();
        }
        else if (!currentToken.getType().equals(Token.SEMICOLON)) {
            error(";");
        }
    }

    public static void more_names() {
        if (currentToken.getType().equals(Token.COMMA)) {
            nextToken();
            name_list();
        }
        else if (!currentToken.getType().equals(Token.SEMICOLON)) {
            error(";");
        }
    }

    public static void data_type() {
        if (currentToken.getType().equals(Token.INT)) {
            nextToken();
        }
        else if (currentToken.getType().equals(Token.FLOAT)) {
            nextToken();
        }
        else if (currentToken.getType().equals(Token.CHAR)){
            nextToken();
        }
        else {
            error("int or float or char");
        }
    }

    public static void function_decl() {
        function_heading();
        declarations();
        block();
        if(!currentToken.getType().equals(Token.SEMICOLON)){
            error(";");
        }
        nextToken();
    }

    public static void function_heading() {
        if(currentToken.getType().equals(Token.FUNCTION)) {
            nextToken();
            if (currentToken.getType().equals(Token.NAME)) {
                function_name();
                if(currentToken.getType().equals(Token.SEMICOLON)){
                    nextToken();
                }
                else  {
                    error(";");
                }
            }else{
                error("function_name");
            }
        }else {
            error("function");
        }
    }

    public static void block() {
        if (currentToken.getType().equals(Token.NEWB)){
            nextToken();
            stmt_list();
            if(currentToken.getType().equals(Token.ENDB)){
                nextToken();
            }
            else{
                error("endb");
            }
        }else {
            error("newb");
        }
    }

    public static void stmt_list() {
        if(currentToken.getType().equals(Token.ENDB) || currentToken.getType().equals(Token.UNTIL)) {
            return;
        }
        statement();
        if(currentToken.getType().equals(Token.SEMICOLON)){
            nextToken();
            stmt_list();
        }
    }

    public static void statement() {
        if (currentToken.getType().equals(Token.NAME)) {
            ass_stmt();
        }
        else if (currentToken.getType().equals(Token.CIN) || currentToken.getType().equals(Token.COUT)) {
            inout_stmt();
        }else if (currentToken.getType().equals(Token.IF)) {
            if_stmt();
        }else if (currentToken.getType().equals(Token.WHILE)) {
            while_stmt();
        }else if (currentToken.getType().equals(Token.NEWB)){
            block();
        }else if(currentToken.getType().equals(Token.REPEAT)){
            repeat_stmt();
        }else if (currentToken.getType().equals(Token.CALL)) {
            function_call_stmt();
        }else {
            error("name or cin or if or while or newb or repeat or call");
        }
    }

    public static void ass_stmt() {
        var_name();
        if(currentToken.getType().equals(Token.ASSIGN)){
            nextToken();
            exp();
        }
        else{
            error(":=");
        }
    }

    public static void exp() {
        term();
        exp_prime();
    }

    public static void exp_prime() {
        if(currentToken.getType().equals(Token.RPAREN) || currentToken.getType().equals(Token.SEMICOLON) || currentToken.getType().equals(Token.ELSE)) {
            return;
        }
        add_oper();
        term();
        exp_prime();
    }

    public static void term() {
        factor();
        term_prime();
    }


    public static void term_prime() {
        if(currentToken.getType().equals(Token.MINUS) || currentToken.getType().equals(Token.PLUS) || currentToken.getType().equals(Token.RPAREN) || currentToken.getType().equals(Token.SEMICOLON)) {
            return;
        }
        mul_oper();
        factor();
        term_prime();
    }

    public static void factor() {
        if(currentToken.getType().equals(Token.LPAREN)) {
            nextToken();
            exp();
            if(currentToken.getType().equals(Token.RPAREN)) {
                nextToken();

            }
            else{
                error(")");
            }
        }else if (currentToken.getType().equals(Token.QUOTE)) {
            nextToken();
            var_name(); // same as const_name
            if(currentToken.getType().equals(Token.QUOTE)) {
                nextToken();
            }
            else{
                error("\"");
            }
        } else if (currentToken.getType().equals(Token.INT) || currentToken.getType().equals(Token.REALVALUE)) {
            value();
        }
        else{
            error("( or \" or digit");
        }
    }

    public static void add_oper() {
        if(currentToken.getType().equals(Token.PLUS)){
            nextToken();
        } else if(currentToken.getType().equals(Token.MINUS)){
            nextToken();
        }else {
            error("+ or -");
        }
    }

    public static void mul_oper() {
        if (currentToken.getType().equals(Token.MULTIPLY)){
            nextToken();
        }else if (currentToken.getType().equals(Token.DIVIDE)) {
            nextToken();
        }else if (currentToken.getType().equals(Token.MOD)) {
            nextToken();
        }else if (currentToken.getType().equals(Token.DIV)){
            nextToken();
        }else{
            error("* or / or mod or div");
        }
    }

    public static void inout_stmt() {
            if(currentToken.getType().equals(Token.CIN)){
                nextToken();
                if(currentToken.getType().equals(Token.DOUBLE_GREATER)){
                    nextToken();
                    var_name();
                }else{
                    error(">>");
                }
            } else if (currentToken.getType().equals(Token.COUT)) {
                nextToken();
                if(currentToken.getType().equals(Token.DOUBLE_LESS)){
                    nextToken();
                    name_value();
                }
                else{
                    error("<<");
                }
            }else{
                error("Cin or Cout");
            }
    }

    public static void if_stmt() {
        if(currentToken.getType().equals(Token.IF)) {
            nextToken();
            if(currentToken.getType().equals(Token.LPAREN)) {
                nextToken();
                condition();
                if(currentToken.getType().equals(Token.RPAREN)) {
                    nextToken();
                    statement();
                    else_part();
                }else{
                    error(")");
                }
            }else{
                error("(");
            }
        }else{
            error("if");
        }
    }

    public static void else_part() {
        if(currentToken.getType().equals(Token.SEMICOLON)) {
            return;
        }
        if(currentToken.getType().equals(Token.ELSE)) {
            nextToken();
            statement();
        }else{
            error("else");
        }
    }

    public static void while_stmt() {
        if(currentToken.getType().equals(Token.WHILE)) {
            nextToken();
            if(currentToken.getType().equals(Token.LPAREN)) {
                nextToken();
                condition();
                if(currentToken.getType().equals(Token.RPAREN)) {
                    nextToken();
                    if(currentToken.getType().equals(Token.NEWB)) {
                        nextToken();
                        stmt_list();
                        if(currentToken.getType().equals(Token.ENDB)) {
                            nextToken();
                        }else{
                            error("endb");
                        }
                    }else{
                        error("newb");
                    }
                }else{
                    error(")");
                }
            }else{
                error("(");
            }
        }else{
            error("while");
        }
    }

    public static void repeat_stmt() {
        if(currentToken.getType().equals(Token.REPEAT)) {
            nextToken();
            stmt_list();
            if(currentToken.getType().equals(Token.UNTIL)) {
                nextToken();
                condition();
            }else {
                error("until");
            }
        }else{
            error("repeat");
        }
    }

    public static void condition() {
        name_value();
        relational_oper();
        name_value();
    }

    public static void name_value() {
        if(currentToken.getType().equals(Token.QUOTE)) {
            nextToken();
            var_name();
            if(currentToken.getType().equals(Token.QUOTE)) {
                nextToken();
            }else{
                error("\"");
            }
        }else if(currentToken.getType().equals(Token.INT) || currentToken.getType().equals(Token.REALVALUE)) {
            nextToken();
            value();
        }else{
            error("\" or digit");
        }
    }

    public static void relational_oper() {
        if(currentToken.getType().equals(Token.EQUAL)) {
            nextToken();
        }else if(currentToken.getType().equals(Token.NOT_EQUAL)) {
            nextToken();
        }else if(currentToken.getType().equals(Token.LESS)) {
            nextToken();
        }else if(currentToken.getType().equals(Token.GREATER)) {
            nextToken();
        }else if (currentToken.getType().equals(Token.LESS_EQUAL)) {
            nextToken();
        }else if(currentToken.getType().equals(Token.GREATER_EQUAL)) {
            nextToken();
        }else{
            error("= or =! or < or =< or > or =>");
        }
    }

    public static void name() {
        if(currentToken.getType().equals(Token.NAME)) {
            while(currentToken.getType().equals(Token.NAME) || currentToken.getType().equals(Token.INT)) {
                nextToken();
            }
        }else{
            error("name");
        }
    }

    public static void value() {
        if(currentToken.getType().equals(Token.INT)) {
            integer_value();
        }
        else if(currentToken.getType().equals(Token.REALVALUE)) {
            real_value();
        }else{
            error("not a digit");
        }
    }

    public static void integer_value() {
            if(currentToken.getType().equals(Token.INT)) {
                nextToken();
            }
            else{
                error("not a digit");
            }
    }

    public static void real_value() {
        if (currentToken.getType().equals(Token.REALVALUE)) {
            nextToken();
        }else{
            error("not a digit");
        }
    }

    public static void function_call_stmt() {
        if(currentToken.getType().equals(Token.CALL)) {
            nextToken();
            function_name();
        }else{
            error("call");
        }
    }
}
