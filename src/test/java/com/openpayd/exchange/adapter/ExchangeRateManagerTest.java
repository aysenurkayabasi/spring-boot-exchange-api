package com.openpayd.exchange.adapter;

import com.openpayd.exchange.enums.ClientType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateManagerTest {

    @Mock
    private CurrencyLayerApiStrategy currencyLayerApiStrategy;

    @InjectMocks
    private ExchangeRateManager exchangeRateManager;

    @Test
    void testGetClientStrategyForCurrencyLayer() {
        ExchangeRateStrategy result = exchangeRateManager.getClientStrategy(ClientType.CURRENCY_LAYER);
        assertNotNull(result, "The result should not be null");
        assertSame(currencyLayerApiStrategy, result, "The result should be the same as the mocked CurrencyLayerApiStrategy");
    }

    @Test
    void testGetClientStrategyForUnsupportedClientType() {
        assertThrows(IllegalArgumentException.class, () -> {
            exchangeRateManager.getClientStrategy(ClientType.UNKNOWN);
        }, "Should throw IllegalArgumentException for unsupported client type");
    }
}