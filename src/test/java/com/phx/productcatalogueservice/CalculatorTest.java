package com.phx.productcatalogueservice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void Test_addWithTwoInteger_RunSuccessfully() {
        // Arrange
        Calculator calculator = new Calculator();

        // Act
        int result = calculator.add(100,150);

        // Assert
        assertEquals(250,result);
    }

    @Test
    void Test_DivideByZero_ThrowsArithmaticException() {
        // Arrange
        Calculator calculator = new Calculator();

        // Act & Assert
        assertThrows(ArithmeticException.class, () -> calculator.divide(100,0));
    }
}