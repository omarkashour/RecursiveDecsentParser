

public class Token {

    public static final String INCLUDE = "INCLUDE";
    public static final String EXIT = "EXIT";
    public static final String CONST = "CONST";
    public static final String VAR = "VAR";
    public static final String IF = "IF";
    public static final String ELSE = "ELSE";
    public static final String WHILE = "WHILE";
    public static final String UNTIL = "UNTIL";
    public static final String REPEAT = "REPEAT";
    public static final String CIN = "CIN";
    public static final String COUT = "COUT";
    public static final String PLUS = "PLUS";
    public static final String MINUS = "MINUS";
    public static final String MULTIPLY = "MULTIPLY";
    public static final String DIVIDE = "DIVIDE";
    public static final String MOD = "MOD";
    public static final String DIV = "DIV";
    public static final String EQUAL = "EQUAL";
    public static final String NOT_EQUAL = "NOT_EQUAL";
    public static final String LESS = "LESS";
    public static final String LESS_EQUAL = "LESS_EQUAL";
    public static final String GREATER = "GREATER";
    public static final String GREATER_EQUAL = "GREATER_EQUAL";
    public static final String SEMICOLON = "SEMICOLON";
    public static final String COMMA = "COMMA";
    public static final String COLON = "COLON";
    public static final String LPAREN = "LPAREN";
    public static final String RPAREN = "RPAREN";
    public static final String DOUBLE_LESS = "DOUBLE_LESS";
    public static final String DOUBLE_GREATER= "DOUBLE_GREATER";
    public static final String FLOAT = "FLOAT";
    public static final String INT = "INT";
    public static final String PERIOD = "PERIOD";
    public static final String REALVALUE = "REAL-VALUE";
    public static final String CHAR = "CHAR";
    public static final String NEWB = "NEWB";
    public static final String ENDB = "ENDB";
    public static final String CALL = "CALL";
    public static final String FUNCTION = "FUNCTION";
    public static final String ASSIGN = "ASSIGN";
    public static final String NAME = "NAME";
    public static final String EOF = "EOF";
    public static final String INVALID = "INVALID";

    String content;
    String type;
    int lineNum;

    public Token(String type, String content, int lineNum) {
        this.content = content;
        this.type = type;
        this.lineNum = lineNum;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getLine() {
        return lineNum;
    }

    @Override
    public String toString() {
        return "Token{" +
                "content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", lineNum=" + lineNum +
                '}';
    }
}
