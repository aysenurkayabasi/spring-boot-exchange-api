package com.openpayd.exchange.service;

import com.openpayd.exchange.adapter.CurrencyLayerApiStrategy;
import com.openpayd.exchange.adapter.ExchangeRateManager;
import com.openpayd.exchange.adapter.ExchangeRateStrategy;
import com.openpayd.exchange.client.currencyLayer.CurrencyLayerClient;
import com.openpayd.exchange.client.currencyLayer.response.ConvertCurrencyResponse;
import com.openpayd.exchange.entity.CurrencyConversionHistory;
import com.openpayd.exchange.enums.ClientType;
import com.openpayd.exchange.repository.CurrencyConversionHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {
    @Mock
    private CurrencyConversionHistoryRepository repository;
    @Mock
    private ExchangeRateManager exchangeRateManager;

    @Mock
    private CurrencyLayerClient client;

    private ExchangeRateStrategy exchangeRateStrategy;

    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        exchangeRateStrategy = new CurrencyLayerApiStrategy(client);
        when(exchangeRateManager.getClientStrategy(ClientType.CURRENCY_LAYER)).thenReturn(exchangeRateStrategy);
        currencyService = new CurrencyService(repository, exchangeRateManager);
    }

    @Test
    void convert() {
        when(client.convert(null, "source", "destination", 1.0))
                .thenReturn(ConvertCurrencyResponse.builder()
                        .result(1.0)
                        .success(true)
                        .info(ConvertCurrencyResponse.Info.builder()
                                .timestamp(1723481477L)
                                .build())
                        .build());

        when(repository.save(any())).thenReturn(CurrencyConversionHistory.builder().id(UUID.fromString("3109e827-2316-4fbd-b738-c61f3f44d06b")).build());
        currencyService.convert("source", "destination", 1.0);
        verify(repository).save(any());
    }

}
