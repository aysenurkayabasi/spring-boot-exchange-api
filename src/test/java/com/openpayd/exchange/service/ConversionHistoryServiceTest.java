package com.openpayd.exchange.service;

import com.openpayd.exchange.entity.CurrencyConversionHistory;
import com.openpayd.exchange.repository.CurrencyConversionHistoryRepository;
import com.openpayd.exchange.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConversionHistoryServiceTest {

    @Mock
    private CurrencyConversionHistoryRepository conversionHistoryRepository;

    @InjectMocks
    private ConversionHistoryService conversionHistoryService;

    @Test
    public void testGetConversionHistoryByTransactionId() {
        UUID transactionId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        List<CurrencyConversionHistory> mockResponseList = Arrays.asList(
                CurrencyConversionHistory.builder().id(transactionId).sourceCurrency("USD").targetCurrency("EUR").amount(100.0).convertedAmount(90.0).transactionDate(LocalDateTime.now()).build()
        );
        when(conversionHistoryRepository.findById(transactionId, pageable)).thenReturn(mockResponseList);
        conversionHistoryService.getConversionHistoryByTransactionId(transactionId, pageable);
        verify(conversionHistoryRepository).findById(transactionId, pageable);
    }

    @Test
    public void testGetConversionHistoryByDate() {
        String transactionDate = "2024-09-01T10:00:00";
        Pageable pageable = PageRequest.of(0, 10);
        List<CurrencyConversionHistory> mockResponseList = Arrays.asList(
                CurrencyConversionHistory.builder().id(UUID.randomUUID()).sourceCurrency("USD").targetCurrency("EUR").amount(100.0).convertedAmount(90.0).transactionDate(LocalDateTime.now()).build()
        );
        LocalDateTime convertedDate = DateTimeUtil.convertStringToLocalDateTime(transactionDate);
        when(conversionHistoryRepository.findByTransactionDate(convertedDate, pageable)).thenReturn(mockResponseList);
        conversionHistoryService.getConversionHistoryByDate(transactionDate, pageable);
        verify(conversionHistoryRepository).findByTransactionDate(convertedDate, pageable);
    }

    @Test
    public void testGetConversionHistoryByIdAndCreatedAt() {
        UUID transactionId = UUID.randomUUID();
        String transactionDate = "2024-09-01T10:00:00";
        Pageable pageable = PageRequest.of(0, 10);
        List<CurrencyConversionHistory> mockResponseList = Arrays.asList(
                CurrencyConversionHistory.builder().id(transactionId).sourceCurrency("USD").targetCurrency("EUR").amount(100.0).convertedAmount(90.0).transactionDate(LocalDateTime.now()).build()
        );
        LocalDateTime convertedDate = DateTimeUtil.convertStringToLocalDateTime(transactionDate);
        when(conversionHistoryRepository.findByIdAndTransactionDate(transactionId, convertedDate, pageable)).thenReturn(mockResponseList);
        conversionHistoryService.getConversionHistoryByIdAndCreatedAt(transactionId, transactionDate, pageable);
        verify(conversionHistoryRepository).findByIdAndTransactionDate(transactionId, convertedDate, pageable);
    }
}
