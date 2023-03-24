import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PLC2 {
    public static void main(String[] args) {
        // Create a Scanner object to read user input.
        Scanner scanner = new Scanner(System.in);
        // Prompt the user to enter the input file name.
        System.out.print("Enter the input file name: ");
        // Read the file name from the user input.
        String inputFileName = scanner.nextLine();
        
        // Instantiate the Lexer class to tokenize the input file.
        Lexer lexer = new Lexer();

        try {
            // Tokenize the input file and store the tokens in a list.
            List<Token> tokens = lexer.tokenizeFile(inputFileName);
            // Print the tokens in the list.
            System.out.println("Tokens:");
            tokens.forEach(System.out::println);
        } catch (IOException e) {
            // Print an error message if there is an issue reading the input file.
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
}

class Token {
    // Enum declaration for all possible token types that the lexer will recognize.
    public enum TokenType {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, MODULO,
        LPAREN, RPAREN, ASSIGNMENT, EQUALS, LESS_THAN, LESS_THAN_OR_EQUAL_TO,
        GREATER_THAN, GREATER_THAN_OR_EQUAL_TO, LOGICAL_AND, LOGICAL_OR,
        IDENTIFIER, INTEGER_LITERAL, FLOATING_POINT_LITERAL
    }

    // Variable declarations to store the token type and its value.
    private TokenType type;
    private String value;

    // Constructor that initializes a Token object with a specified type and value.
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    // Getter method for the token type.
    public TokenType getType() {
        return type;
    }

    // Getter method for the token value.
    public String getValue() {
        return value;
    }

    // Override the toString() method to return a formatted string with token information.
    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value='" + value + '\'' + '}';
    }
}


class Lexer {
    // Define the regex pattern for token matching.
    private static final String TOKEN_PATTERN =
            "\\s*(=>|<=|<|>|==|&&|\\|\\||\\+|-|\\*|/|%|=|\\(|\\)|[a-zA-Z_][a-zA-Z0-9_]*|[0-9]+(?:\\.[0-9]+)?)\\s*";

    // Tokenize the contents of a file, returning a list of tokens.
    public List<Token> tokenizeFile(String fileName) throws IOException {
        List<Token> tokens = new ArrayList<>();
        // Read the file line by line using a BufferedReader.
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tokens.addAll(tokenizeLine(line));
            }
        }
        return tokens;
    }

    // Tokenize a single line, returning a list of tokens.
    private List<Token> tokenizeLine(String line) {
        List<Token> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile(TOKEN_PATTERN);
        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            String value = matcher.group().trim();
            Token.TokenType type = determineTokenType(value);
            tokens.add(new Token(type, value));
        }

        return tokens;
    }

    // Determine the token type based on its value.
    private Token.TokenType determineTokenType(String value) {
        switch (value) {
            case "+": return Token.TokenType.ADDITION;
            case "-": return Token.TokenType.SUBTRACTION;
            case "*": return Token.TokenType.MULTIPLICATION;
            case "/": return Token.TokenType.DIVISION;
            case "%": return Token.TokenType.MODULO;
            case "(": return Token.TokenType.LPAREN;
            case ")": return Token.TokenType.RPAREN;
            case "=": return Token.TokenType.ASSIGNMENT;
            case "==": return Token.TokenType.EQUALS;
            case "<": return Token.TokenType.LESS_THAN;
            case "<=": return Token.TokenType.LESS_THAN_OR_EQUAL_TO;
            case ">": return Token.TokenType.GREATER_THAN;
            case ">=": return Token.TokenType.GREATER_THAN_OR_EQUAL_TO;
            case "&&": return Token.TokenType.LOGICAL_AND;
            case "||": return Token.TokenType.LOGICAL_OR;
            default:
                if (value.matches("^[0-9]+$")) {
                    return Token.TokenType.INTEGER_LITERAL;
                } else if (value.matches("^[0-9]+\\.[0-9]+$")) {
                    return Token.TokenType.FLOATING_POINT_LITERAL;
                } else if (value.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
                    return Token.TokenType.IDENTIFIER;
                } else {
                    throw new IllegalArgumentException("Invalid token: " + value);
                }
        }
    }
}


