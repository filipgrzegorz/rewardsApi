package pl.filipgrzegorz.apps.rewards.rewards.service;

import pl.filipgrzegorz.apps.rewards.rewards.api.rewards.dto.CustomerRewardResponseDto;

import java.util.List;

public interface RewardsService {
    List<CustomerRewardResponseDto> getCustomerRewards(Integer customerId);

    List<CustomerRewardResponseDto> getAllCustomersRewards();
}
