package com.openpayd.exchange.controller;

import com.openpayd.exchange.model.response.ExchangeListResponseDto;
import com.openpayd.exchange.model.response.ExchangeRateResponseDto;
import com.openpayd.exchange.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class ExchangeRateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private ExchangeRateController exchangeRateController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(exchangeRateController).build();
    }

    @Test
    void testGetExchangeList() throws Exception {
        ExchangeListResponseDto exchangeListResponseDto = ExchangeListResponseDto.builder()
                .currencies(Map.of("USD", "United States Dollar", "TRY", "Turkish Lira"))
                .build();

        when(exchangeRateService.getExchangeList()).thenReturn(exchangeListResponseDto);

        // Act & Assert
        mockMvc.perform(get("/exchange-list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"currencies\":{\"USD\":\"United States Dollar\",\"TRY\":\"Turkish Lira\"}}"));

        verify(exchangeRateService, times(1)).getExchangeList();
    }

    @Test
    void testCalculateExchangeRate() throws Exception {
        String sourceCurrencyCode = "USD";
        String targetCurrencyCode = "TRY";

        ExchangeRateResponseDto exchangeRateResponseDto = ExchangeRateResponseDto.builder()
                .quote(7.45)
                .build();

        when(exchangeRateService.getExchangeRate(sourceCurrencyCode, targetCurrencyCode))
                .thenReturn(exchangeRateResponseDto);

        mockMvc.perform(get("/exchange-rate")
                        .param("sourceCurrencyCode", sourceCurrencyCode)
                        .param("targetCurrencyCode", targetCurrencyCode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"quote\":7.45}"));

        verify(exchangeRateService, times(1)).getExchangeRate(sourceCurrencyCode, targetCurrencyCode);
    }


}
