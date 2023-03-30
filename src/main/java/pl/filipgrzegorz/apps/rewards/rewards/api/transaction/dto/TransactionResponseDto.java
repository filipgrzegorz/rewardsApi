package pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponseDto {
    Integer customerId;
    Integer reward;
}

