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
        while (currentToken.getType().equals(Token.NAME) ) {
            nextToken();
        }
    }
    private static void function_name() {
        while (currentToken.getType().equals(Token.NAME) ) {
            nextToken();
        }
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
        else if (currentToken.getType().equals(Token.SEMICOLON)) {
            error(";");
        }
    }

    public static void more_names() {
        if (currentToken.getType().equals(Token.COLON)) {
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
        else if (currentToken.getType().equals(Token.CIN)) {
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

    }

    public static void exp() {

    }

    public static void exp_prime() {

    }

    public static void term() {

    }

    public static void term_prime() {

    }

    public static void factor() {

    }

    public static void add_oper() {

    }

    public static void mul_oper() {

    }

    public static void inout_stmt() {

    }

    public static void if_stmt() {

    }

    public static void else_part() {

    }

    public static void while_stmt() {

    }

    public static void repeat_stmt() {

    }

    public static void condition() {

    }

    public static void name_value() {

    }

    public static void relational_oper() {

    }

    public static void name() {

    }

    public static void value() {
        if(currentToken.getType().equals(Token.INT)) {
            integer_value();
        }
        else if(currentToken.getType().equals(Token.REALVALUE)) {
            real_value();
        }
    }

    public static void integer_value() {
            if(currentToken.getType().equals(Token.INT)) {
                nextToken();
            }
    }

    public static void real_value() {
        if (currentToken.getType().equals(Token.REALVALUE)) {
            nextToken();
        }
    }

    public static void function_call_stmt() {

    }
}
