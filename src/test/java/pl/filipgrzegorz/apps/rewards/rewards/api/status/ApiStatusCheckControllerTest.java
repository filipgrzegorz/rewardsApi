package pl.filipgrzegorz.apps.rewards.rewards.api.status;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Api Status Controller Integration Test")
class ApiStatusCheckControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final String API_ENDPOINT = "/api/status/check";


    @Test
    @DisplayName("When API is UP should return status OK")
    void shouldReturnValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_ENDPOINT))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()));

    }

}
