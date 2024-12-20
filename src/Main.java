import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    static final String filePath = "./testing.txt";
    static File file;
    static Scanner sc;
    static String token = "";
    public static void main(String[] args) throws FileNotFoundException {
        file = new File(filePath);
        sc = new Scanner(file);
        while(sc.hasNext()){
            token = getToken();
            lib_decl();
            declarations();
            function_decl();
            if(!token.equals("exit")){
                System.out.println("ERROR AT LINE :" ); // add the error line and token
            }

        }
    }
    public static String getToken(){
        return sc.next();
    }
    public static void lib_decl(){

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
