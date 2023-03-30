package pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

import static pl.filipgrzegorz.apps.rewards.rewards.helper.TransactionHelper.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRestDto {

    @NotNull(message = VALIDATE_CUSTOMER_ID_MSG)
    private Integer customerId;
    @ApiModelProperty(value = UNIQUE_ID_OF_TRANSACTION, example = EXAMPLE_TRANSACTION_ID)
    @NotBlank(message = VALIDATE_TRANSACTION_ID_MSG)
    private String transactionId;
    @NotNull
    @DecimalMin(value = "0.01", message = VALIDATE_TRANSACTION_AMOUNT_SHOULD_BE_GREATER_THAN_ZERO)
    @DecimalMax(value = "99999999.99", message = VALIDATE_TRANSACTION_AMOUNT_SHOULD_BE_LESS_THAN)
    private BigDecimal amount;
    @ApiModelProperty(hidden = true)
    private LocalDate creationDate;
    @ApiModelProperty(example = "2023-03-27", notes = "YYYY-MM-DD")
    @NotNull(message = TRANSACTION_DATE_DATE_SHOULD_BE_NOT_NULL_MSG)
    private LocalDate transactionDate;
    @ApiModelProperty(hidden = true)
    private Integer awardingPoints;

}
