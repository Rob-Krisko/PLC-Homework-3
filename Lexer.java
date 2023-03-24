import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
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

