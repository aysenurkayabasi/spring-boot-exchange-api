package com.openpayd.exchange.controller;

import com.openpayd.exchange.model.request.ConvertCurrencyRequestDto;
import com.openpayd.exchange.model.response.ConvertCurrencyResponseDto;
import com.openpayd.exchange.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class CurrencyConversionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyConversionController currencyConversionController;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(currencyConversionController).build();
    }

    @Test
    void testConvertCurrency() throws Exception {
        ConvertCurrencyResponseDto responseDto = ConvertCurrencyResponseDto.builder()
                .convertedAmount(85.0)
                .convertedDate(LocalDateTime.now())
                .transactionId(UUID.randomUUID())
                .build();

        when(currencyService.convert("USD", "EUR", 100.0)).thenReturn(responseDto);

        mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sourceCurrency\":\"USD\",\"targetCurrency\":\"EUR\",\"amount\":100.0}"))
                .andExpect(status().isOk());

        verify(currencyService, times(1)).convert("USD", "EUR", 100.0);
    }


}
