package com.openpayd.exchange.controller;

import com.openpayd.exchange.model.response.ConversionHistoryResponseDto;
import com.openpayd.exchange.service.ConversionHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class ConversionHistoryController {

    private final ConversionHistoryService conversionHistoryService;

    @Operation(summary = "Fetch conversion history by transaction ID and/or date", description = "Provide a transaction ID or date to fetch the conversion history.")
    @GetMapping("/conversion-history")
    @PageableAsQueryParam
    public ResponseEntity<?> getConversionHistory(
            @Parameter(description = "Transaction ID to filter history", example = "3109e827-2316-4fbd-b738-c61f3f44d06b")
            @RequestParam(required = false) UUID transactionId,
            @Parameter(description = "Transaction date to filter history", example = "2024-09-01T17:18:04")
            @RequestParam(required = false) String transactionDate,
            @Parameter(description = "Page")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Size")
            @RequestParam(defaultValue = "3") int size) {

        Pageable pageable = PageRequest.of(page, size);
        if (transactionId != null && transactionDate != null) {
            List<ConversionHistoryResponseDto> history = conversionHistoryService.getConversionHistoryByIdAndCreatedAt(transactionId, transactionDate, pageable);
            return ResponseEntity.ok(history);
        }

        if (transactionId != null) {
            List<ConversionHistoryResponseDto> history = conversionHistoryService.getConversionHistoryByTransactionId(transactionId, pageable);
            return ResponseEntity.ok(history);
        }

        if (transactionDate != null) {
            List<ConversionHistoryResponseDto> history = conversionHistoryService.getConversionHistoryByDate(transactionDate, pageable);
            return ResponseEntity.ok(history);
        }

        return ResponseEntity.badRequest().body("At least one of transactionId or transactionDate must be provided");

    }

}
