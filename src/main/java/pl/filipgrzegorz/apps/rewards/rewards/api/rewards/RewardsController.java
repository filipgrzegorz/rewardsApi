package pl.filipgrzegorz.apps.rewards.rewards.api.rewards;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.filipgrzegorz.apps.rewards.rewards.api.rewards.dto.CustomerRewardResponseDto;
import pl.filipgrzegorz.apps.rewards.rewards.service.RewardsService;

import java.util.List;

@RestController
@RequestMapping(value = "api/rewards")
public class RewardsController {

    @Autowired
    RewardsService rewardsService;

    @ApiOperation(value = "Get Customer Rewards")
    @GetMapping(path = "/{customerId}")
    public ResponseEntity<List<CustomerRewardResponseDto>> getCustomerRewards(@ApiParam(value = "Unique id of Customer", defaultValue = "1l", required = true) @PathVariable Integer customerId) {
        List<CustomerRewardResponseDto> rewardsResponseDto = rewardsService.getCustomerRewards(customerId);
        return new ResponseEntity<>(rewardsResponseDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Customers Rewards")
    @GetMapping
    public ResponseEntity<List<CustomerRewardResponseDto>> getAllCustomersRewards() {
        List<CustomerRewardResponseDto> rewardsResponseDto = rewardsService.getAllCustomersRewards();
        return new ResponseEntity<>(rewardsResponseDto, HttpStatus.OK);
    }
}
