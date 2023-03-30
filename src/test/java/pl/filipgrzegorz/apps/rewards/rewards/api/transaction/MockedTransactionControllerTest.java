package pl.filipgrzegorz.apps.rewards.rewards.api.transaction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto.TransactionResponseDto;
import pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto.TransactionRestDto;
import pl.filipgrzegorz.apps.rewards.rewards.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MockedTransactionControllerTest {


    private static TransactionRestDto transactionRestDto;
    @InjectMocks
    TransactionController transactionController;
    @Spy
    TransactionService transactionService;

    @BeforeAll
    static void setup() {
        transactionRestDto = new TransactionRestDto(1, "trans1", BigDecimal.valueOf(80.40), null, LocalDate.now(), 50);
    }

    @Test
    void shouldAddTransaction() {
        when(transactionService.addTransaction(any(TransactionRestDto.class))).thenReturn(new TransactionResponseDto(transactionRestDto.getCustomerId(), transactionRestDto.getAwardingPoints()));
        ResponseEntity<TransactionResponseDto> responseEntity = transactionController.addTransaction(transactionRestDto);
        assertEquals(responseEntity.getStatusCodeValue(), 201);
    }
}