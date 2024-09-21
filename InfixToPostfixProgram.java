import javax.swing.JOptionPane;
import java.lang.Math;

public class InfixToPostfixProgram {
    
    // Checks if the character is a digit (0-9).
    private static boolean isDigit(char character) {
        return character >= '0' && character <= '9';
    }
    
    // Checks if the character is a valid operator.
    private static boolean isOperator(char character) {
        char[] operators = {'+', '-', '/', '*', '^'};
        for (char c : operators) {
            if (c == character) {
                return true; // Return true if character matches an operator.
            }
        }
        return false; // Return false if no match found.
    }
    
    // Returns the precedence of the operator. Higher values indicate higher precedence.
    private static int getOperatorPrecedence(char operator) {
        switch (operator) {
            case '^': return 3; // Exponentiation has the highest precedence.
            case '*':
            case '/': return 2; // Multiplication and division have medium precedence.
            case '+':
            case '-': return 1; // Addition and subtraction have the lowest precedence.
            default: return 0; // Non-operators have no precedence.
        }
    }
    
    public static void main(String[] args) {
        // Initialize stack for operators and other necessary variables.
        Stack stack = new ArrayStack(20);
        String inputExpression = "";
        boolean acceptedExpression = false;

        // Validate user input for the expression.
        while (!acceptedExpression) {
            inputExpression = JOptionPane.showInputDialog("Please enter an infix numerical expression (3-20 characters):");
            // Check for valid length and characters in the expression.
            if (inputExpression.length() >= 3 && inputExpression.length() <= 20) {
                acceptedExpression = true; // Valid length.
                for (int i = 0; i < inputExpression.length(); i++) {
                    char c = inputExpression.charAt(i);
                    // Validate each character against allowed characters (digits, operators, parentheses).
                    if (!isDigit(c) && !isOperator(c) && c != '(' && c != ')') {
                        acceptedExpression = false; // Invalid character found.
                        break; // Exit early if invalid character is detected.
                    }
                }
            }
            // Prompt the user to re-enter if input is invalid.
            if (!acceptedExpression) {
                JOptionPane.showMessageDialog(null, "Invalid characters. Use +, -, *, /, ^, (, ), or digits 0-9.");
            }
        }

        // Convert the validated infix expression to postfix notation.
        StringBuilder output = new StringBuilder(); // Output string for postfix expression.
        for (int i = 0; i < inputExpression.length(); i++) {
            char currentChar = inputExpression.charAt(i);
            if (isDigit(currentChar)) {
                output.append(currentChar); // Append digits directly to output.
            } else if (currentChar == '(') {
                stack.push(currentChar); // Push opening parenthesis onto stack.
            } else if (currentChar == ')') {
                // Pop from stack to output until an opening parenthesis is found.
                while (!stack.isEmpty() && (char) stack.top() != '(') {
                    output.append(stack.pop());
                }
                stack.pop(); // Discard the '(' from the stack.
            } else if (isOperator(currentChar)) {
                // Pop operators from stack to output based on precedence.
                while (!stack.isEmpty() && getOperatorPrecedence((char) stack.top()) >= getOperatorPrecedence(currentChar)) {
                    output.append(stack.pop());
                }
                stack.push(currentChar); // Push current operator onto stack.
            }
        }
        
        // Pop any remaining operators from the stack to the output.
        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }
        
        // Evaluate the postfix expression obtained from the conversion.
        Stack postfixStack = new ArrayStack(output.length()); // Stack for operands in postfix evaluation.
        for (int i = 0; i < output.length(); i++) {
            char currentChar = output.charAt(i);
            if (isDigit(currentChar)) {
                // Push numeric values (cast to double) onto the postfix stack.
                postfixStack.push((double) (currentChar - '0'));
            } else {
                // Pop the top two values for evaluation based on the operator.
                double val1 = (double) postfixStack.pop();
                double val2 = (double) postfixStack.pop();
                // Perform the operation based on the current operator.
                switch (currentChar) {
                    case '+': postfixStack.push(val2 + val1); break; // Addition.
                    case '-': postfixStack.push(val2 - val1); break; // Subtraction.
                    case '*': postfixStack.push(val2 * val1); break; // Multiplication.
                    case '/': postfixStack.push(val2 / val1); break; // Division.
                    case '^': postfixStack.push(Math.pow(val2, val1)); break; // Exponentiation.
                }
            }
        }
        double result = (double) postfixStack.top(); // Get the final result from the top of the stack.
        
        // Display the result, postfix expression, and original infix expression to the user.
        JOptionPane.showMessageDialog(null, "Result: " + result + "\nPostfix: " + output.toString() + "\nInfix: " + inputExpression);
        System.exit(0); // Exit the program.
    }
}
