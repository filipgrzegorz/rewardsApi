package pl.filipgrzegorz.apps.rewards.rewards.api.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto.TransactionResponseDto;
import pl.filipgrzegorz.apps.rewards.rewards.model.Transaction;
import pl.filipgrzegorz.apps.rewards.rewards.service.TransactionService;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Transaction Controller Integration Test")
class TransactionControllerTest {
    public static final String NEW_TRANSACTION_JSON_METADATA = "{\"customerId\":\"1\", \"transactionId\" : \"%s\", \"amount\":\"120.00\", \"transactionDate\":\"2023-03-28\"}";
    public static final String UPDATE_AMOUNT_JSON_METADATA = "{\"amount\":\"70.00\"}";
    private final String API_ADD_TRANSACTION_ENDPOINT = "/api/transactions";
    private final String API_UPDATE_TRANSACTION_ENDPOINT = "/api/transactions/%s";
    @Autowired
    TransactionService transactionService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private TransactionResponseDto responseDto;
    private MvcResult results;
    private MockHttpServletRequestBuilder request;

    @Test
    @DisplayName("Should add transaction and calculate rewards points")
    @Transactional
    void shouldAddTransactionAndCalculateRewards() throws Exception {
        //given
        request = buildAddTransactionRequest("trans-1");
        //when
        results = mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn();
        //then
        responseDto = getTransactionResponseDto(results);
        List<Transaction> customerTransactions = transactionService.findAllCustomerTransactions(1);
        assertEquals(responseDto.getCustomerId(), 1);
        assertEquals(responseDto.getReward(), 90);
        assertEquals(1, customerTransactions.size());
    }

    @Test
    @DisplayName("Should update transaction and calculate rewards points")
    @Transactional
    void shouldUpdateTransactionAndCalculateRewards() throws Exception {
        //given
        request = buildAddTransactionRequest("trans-2");
        results = mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn();
        //when
        results = mockMvc.perform(MockMvcRequestBuilders.put(String.format(API_UPDATE_TRANSACTION_ENDPOINT, "trans-2"))
                        .content(UPDATE_AMOUNT_JSON_METADATA).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(HttpStatus.ACCEPTED.value()))
                .andReturn();
        //then
        List<Transaction> customerTransactions = transactionService.findAllCustomerTransactions(1);
        responseDto = getTransactionResponseDto(results);
        assertEquals(responseDto.getCustomerId(), 1);
        assertEquals(responseDto.getReward(), 50);
        assertEquals(1, customerTransactions.size());
    }


    private MockHttpServletRequestBuilder buildAddTransactionRequest(String transactionId) {
        return MockMvcRequestBuilders.post(API_ADD_TRANSACTION_ENDPOINT)
                .content(String.format(NEW_TRANSACTION_JSON_METADATA, transactionId))
                .contentType(MediaType.APPLICATION_JSON);
    }

    private TransactionResponseDto getTransactionResponseDto(MvcResult results) throws JsonProcessingException, UnsupportedEncodingException {
        TransactionResponseDto responseDto = objectMapper.readValue(results.getResponse().getContentAsString(), TransactionResponseDto.class);
        return responseDto;
    }


}

