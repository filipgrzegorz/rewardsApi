package pl.filipgrzegorz.apps.rewards.rewards.api.transaction;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto.TransactionResponseDto;
import pl.filipgrzegorz.apps.rewards.rewards.api.transaction.dto.TransactionRestDto;
import pl.filipgrzegorz.apps.rewards.rewards.service.TransactionService;

import javax.validation.Valid;

import static pl.filipgrzegorz.apps.rewards.rewards.helper.TransactionHelper.*;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {


    @Autowired
    TransactionService transactionService;

    @ApiOperation(value = "Add Transaction")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseDto> addTransaction(@ApiParam(value = TRANSACTION_METADATA) @Valid @RequestBody TransactionRestDto transactionRestDto) {
        TransactionResponseDto transactionResponseDto = transactionService.addTransaction(transactionRestDto);
        return new ResponseEntity<>(transactionResponseDto, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Transaction")
    @PutMapping(path = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponseDto> updateTransaction(@ApiParam(value = UNIQUE_ID_OF_TRANSACTION, defaultValue = EXAMPLE_TRANSACTION_ID, required = true) @PathVariable String transactionId, @RequestBody TransactionRestDto transactionRestDto) {
        TransactionResponseDto transactionResponseDto = transactionService.updateTransaction(transactionId, transactionRestDto);
        return new ResponseEntity<>(transactionResponseDto, HttpStatus.ACCEPTED);
    }
}
