package com.app.controller;

import com.app.service.AuthorizationService;
import com.app.service.DebitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorizationController.class)
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizationService authorizationService;

    @MockBean
    private DebitService debitService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthorizationController(authorizationService)).build();
    }

    @Test
    public void authorizeTransaction_ShouldReturnOk() throws Exception {
        Mockito.when(authorizationService.authorize(any())).thenReturn("00");

        String requestBody = getRequestBody();

        mockMvc.perform(post("/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "code": "00"
                }
            """));
    }


    @Test
    public void authorizeTransaction_ShouldReturnOkAnd51Code() throws Exception {
        Mockito.when(authorizationService.authorize(any())).thenReturn("51");

        String requestBody = getRequestBody();
        mockMvc.perform(post("/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "code": "51"
                }
            """));
    }

    @Test
    public void authorizeTransaction_ShouldReturnOkAnd07Code() throws Exception {
        Mockito.when(authorizationService.authorize(any())).thenReturn("07");

        String requestBody = getRequestBody();
        mockMvc.perform(post("/authorize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "code": "07"
                }
            """));
    }

    private static String getRequestBody() {
        return """
                    {
                        "transactionId": "123456",
                        "mcc": "1234",
                        "amount": 100.00,
                        "merchantName": "Test Merchant"
                    }
                """;
    }
}
