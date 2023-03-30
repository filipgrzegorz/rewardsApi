package pl.filipgrzegorz.apps.rewards.rewards.model;

import lombok.*;
import org.springframework.beans.BeanUtils;
import pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto.TransactionRestDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Transaction")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "TransactionId", nullable = false, unique = true)
    private String transactionId;
    @Column(name = "CustomerId", nullable = false)
    private Integer customerId;
    @Column(name = "Amount", nullable = false, scale = 2)
    private BigDecimal amount;
    @Column(name = "AwardingPoints", nullable = false)
    private Integer awardingPoints;
    @Column(name = "CreationDate", nullable = false)
    private LocalDate creationDate;
    @Column(name = "TransactionDate", nullable = false)
    private LocalDate transactionDate;

    public Transaction(TransactionRestDto transactionRestDto) {
        BeanUtils.copyProperties(transactionRestDto, this);
    }
}
