package pl.filipgrzegorz.apps.rewards.rewards.helper;

import pl.filipgrzegorz.apps.rewards.rewards.api.exceptions.TransactionServiceException;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class RewardsCalculator {
    private static final BigDecimal TWO_USD = BigDecimal.valueOf(2);
    private static final BigDecimal FIFTY_USD = BigDecimal.valueOf(50);
    private static final BigDecimal ONE_HUNDRED_USD = BigDecimal.valueOf(100);
    public static final String VALIDATE_NOT_NULL_MSG = "Amount should be not null";

    public static Integer calculatePointsFromTransactionAmount(BigDecimal amount) {

        if (amount == null) {
            throw new TransactionServiceException(VALIDATE_NOT_NULL_MSG);
        }

        Integer pointsAwarded = 0;
        if (amount.compareTo(FIFTY_USD) >= 0) {
            pointsAwarded += 50;
        }
        if (amount.compareTo(ONE_HUNDRED_USD) >= 0) {
            pointsAwarded += amount.setScale(0, RoundingMode.DOWN)
                    .subtract(ONE_HUNDRED_USD)
                    .multiply(TWO_USD)
                    .intValue();
        }

        return pointsAwarded;
    }
}
