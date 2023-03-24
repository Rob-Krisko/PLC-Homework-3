import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PLC1 {
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
