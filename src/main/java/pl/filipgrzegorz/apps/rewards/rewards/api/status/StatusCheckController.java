package pl.filipgrzegorz.apps.rewards.rewards.api.status;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class StatusCheckController {

    @GetMapping(path = "/status/check")
    public ResponseEntity<String> statusCheck() {
        return new ResponseEntity("Api is UP", HttpStatus.OK);
    }
}
