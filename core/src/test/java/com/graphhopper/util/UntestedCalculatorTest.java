package com.graphhopper.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UntestedCalculatorTest {

    private final untestedCalculator calculator = new untestedCalculator();

    @Test
    void testAddPositiveNumbers() {
        assertEquals(7, calculator.add(3, 4));
        assertEquals(100, calculator.add(50, 50));
    }

    @Test
    void testAddWithZero() {
        assertEquals(5, calculator.add(5, 0));
        assertEquals(5, calculator.add(0, 5));
    }

    @Test
    void testAddNegativeNumbers() {
        assertEquals(-5, calculator.add(-2, -3));
        assertEquals(1, calculator.add(-4, 5));
    }

    @Test
    void testMultiplyPositiveNumbers() {
        assertEquals(12, calculator.multiply(3, 4));
        assertEquals(100, calculator.multiply(10, 10));
    }

    @Test
    void testMultiplyWithZero() {
        assertEquals(0, calculator.multiply(5, 0));
        assertEquals(0, calculator.multiply(0, 8));
    }

    @Test
    void testMultiplyWithOne() {
        assertEquals(7, calculator.multiply(7, 1));
        assertEquals(9, calculator.multiply(1, 9));
    }

    @Test
    void testMultiplyNegativeNumbers() {
        assertEquals(12, calculator.multiply(-3, -4));
        assertEquals(-20, calculator.multiply(-5, 4));
    }
}
