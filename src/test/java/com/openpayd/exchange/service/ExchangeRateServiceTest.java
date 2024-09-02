package com.openpayd.exchange.service;

import com.openpayd.exchange.adapter.CurrencyLayerApiStrategy;
import com.openpayd.exchange.adapter.ExchangeRateManager;
import com.openpayd.exchange.adapter.ExchangeRateStrategy;
import com.openpayd.exchange.client.currencyLayer.CurrencyLayerClient;
import com.openpayd.exchange.client.currencyLayer.response.ExchangeListResponse;
import com.openpayd.exchange.client.currencyLayer.response.ExchangeRateResponse;
import com.openpayd.exchange.enums.ClientType;
import com.openpayd.exchange.model.response.ExchangeListResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateManager exchangeRateManager;

    @Mock
    private CurrencyLayerClient client;

    private ExchangeRateStrategy exchangeRateStrategy;

    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        exchangeRateStrategy = new CurrencyLayerApiStrategy(client);
        when(exchangeRateManager.getClientStrategy(ClientType.CURRENCY_LAYER)).thenReturn(exchangeRateStrategy);

        exchangeRateService = new ExchangeRateService(exchangeRateManager);
    }

    @Test
    void calculateExchangeRate() {
        when(client.getExchangeRates(null, "source", "destination"))
                .thenReturn(ExchangeRateResponse.builder()
                        .quotes(Map.of("USD", 1.0))
                        .success(true)
                        .build());
        exchangeRateService.getExchangeRate("source", "destination");
    }


    @Test
    void testGetExchangeList() {
        ExchangeListResponse mockExchangeList = ExchangeListResponse.builder().currencies(Map.of("USD", "United States Dollar", "EUR", "Euro")).build();
        when(client.getExchangeList(null)).thenReturn(mockExchangeList);
        ExchangeListResponseDto responseDto = exchangeRateService.getExchangeList();
        assertEquals(mockExchangeList.getCurrencies(), responseDto.getCurrencies());
        verify(client, times(1)).getExchangeList(null);
    }
}
