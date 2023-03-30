package pl.filipgrzegorz.apps.rewards.rewards.service;

import pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto.TransactionResponseDto;
import pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto.TransactionRestDto;
import pl.filipgrzegorz.apps.rewards.rewards.model.Transaction;

import java.util.List;

public interface TransactionService {

    TransactionResponseDto addTransaction(TransactionRestDto transactionRestDto);

    TransactionResponseDto updateTransaction(String transactionId, TransactionRestDto transactionRestDto);

    public List<Transaction> findAllCustomerTransactions(Integer customerId);

    List<Transaction> findAllTransactionInPastThreeMonth();


}
