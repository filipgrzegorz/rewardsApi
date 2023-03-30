package pl.filipgrzegorz.apps.rewards.rewards.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.filipgrzegorz.apps.rewards.rewards.api.exceptions.EntityNotFoundException;
import pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto.TransactionResponseDto;
import pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto.TransactionRestDto;
import pl.filipgrzegorz.apps.rewards.rewards.helper.RewardsCalculator;
import pl.filipgrzegorz.apps.rewards.rewards.model.Transaction;
import pl.filipgrzegorz.apps.rewards.rewards.repository.TransactionRepository;
import pl.filipgrzegorz.apps.rewards.rewards.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.filipgrzegorz.apps.rewards.rewards.helper.TransactionHelper.TRANSACTION_ERROR_MSG;

@Service
public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    Predicate<Transaction> isThreeMonthBack = trans -> trans.getTransactionDate().isAfter(LocalDate.now().minusMonths(3));

    public TransactionServiceImpl(@Autowired TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    private static String getMessage(String transactionId) {
        return String.format(TRANSACTION_ERROR_MSG, transactionId);
    }

    public TransactionResponseDto addTransaction(TransactionRestDto transactionRestDto) {
        if (transactionRestDto.getCreationDate() == null) {
            transactionRestDto.setCreationDate(LocalDate.now());
        }
        transactionRestDto.setAwardingPoints(RewardsCalculator.calculatePointsFromTransactionAmount(transactionRestDto.getAmount()));
        Transaction entity = new Transaction(transactionRestDto);
        transactionRepository.saveAndFlush(entity);
        return new TransactionResponseDto(transactionRestDto.getCustomerId(), transactionRestDto.getAwardingPoints());
    }

    @Override
    public TransactionResponseDto updateTransaction(String transactionId, TransactionRestDto transactionRestDto) {
        Transaction entity = findTransaction(transactionId);
        fillTransactionEntity(entity, transactionRestDto);
        transactionRepository.saveAndFlush(entity);
        return new TransactionResponseDto(entity.getCustomerId(), entity.getAwardingPoints());
    }

    private Transaction findTransaction(String transactionId) {
        Transaction entity = transactionRepository.findByTransactionId(transactionId);
        if (isNull(entity)) {
            throw new EntityNotFoundException(getMessage(transactionId));
        }
        return entity;
    }

    private void fillTransactionEntity(Transaction entity, TransactionRestDto transactionRestDto) {
        if (nonNull(transactionRestDto.getAmount()) && transactionRestDto.getAmount().compareTo(BigDecimal.ZERO) >= 0) {
            entity.setAmount(transactionRestDto.getAmount());
            entity.setAwardingPoints(RewardsCalculator.calculatePointsFromTransactionAmount(transactionRestDto.getAmount()));
        }
        if (nonNull(transactionRestDto.getCreationDate())) {
            entity.setCreationDate(transactionRestDto.getCreationDate());
        }
        if (nonNull(transactionRestDto.getTransactionDate())) {
            entity.setTransactionDate(transactionRestDto.getTransactionDate());
        }
        if (nonNull(transactionRestDto.getCustomerId())) {
            entity.setCustomerId(transactionRestDto.getCustomerId());
        }
    }

    @Override
    public List<Transaction> findAllCustomerTransactions(Integer customerId) {
        List<Transaction> customerTransaction = transactionRepository.findAllByCustomerId(customerId);
        return customerTransaction.stream().filter(isThreeMonthBack).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findAllTransactionInPastThreeMonth() {
        LocalDate date = LocalDate.now().minusMonths(3);
        return transactionRepository.findAllByTransactionDateGreaterThan(date);
    }
}
