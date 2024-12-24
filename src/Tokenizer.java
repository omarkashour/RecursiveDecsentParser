
import java.util.ArrayList;
import java.util.List;


public class Tokenizer {

    String text;
    int index;
    int lineNum;
    List<Token> tokens = new ArrayList<>();
    public Tokenizer(String text) {
        this.text = text;
        this.index = 0;
        this.lineNum = 1;
    }

    int currTokenIndex = 0;
    public  Token getNextToken() {
        return this.tokens.get(currTokenIndex++);
    }

    public List<Token> tokenize() {

        while (index < text.length()) {
            char curChar = text.charAt(index);

            if (Character.isWhitespace(curChar)) {
                if (curChar == '\n') {
                    lineNum++;
                }
                index++;
                continue;
            }
            if (Character.isLetter(curChar)) {
                String ident = readName();
                String type = getKeywordType(ident);
                tokens.add(new Token(type != null ? type : Token.NAME, ident, lineNum));
                continue;
            }
            if (Character.isDigit(curChar)) {
                String num = readNumber();
                if (num.contains(".")) {
                    tokens.add(new Token(Token.REALVALUE, num, lineNum));
                } else {
                    tokens.add(new Token(Token.INT, num, lineNum));
                }
                continue;
            }
            switch (curChar) {
                case '=':
                    index++;
                    if (index < text.length() && text.charAt(index) == '<') {
                        index++;
                        tokens.add(new Token(Token.LESS_EQUAL, "=<", lineNum));
                    } else if (index < text.length() && text.charAt(index) == '>') {
                        index++;
                        tokens.add(new Token(Token.GREATER_EQUAL, "=<", lineNum));
                    } else if (index < text.length() && text.charAt(index) == '!') {
                        index++;
                        tokens.add(new Token(Token.NOT_EQUAL, "=!", lineNum));
                    } else {
                        tokens.add(new Token(Token.EQUAL, "=", lineNum));
                    }
                    break;
                case ':':
                    index++;
                    if (index < text.length() && text.charAt(index) == '=') {
                        index++;
                        tokens.add(new Token(Token.ASSIGN, ":=", lineNum));
                    } else {
                        tokens.add(new Token(Token.COLON, ":", lineNum));
                    }
                    break;
                case '+':
                    index++;
                    tokens.add(new Token(Token.PLUS, "+", lineNum));
                    break;
                case '-':
                    index++;
                    tokens.add(new Token(Token.MINUS, "-", lineNum));
                    break;
                case '*':
                    index++;
                    tokens.add(new Token(Token.MULTIPLY, "*", lineNum));
                    break;
                case '#':
                    index++;
                    tokens.add(new Token(Token.HASHTAG, "#", lineNum));
                    break;
                case '/':
                    index++;
                    tokens.add(new Token(Token.DIVIDE, "/", lineNum));
                    break;
                case '<':
                    index++;
                    if (index < text.length() && text.charAt(index) == '<') {
                        index++;
                        tokens.add(new Token(Token.DOUBLE_LESS, "<<", lineNum));
                    } else {
                        tokens.add(new Token(Token.LESS, "<", lineNum));
                    }
                    break;
                case '>':
                    index++;
                    if (index < text.length() && text.charAt(index) == '>') {
                        index++;
                        tokens.add(new Token(Token.DOUBLE_GREATER, ">>", lineNum));
                    } else {
                        tokens.add(new Token(Token.GREATER, ">", lineNum));
                    }
                    break;
                case ';':
                    index++;
                    tokens.add(new Token(Token.SEMICOLON, ";", lineNum));
                    break;
                case ',':
                    index++;
                    tokens.add(new Token(Token.COMMA, ",", lineNum));
                    break;
                case '(':
                    index++;
                    tokens.add(new Token(Token.LPAREN, "(", lineNum));
                    break;
                case ')':
                    index++;
                    tokens.add(new Token(Token.RPAREN, ")", lineNum));
                    break;
                case '.':
                    index++;
                    tokens.add(new Token(Token.PERIOD, ".", lineNum));
                    break;
                default:
                    index++;
                    tokens.add(new Token(Token.INVALID, String.valueOf(curChar), lineNum));
                    break;
            }
        }
        tokens.add(new Token(Token.EOF, "", lineNum));
        return tokens;

    }

    private String readName() {
        StringBuilder str = new StringBuilder();
        if (index < text.length() && Character.isLetter(text.charAt(index))) {
            str.append(text.charAt(index++));
        } else {
            return "";
        }
        while (index < text.length() && Character.isLetterOrDigit(text.charAt(index))) {
            str.append(text.charAt(index++));
        }
        return str.toString();
    }

    private String readNumber() {
        StringBuilder str = new StringBuilder();
        while (index < text.length() && Character.isDigit(text.charAt(index))) {
            str.append(text.charAt(index++));
        }
        if (index < text.length() && text.charAt(index) == '.') {
            str.append(text.charAt(index++));
            while (index < text.length() && Character.isDigit(text.charAt(index))) {
                str.append(text.charAt(index++));
            }
        }
        return str.toString();
    }

    private String getKeywordType(String ident) {
        switch (ident) {
            case "mod":
                return Token.MOD;
            case "div":
                return Token.DIV;
            case "int":
                return Token.INT;
            case "char":
                return Token.CHAR;
            case "float":
                return Token.FLOAT;
            case "exit":
                return Token.EXIT;
            case "const":
                return Token.CONST;
            case "var":
                return Token.VAR;
            case "function":
                return Token.FUNCTION;
            case "if":
                return Token.IF;
            case "else":
                return Token.ELSE;
            case "while":
                return Token.WHILE;
            case "repeat":
                return Token.REPEAT;
            case "until":
                return Token.UNTIL;
            case "newb":
                return Token.NEWB;
            case "endb":
                return Token.ENDB;
            case "cout":
                return Token.COUT;
            case "cin":
                return Token.CIN;
            case "call":
                return Token.CALL;
            case "include":
                return Token.INCLUDE;
            default:
                return null;
        }
    }


}
