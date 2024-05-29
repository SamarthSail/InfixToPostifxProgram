/**
 * Student id (22361553)
 * Name (Samarth Sail)
 */ 
import javax.swing.JOptionPane;
import java.lang.Math;
public class InfixToPostfix_PostfixSolver {
    /*
     * This method checks whether the character we put in this method is a digit that we can accept in the infix --> postfix converter, since the converter will accept digits from 1 to 9
     * if it is: then the method will return a boolean of true....else it will return false representing that the character is not a digit from 1 to 9.
     */
    private static boolean isDigit(char character) {
        return character >= '0' && character <= '9';
    }
    
    /*
     * This method checks whether the character we put in this method is a suitable operator that we can accept in the infix --> postfix converter, since the converter will accept the operator
     * that we have put in the character array called operators...
     * if the character is a suitable operator (it exists in the array called operators) then method returns true....else it will return false representing the character is not a suitable
     * operator for us to be able to use
     */
    private static boolean isOperator(char character) {
        char[] operators = {'+', '-', '/', '*', '^'};
        for (char c : operators) {
            if (c == character) {
                return true;
            }
        }
        return false;
    }
    
    /*
     * This method will give a value to the operator that we insert in this method. First we need the operator to be a character and then we insert it into this method....allowing us to give it
     * a value if it is a suitable operator if we have done our tests on the characters beforehand.
     * The method returns a value for the operator if the operator matches one of our case values.
     * the higher the number....the higher the priority
     * default has return 0 to complete this code as it wouldn't work without it...also used it to just double check the functionality for my other tests when building the infixpostfix code
     */
    private static int getOperatorPrecedence(char operator) {
        switch (operator) {
            case '^':
                return 3;
            case '*':
            case '/':    
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                return 0; 
        }
    }
    
