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
        for(Token t : tokenizer.tokens){
            System.out.println(t);
        }
        nextToken();
        lib_decl();
        declarations();
        while (!currentToken.equals(Token.NEWB)) {
            function_decl();
        }
        block();
        if(!currentToken.equals(Token.EXIT)){
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
        while(sc.hasNext()){
            file_content += sc.nextLine() +"\n";
        }
    }
    public static void error(String expected){
        System.out.println("Error: expected token '" + expected + "' but parsed '" + currentToken.getContent() +"'" + " at line " + currentToken.lineNum);
        System.exit(1);

    }

    public static void lib_decl(){
        if(currentToken.getType().equals(Token.INCLUDE)){
            nextToken();
            if(currentToken.getType().equals(Token.LESS)){
                nextToken();
                while(currentToken.getType().equals(Token.NAME) || currentToken.getType().equals(Token.PERIOD)) {
                    if(currentToken.getType().equals(Token.GREATER)){
                        break;
                    }
                    nextToken();
                }
                if(currentToken.getType().equals(Token.GREATER)){
                    nextToken();
                    if(currentToken.getType().equals(Token.SEMICOLON)){
                        nextToken();
                        lib_decl();
                    }
                    else{
                        return;
                    }
                }
                else{
                    error(">");
                }
            }else{
                error("<");
            }
        }else if(!currentToken.getType().equals(Token.CONST) || !currentToken.getType().equals(Token.VAR)){
            error("include");
        }
    }

    public static void declarations(){

    }
    public static void const_decl(){

    }
    public static void var_decl(){

    }
    public static void name_list(){

    }
    public static void more_names(){

    }
    public static void data_type(){

    }
    public static void function_decl(){

    }
    public static void function_heading(){

    }
    public static void block(){

    }
    public static void stmt_list(){

    }
    public static void statement(){

    }
    public static void ass_stmt(){

    }
    public static void exp(){

    }
    public static void exp_prime(){

    }
    public static void term(){

    }
    public static void term_prime(){

    }
    public static void factor(){

    }
    public static void add_oper(){

    }
    public static void mul_oper(){

    }
    public static void inout_stmt(){

    }
    public static void if_stmt(){

    }
    public static void else_part(){

    }
    public static void while_stmt(){

    }
    public static void repeat_stmt(){

    }
    public static void condition(){

    }
    public static void name_value(){

    }
    public static void relational_oper(){

    }
    public static void name(){

    }
    public static void value(){

    }
    public static void integer_value(){

    }
    public static void real_value(){

    }
    public static void function_call_stmt(){

    }
}
