package pl.filipgrzegorz.apps.rewards.rewards.api.rewards.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.util.Map;

@Data
@NoArgsConstructor
public class CustomerRewardResponseDto {
    Integer customerId;
    Integer totalPoints;
    Map<Month, Integer> monthlyPoints;
}
