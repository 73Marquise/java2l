package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    @Test
    void testSum() throws Exception {
        Assertions.assertEquals(12.0, calculator.evaluateExpression("3 + 7 + 2", new HashMap<>()));
    }

    @Test
    void testSubtraction() throws Exception {
        Assertions.assertEquals(0.0, calculator.evaluateExpression("15 - 7 -8", new HashMap<>()));
    }

    @Test
    void testMultiply() throws Exception {
        Assertions.assertEquals(20.0, calculator.evaluateExpression("2 * 2 * 5", new HashMap<>()));
    }

    @Test
    void testDivision() throws Exception {
        Assertions.assertEquals(1.0, calculator.evaluateExpression("20/4/5", new HashMap<>()));
    }

    @Test
    void testComplexExpression() throws Exception {
        Assertions.assertEquals(1.0, calculator.evaluateExpression("(x*x - 4*x + 12) / (16*3/6)", Map.of("x", 2.0)));
    }

    @Test
    void testDivisionByZero() throws Exception {
        Assertions.assertThrows(Exception.class, () -> calculator.evaluateExpression("3 / 0", new HashMap<>()));
    }

    @Test
    void testExpressionError() {
        Assertions.assertThrows(Exception.class, () -> calculator.evaluateExpression("2^3 - ))", new HashMap<>()));
    }
}
