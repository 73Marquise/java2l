package org.example;
import java.util.*;
public class Calculator {

    /**
     * checking an expression and calculating its value
     * @param expression processed expression
     * @param variables set of variables and their values
     * @return result of the expression
     * @throws Exception error in writing an expression or arithmetic error
     */
    public double evaluateExpression(String expression, Map<String, Double> variables) throws Exception {
        expression = expression.replaceAll("\\s", "");

        String[] tokens = expression.split("(?=[-+*/()])|(?<=[-+*/()])");

        Stack<String> operatorStack = new Stack<>();
        Stack<Double> operandStack = new Stack<>();

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                operandStack.push(Double.parseDouble(token));
            } else if (variables.containsKey(token)) {
                operandStack.push(variables.get(token));
            } else if (isOperator(token)) {
                while (!operatorStack.isEmpty() && hasHigherPrecedence(token, operatorStack.peek())) {
                    double operand2 = operandStack.pop();
                    double operand1 = operandStack.pop();
                    String operator = operatorStack.pop();
                    double result = performOperation(operator, operand1, operand2);
                    operandStack.push(result);
                }
                operatorStack.push(token);
            } else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    double operand2 = operandStack.pop();
                    double operand1 = operandStack.pop();
                    String operator = operatorStack.pop();
                    double result = performOperation(operator, operand1, operand2);
                    operandStack.push(result);
                }
                if (!operatorStack.isEmpty() && operatorStack.peek().equals("(")) {
                    operatorStack.pop();
                } else {
                    throw new Exception("Несогласованные скобки");
                }
            } else {
                throw new Exception("Неверный токен: " + token);
            }
        }

        while (!operatorStack.isEmpty()) {
            double operand2 = operandStack.pop();
            double operand1 = operandStack.pop();
            String operator = operatorStack.pop();
            double result = performOperation(operator, operand1, operand2);
            operandStack.push(result);
        }

        if (operandStack.size() != 1) {
            throw new Exception("Ошибка в выражении");
        }

        return operandStack.pop();
    }

    /**
     * check if the token is an operator
     * @param token token to be checked
     * @return true if token is an operator
     */
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    /**
     * arithmetic operation prioritization
     * @param operator operator to be checked
     * @return operator precedence
     */
    private int getOperatorPrecedence(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        } else if (operator.equals("*") || operator.equals("/")) {
            return 2;
        }
        return 0;
    }

    /**
     * check that one operator has a higher precedence than the other
     * @param op1 first operator to be checked
     * @param op2 second operator to be checked
     * @return true if the first operator has a higher precedence than the second
     */
    private boolean hasHigherPrecedence(String op1, String op2) {
        if (op1.equals("+") && op2.equals("-")) {
            return true;
        }
        int precedence1 = getOperatorPrecedence(op1);
        int precedence2 = getOperatorPrecedence(op2);
        return precedence2 >= precedence1;
    }

    /**
     * calculating the result of an operator with two operands
     * @param operator arithmetic operation
     * @param operand1 first operand
     * @param operand2 second operand
     * @return operation result
     * @throws Exception division by zero or wrong operator
     */
    private double performOperation(String operator, double operand1, double operand2) throws Exception {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new Exception("Деление на ноль");
                }
                return operand1 / operand2;
            default:
                throw new Exception("Неверный оператор: " + operator);
        }
    }
}