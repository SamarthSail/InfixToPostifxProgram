# Infix to Postfix Converter and Evaluator

This program reads a numerical infix expression from the user, converts it to a postfix expression, and then evaluates the postfix expression to return the result, as well as the infix and postfix expression. 

## Overview

### Features
This project demonstrates:
-  efficent use of the stack data structure concept
-  Stringbuilder for efficient string inputs
-  Input Validation and error handling to prevent invalid expressions from being processed
-  JOptionPane for GUI 


### Infix Expression
An infix expression is of the form `<operand> <operator> <operand>`. For example: 3 + (5 - 2) * 8
 
### Postfix Expression
A postfix expression is of the form `<operand> <operand> <operator>`, where the operator follows the operands. For example: 3 (4 2 *) +

## General Notes
- **Valid Characters**: The program only considers single digits (0-9) and the operators +, -, *, /, ^, (, ).
- **Input Length**: The user must input an expression with a minimum of 3 characters and a maximum of 20 characters.
- **Validation**: The program prompts the user to re-enter the expression if it contains invalid characters or does not meet the length requirements.
- **Operator Precedence**: 
  - `^` (Math.pow) has the highest precedence.
  - `*` and `/` have medium precedence.
  - `+` and `-` have the lowest precedence.
- **Casting**: Ensure operands are cast to an appropriate numeric type, as results may be decimal values (e.g., `3 * 4 / 5 = 2.4`).

## Infix to Postfix Algorithm
1. Scan the infix expression from left to right.
2. If the scanned character is an operand (number), append it to an output String.
3. If the character is an operator:
   - If its precedence is greater than the operator at the top of the stack (or the stack is empty or has an opening parenthesis), push it onto the stack.
   - Otherwise, pop all operators from the stack with equal or higher precedence and append them to the output. Push the scanned operator onto the stack afterward.
4. If the character is an `(`, push it onto the stack.
5. If the character is an `)`, pop from the stack to the output until an `(` is encountered; discard both parentheses.
6. Repeat the above steps until the entire infix expression has been scanned.
7. Pop any remaining operators from the stack to the output.
8. The output now contains the postfix expression.

### Evaluation of Postfix Expressions
1. Create an ArrayStack to store operands.
2. Scan the postfix expression:
   - If the element is a number (operand), push it onto the stack.
   - If the element is an operator, pop two operands, evaluate the operator, and push the result back onto the stack.
3. The final number on the stack is the result of the expression.

## How to Run
1. Make sure you have Java installed on your machine.
2. Clone this repository to your local machine or download the necessary files from this repo.
3. Compile the Java file using:
``` javac InfixToPostfixProgram.java```
4. Run the program:
``` java InfixToPostfixProgram```