    /*
     * Main method which will answer the assignment that is given to us
     * This code contains the following:
     * initialised and declared StackArray of size 20...along with initialised and declared String and boolean to be used in the functionality of the main method.
     * Code to validate whether to accept the expression which the user has inputted when prompted to give an expression which follows the conditions of this assignment...length of expression...
     * valid operators...valid digits...etc.
     * Code to convert an infix expression inputted by the user to a postfix expression
     * Code to Solve the postfix expression
     * 
     */
    public static void main(String[] args) {
        // Create a Stack and instantiate any needed variables
        Stack stack = new ArrayStack(20);
        String inputExpression = "";
        boolean acceptedExpression = false;

        /* Validating the expression....seeing whether the expression from the user can be accepted into the infixPostfix Converter
         * while the acceptedExpression is false...the while loop will keep executing until we find an acceptable expression...(when acceptedExpression becomes true)
         * to get this acceptedExpression, the while loop will keep asking for the user to input a proper expression and also give an error message if not done so and will repeat again
         * the while loop uses the if and for statements to see if the expression fits the conditions of the expression having to be of size between 3-20 in length and whether all the
         * characters in the expression are acceptable in the InfixPostfix converter code. if the size of expression is between 3-20 then the acceptedExpression boolean becomes true...and will
         * stay true as long as each character in the expression doesn't fulfill the condition in the if statement...(if statement checks if the character is not a digit,not an operator, and
         * not an opening and closing parenthesis)...then the acceptedExpression becomes false...starts again...
         * the if statement at the end gives an error message letting the user know their expression is valid and to try again...giving them the chance to input expression again until right
        */ 
        while (!acceptedExpression) {
            inputExpression = JOptionPane.showInputDialog("Please enter an infix numerical expression between 3 and 20 characters");
            if (inputExpression.length() >= 3 && inputExpression.length() <= 20) {
                acceptedExpression = true;
                for (int i = 0; i < inputExpression.length(); i++) {
                    char c = inputExpression.charAt(i);
                    if (!isDigit(c) && !isOperator(c) && c != '(' && c != ')') {
                        acceptedExpression = false;
                        break;
                    }
                }
            }
            if (!acceptedExpression) {
                JOptionPane.showMessageDialog(null, "Only the following characters are valid: +,-,*,/,^,(,), and numbers 0-9");
            }
        }

        // Code for converting an infix expression to postfix
        /*
         * NB: a stringbuilder called output is initialised which we will fill with a postfix expression answer...did not use string due to some implementation issues
         * NB: "scanned operator" = currentChar ....when looking at the comments for when the character is an operator...
         * The code first uses a for loop to go through the inputExpression (sent by the user containing the infix expression) character by character using currentChar.
         * The if and else if statements will evaluate each character in the expression and do the instructions based on the pdf assignment: 
         * - when the character is a digit from 0-9: the character is appended to the output
         * - when the character is a opening bracket: that character is pushed to the stack
         * - when the character is a closing bracket: the while loop will see as long as the stack is not empty (false) and the character on top of the stack is not an opening bracker...
         * the characters are popped one by one out of the stack and appened to the output....with stack.pop() at the end indicating that the while loop encountered an opening bracket and 
         * popped it making the parenthesis discarded.
         * - when the character is an operator: the while loop states while the stack is not empty and the value of the stack operator is greater or equal to the scanned operator to
         * append the popped character (the stack operator)...if this while loop doesnt have its conditioned fulfilled....then the scanned operator is pushed into the stack. These rules follow
         * the conditions in the assignment pdf which state what to do when a scanned character is an operator.
         * After looping through all the characters in the inputExpression....anything remaining in the stack becomes pushed and appended to our output as long as the stack is not empty
         */
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < inputExpression.length(); i++) {
            char currentChar = inputExpression.charAt(i);
            if (isDigit(currentChar)) {
                output.append(currentChar);
            } else if (currentChar == '(') {
                stack.push(currentChar);
            } else if (currentChar == ')') {
                while (!stack.isEmpty() && (char) stack.top() != '(') {
                    output.append(stack.pop());
                }
                stack.pop(); // remember that '(' gets popped here
            } else if (isOperator(currentChar)) {
                while (!stack.isEmpty() && getOperatorPrecedence((char) stack.top()) >= getOperatorPrecedence(currentChar)) {
                    output.append(stack.pop());
                }
                stack.push(currentChar);
            }
        }
        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }
        
        // Code for solving the postfix expression we got from the infixpostfix converter
        /*
         * Creates a new stackarray called postfixstack since we will be using the stack to solve a postfix expression.
         * NB: anything we do to the stack is converted into a double type if we are either popping/pushing or getting the top of the stack at all times.
         * For loop goes through each character(currentChar keeps track of this) in our output from our infixPostfix converter code...
         * if the current character(currentChar) is a digit of 0-9 using the isDigit method from before and it is true...then the character is pushed to the stack
         * else: that means the current character is an operator and depending on the operator we take two double values, val1 and val2 which will be the two popped values from the stack
         * and we use the current character operator on those values according to the if statements when their condition is fulfilled depending on the operator
         * Once all the characters in the output have been iterated through....we simply get the top of the stack since at the end of the for loop, it will always have our answer that we need
         * to solve the postfix expression. We make sure to convert the instantiate and declare the variable result into a type double just like before.
         */
        Stack postfixStack = new ArrayStack(output.length());
        for(int i = 0;i < output.length();i++){
            char currentChar = output.charAt(i);
            if (isDigit(currentChar)){
                postfixStack.push((double) (currentChar - '0'));
            } else{
                double val1 = (double)postfixStack.pop();
                double val2 = (double)postfixStack.pop();

                if (currentChar == '+'){
                    postfixStack.push(val2 + val1);
                }else if(currentChar == '-'){
                    postfixStack.push(val2 - val1);
                }else if (currentChar == '*'){
                    postfixStack.push(val2 * val1);
                }else if (currentChar == '/'){
                    postfixStack.push(val2 / val1);
                }else if (currentChar == '^'){
                    postfixStack.push(Math.pow(val2, val1));
                }
            }
        }
        double result = (double) postfixStack.top();
        
        /*
         * A message is shown to the user accordingly to how the assignment wants it: shows the postfix, infix and result after inputting an infix expression into our code.
         * Finally exits.
         */
        JOptionPane.showMessageDialog(null,"The result of the expression is: " + "\nPostfix: " + output.toString() + "\nInfix: " + inputExpression.toString() + "\nResult: " + result);
        System.exit(0);
    }
}
