package pl.filipgrzegorz.apps.rewards.rewards.service;

import org.springframework.stereotype.Service;
import pl.filipgrzegorz.apps.rewards.rewards.api.rewards.dto.CustomerRewardResponseDto;
import pl.filipgrzegorz.apps.rewards.rewards.model.Transaction;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CalculateRewardsService {

    private int getSumAllPoints(List<Transaction> customerTransactions) {
        return customerTransactions.stream().filter(Objects::nonNull).mapToInt(Transaction::getAwardingPoints).sum();
    }

    public CustomerRewardResponseDto getCustomerRewards(Integer customerId, List<Transaction> customerTransactions) {
        CustomerRewardResponseDto customerRewardResponseDto = new CustomerRewardResponseDto();
        customerRewardResponseDto.setCustomerId(customerId);
        customerRewardResponseDto.setTotalPoints(getSumAllPoints(customerTransactions));
        customerRewardResponseDto.setMonthlyPoints(getMonthlyData(customerTransactions));
        return customerRewardResponseDto;
    }

    private Map<Month, Integer> getMonthlyData(List<Transaction> customerTransactions) {
        Map<Month, Integer> valuesMap = new HashMap<>();
        customerTransactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getTransactionDate().getMonth()))
                .entrySet().stream()
                .forEach(entry -> valuesMap.put(entry.getKey(), getSumAllPoints(entry.getValue())));
        return valuesMap;
    }

}
