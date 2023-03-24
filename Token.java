public class Token {
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
