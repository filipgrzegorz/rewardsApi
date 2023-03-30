package pl.filipgrzegorz.apps.rewards.rewards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.filipgrzegorz.apps.rewards.rewards.model.Transaction;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByCustomerId(Integer customerId);

    List<Transaction> findAllByTransactionDateGreaterThan(LocalDate date);

    Transaction findByTransactionId(String transactionId);
}
