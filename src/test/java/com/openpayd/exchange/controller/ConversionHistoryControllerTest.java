package com.openpayd.exchange.controller;

import com.openpayd.exchange.service.ConversionHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ConversionHistoryControllerTest {

    private MockMvc mockMvc;
    @Mock
    private ConversionHistoryService conversionHistoryService;

    private UUID transactionId;
    private String transactionDate;

    @InjectMocks
    private ConversionHistoryController currencyConversionController;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(currencyConversionController).build();
        MockitoAnnotations.openMocks(this);
        transactionId = UUID.randomUUID();
        transactionDate = "2024-09-01T17:18:04";
    }

    @Test
    public void testConversionHistoryByTransactionId() throws Exception {
        mockMvc.perform(get("/conversion-history")
                        .param("transactionId", transactionId.toString())
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testConversionHistoryByDate() throws Exception {
        mockMvc.perform(get("/conversion-history")
                        .param("transactionDate", transactionDate)
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testConversionHistoryTransactionIdAndDate() throws Exception {
        mockMvc.perform(get("/conversion-history")
                        .param("transactionId", transactionId.toString())
                        .param("transactionDate", transactionDate)
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testConversionHistory_BadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/conversion-history")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("At least one of transactionId or transactionDate must be provided"));

    }
}
