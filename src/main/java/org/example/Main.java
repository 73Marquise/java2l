package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String expression = scanner.nextLine();

        Map<String, Double> variables = new HashMap<>();

        for (int i = 0; i < expression.length(); i++) {
            if (String.valueOf(expression.charAt(i)).matches("[a-zA-Z]")) {
                stringBuilder.append(expression.charAt(i));
            } else if (!stringBuilder.isEmpty()) {
                variables.put(stringBuilder.toString(), value(stringBuilder.toString()));
                stringBuilder = new StringBuilder();
            }
        }
        if (!stringBuilder.isEmpty()) {
            variables.put(stringBuilder.toString(), value(stringBuilder.toString()));
        }

        try {
            double result = calculator.evaluateExpression(expression, variables);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка в выражении");
        }
    }

    /**
     * getting the value of the variable from the user
     * @param variable requested variable
     * @return value of the variable
     */
    private static double value(String variable) {
        String value;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf(String.format("Введите значение переменной %s: ", variable));
            value = scanner.nextLine();
            if (value.matches("-?\\d+(\\.\\d+)?")) {
                break;
            }
            System.out.println("Некорректное знчение");
        }
        return Double.parseDouble(value);
    }
}