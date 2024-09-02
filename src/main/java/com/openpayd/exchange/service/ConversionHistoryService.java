package com.openpayd.exchange.service;


import com.openpayd.exchange.mapper.ConversionHistoryMapper;
import com.openpayd.exchange.model.response.ConversionHistoryResponseDto;
import com.openpayd.exchange.repository.CurrencyConversionHistoryRepository;
import com.openpayd.exchange.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversionHistoryService {

    private final CurrencyConversionHistoryRepository conversionHistoryRepository;

    public List<ConversionHistoryResponseDto> getConversionHistoryByTransactionId(UUID transactionId, Pageable pageable) {
        log.info("Fetching conversion history by transaction ID: {}", transactionId);
        return ConversionHistoryMapper.INSTANCE.toDTOList(conversionHistoryRepository.findById(transactionId, pageable));
    }

    public List<ConversionHistoryResponseDto> getConversionHistoryByDate(String transactionDate, Pageable pageable) {
        log.info("Fetching conversion history by date: {}", transactionDate);
        return ConversionHistoryMapper.INSTANCE.toDTOList(conversionHistoryRepository.findByTransactionDate(DateTimeUtil.convertStringToLocalDateTime(transactionDate), pageable));

    }

    public List<ConversionHistoryResponseDto> getConversionHistoryByIdAndCreatedAt(UUID transactionId, String transactionDate, Pageable pageable) {
        log.info("Fetching conversion history by transaction ID: {} and date: {}", transactionId, transactionDate);
        return ConversionHistoryMapper.INSTANCE.toDTOList(conversionHistoryRepository.findByIdAndTransactionDate(transactionId, DateTimeUtil.convertStringToLocalDateTime(transactionDate), pageable));
    }

}
