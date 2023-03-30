package pl.filipgrzegorz.apps.rewards.rewards.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.filipgrzegorz.apps.rewards.rewards.api.exceptions.EntityNotFoundException;
import pl.filipgrzegorz.apps.rewards.rewards.helper.RewardsCalculator;
import pl.filipgrzegorz.apps.rewards.rewards.model.Transaction;
import pl.filipgrzegorz.apps.rewards.rewards.service.implementation.RewardsServiceImpl;
import pl.filipgrzegorz.apps.rewards.rewards.service.implementation.TransactionServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateRewardsServiceTest {

    @InjectMocks
    RewardsServiceImpl rewardsService;
    @Mock
    TransactionServiceImpl transactionService;
    @Spy
    CalculateRewardsService calculateRewardsService;

    static ArrayList<Transaction> transactions;

    @BeforeAll
    static void setup() {
        transactions = new ArrayList<>();
        transactions.add(new Transaction(1, "trans1", 1, BigDecimal.valueOf(80.40), null, LocalDate.now(), LocalDate.of(2023, 3, 23)));
        transactions.add(new Transaction(2, "trans2", 1, BigDecimal.valueOf(180.80), null, LocalDate.now(), LocalDate.of(2023, 3, 23)));
        transactions.add(new Transaction(3, "trans3", 1, BigDecimal.valueOf(30.40), null, LocalDate.now(), LocalDate.of(2023, 2, 11)));
        transactions.add(new Transaction(4, "trans4", 1, BigDecimal.valueOf(1000.00), null, LocalDate.now(), LocalDate.of(2023, 1, 4)));
        transactions.add(new Transaction(5, "trans5", 2, BigDecimal.valueOf(120), 90, LocalDate.now(), LocalDate.of(2023, 3, 5)));
        transactions.add(new Transaction(6, "trans6", 2, BigDecimal.valueOf(289.49), 428, LocalDate.now(), LocalDate.of(2023, 2, 4)));
        transactions.add(new Transaction(7, "trans7", 2, BigDecimal.valueOf(52), 50, LocalDate.now(), LocalDate.of(2023, 1, 4)));
    }

    @Test
    void testShouldCalculateRewardsForCustomerTransactions() {
        Integer customerId = 1;
        when(transactionService.findAllCustomerTransactions(customerId)).thenReturn(transactions.stream().filter(trans -> trans.getCustomerId().equals(customerId)).collect(Collectors.toList()));
        List<Transaction> customerTransactions = transactionService.findAllCustomerTransactions(customerId);
        customerTransactions.stream().forEach(transaction -> transaction.setAwardingPoints(RewardsCalculator.calculatePointsFromTransactionAmount(transaction.getAmount())));

        assertFalse(customerTransactions.stream().anyMatch(t -> t.getAmount() == null));
    }

    @Test
    void testShouldFindRewardsForCustomerId() {
        Integer customerId = 2;
        when(transactionService.findAllCustomerTransactions(customerId)).thenReturn(transactions.stream().filter(trans -> trans.getCustomerId().equals(customerId)).collect(Collectors.toList()));
        assertEquals(1, rewardsService.getCustomerRewards(customerId).size());

    }

    @Test
    void testShouldThrowEntityNotFoundException() {
        Integer customerId = 3;
        when(transactionService.findAllCustomerTransactions(customerId)).thenReturn(transactions.stream().filter(trans -> trans.getCustomerId().equals(customerId)).collect(Collectors.toList()));
        assertThrows(EntityNotFoundException.class, () -> {
            rewardsService.getCustomerRewards(customerId);
        });
    }
}