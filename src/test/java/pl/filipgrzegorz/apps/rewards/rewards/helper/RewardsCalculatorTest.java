package pl.filipgrzegorz.apps.rewards.rewards.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.filipgrzegorz.apps.rewards.rewards.api.exceptions.TransactionServiceException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RewardsCalculatorTest {

    @Test
    @DisplayName("Should throw TransactionServiceException when when amount is null")
    void testShouldThrowTransactionServiceExceptionWhenArgumentIsNull() {
        assertThrows(TransactionServiceException.class, () -> {
            RewardsCalculator.calculatePointsFromTransactionAmount(null);
        });
    }

    @Test
    @DisplayName("Should return Zero Point when amount is less than fifty dollars")
    void testShouldReturnZeroPointOnceValueIsLessThanFiftyDollars() {
        assertEquals(0, RewardsCalculator.calculatePointsFromTransactionAmount(BigDecimal.valueOf(49.99)));
    }

    @Test
    @DisplayName("Should return Zero Point when amount is negative")
    void testShouldReturnZeroPointOnceValueIsNegative() {
        assertEquals(0, RewardsCalculator.calculatePointsFromTransactionAmount(BigDecimal.valueOf(7777.99).negate()));
    }

    @Test
    @DisplayName("Should return fifty points when amount is lest than one hundred dollars")
    void testShouldReturnFiftyPointOnceValueIsLessThanOneHundred() {
        assertEquals(50, RewardsCalculator.calculatePointsFromTransactionAmount(BigDecimal.valueOf(99.99)));
    }

    @Test
    @DisplayName("Should return expected points when amount is more than one hundred dollars")
    void testShouldReturnValidPointsAmountOnceValueIsGreaterThanOneHundred() {
        assertEquals(90, RewardsCalculator.calculatePointsFromTransactionAmount(BigDecimal.valueOf(120)));
        assertEquals(428, RewardsCalculator.calculatePointsFromTransactionAmount(BigDecimal.valueOf(289.49)));
        assertEquals(428, RewardsCalculator.calculatePointsFromTransactionAmount(BigDecimal.valueOf(289.78)));
        assertEquals(430, RewardsCalculator.calculatePointsFromTransactionAmount(BigDecimal.valueOf(290.01)));
    }
}