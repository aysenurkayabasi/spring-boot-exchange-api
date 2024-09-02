package com.openpayd.exchange.adapter;

import com.openpayd.exchange.client.currencyLayer.CurrencyLayerClient;
import com.openpayd.exchange.client.currencyLayer.response.ConvertCurrencyResponse;
import com.openpayd.exchange.client.currencyLayer.response.ExchangeListResponse;
import com.openpayd.exchange.client.currencyLayer.response.ExchangeRateResponse;
import com.openpayd.exchange.exception.RemoteApiCallException;
import com.openpayd.exchange.model.response.ConvertCurrencyResponseDto;
import com.openpayd.exchange.model.response.ExchangeListResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyLayerApiStrategyTest {
    @Mock
    private CurrencyLayerClient client;

    @InjectMocks
    private CurrencyLayerApiStrategy strategy;

    @Test
    public void testGetExchangeList() {
        Map<String, String> currencies = new HashMap<>();
        currencies.put("USD", "US Dollar");
        currencies.put("EUR", "Euro");

        ExchangeListResponse exchangeListResponse = ExchangeListResponse.builder()
                .success(true)
                .currencies(currencies)
                .build();

        when(client.getExchangeList(null)).thenReturn(exchangeListResponse);

        ExchangeListResponseDto result = strategy.getExchangeList();

        assertNotNull(result);
        assertEquals(currencies, result.getCurrencies());
    }

    @Test
    public void testGetExchangeRate_Success() {
        Map<String, Double> quotes = new HashMap<>();
        quotes.put("USDEUR", 0.85);

        ExchangeRateResponse exchangeRateResponse = ExchangeRateResponse.builder()
                .success(true)
                .source("USD")
                .quotes(quotes)
                .build();

        when(client.getExchangeRates(null, "USD", "EUR")).thenReturn(exchangeRateResponse);

        Double rate = strategy.getExchangeRate("USD", "EUR");

        assertNotNull(rate);
        assertEquals(0.85, rate);
    }

    @Test
    public void testGetExchangeRate_Failure() {
        when(client.getExchangeRates(anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("API Error"));

        assertThrows(RemoteApiCallException.class, () -> strategy.getExchangeRate("USD", "EUR"));
    }

    @Test
    public void testConvert_Success() {
        ConvertCurrencyResponse.Info info = ConvertCurrencyResponse.Info.builder()
                .timestamp(1234567890L)
                .build();

        ConvertCurrencyResponse convertCurrencyResponse = ConvertCurrencyResponse.builder()
                .success(true)
                .result(85.0)
                .info(info)
                .build();

        when(client.convert(null, "USD", "EUR", 100.0)).thenReturn(convertCurrencyResponse);

        ConvertCurrencyResponseDto result = strategy.convert("USD", "EUR", 100.0);

        assertNotNull(result);
        assertEquals(85.0, result.getConvertedAmount());
    }

    @Test
    public void testConvert_Failure() {
        when(client.convert(anyString(), anyString(), anyString(), anyDouble()))
                .thenThrow(new RuntimeException("API Error"));

        assertThrows(RemoteApiCallException.class, () -> strategy.convert("USD", "EUR", 100.0));
    }

    @Test
    public void testConvert_EmptyResponse() {
        ConvertCurrencyResponse convertCurrencyResponse = ConvertCurrencyResponse.builder()
                .success(true)
                .result(null)
                .info(ConvertCurrencyResponse.Info.builder().timestamp(1234567890L).build())
                .build();

        when(client.convert(anyString(), anyString(), anyString(), anyDouble())).thenReturn(convertCurrencyResponse);

        assertThrows(RemoteApiCallException.class, () -> strategy.convert("USD", "EUR", 100.0));
    }
}
