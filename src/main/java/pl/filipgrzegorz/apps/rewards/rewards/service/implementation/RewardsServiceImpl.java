package pl.filipgrzegorz.apps.rewards.rewards.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.filipgrzegorz.apps.rewards.rewards.api.exceptions.EntityNotFoundException;
import pl.filipgrzegorz.apps.rewards.rewards.api.rewards.dto.CustomerRewardResponseDto;
import pl.filipgrzegorz.apps.rewards.rewards.model.Transaction;
import pl.filipgrzegorz.apps.rewards.rewards.service.CalculateRewardsService;
import pl.filipgrzegorz.apps.rewards.rewards.service.RewardsService;
import pl.filipgrzegorz.apps.rewards.rewards.service.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardsServiceImpl implements RewardsService {

    public static final String CUSTOMER_REWARDS_ERROR_MSG = "Customer Rewards with id %s ";
    public static final String REWARDS_MSG = "Rewards";
    TransactionService transactionService;
    CalculateRewardsService calculateRewardsService;

    public RewardsServiceImpl(@Autowired TransactionService transactionService, CalculateRewardsService calculateRewardsService) {
        this.transactionService = transactionService;
        this.calculateRewardsService = calculateRewardsService;
    }

    @Override
    public List<CustomerRewardResponseDto> getCustomerRewards(Integer customerId) {
        List<Transaction> transactions = transactionService.findAllCustomerTransactions(customerId);
        if (transactions.isEmpty()) {
            throw new EntityNotFoundException(String.format(CUSTOMER_REWARDS_ERROR_MSG, customerId));
        }
        return List.of(getCustomerRewards(customerId, transactions));
    }

    @Override
    public List<CustomerRewardResponseDto> getAllCustomersRewards() {
        List<Transaction> transactions = transactionService.findAllTransactionInPastThreeMonth();
        if (transactions.isEmpty()) {
            throw new EntityNotFoundException(REWARDS_MSG);
        }
        return getCustomerRewardResponseList(transactions);
    }

    private List<CustomerRewardResponseDto> getCustomerRewardResponseList(List<Transaction> transactions) {
        Map<Integer, List<Transaction>> groupedTransactions = transactions.stream().collect(Collectors.groupingBy(Transaction::getCustomerId));

        List<CustomerRewardResponseDto> response = new ArrayList<>();
        groupedTransactions.entrySet().stream().forEach(entry -> {
            List<Transaction> customerTransactions = entry.getValue();
            response.add(getCustomerRewards(entry.getKey(), customerTransactions));

        });
        return response;
    }

    private CustomerRewardResponseDto getCustomerRewards(Integer CustomerId, List<Transaction> customerTransactions) {
        return calculateRewardsService.getCustomerRewards(CustomerId, customerTransactions);
    }
}
