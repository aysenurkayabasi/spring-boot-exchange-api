package com.openpayd.exchange.repository;


import com.openpayd.exchange.entity.CurrencyConversionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CurrencyConversionHistoryRepository extends CrudRepository<CurrencyConversionHistory, UUID> {

    List<CurrencyConversionHistory> findById(UUID id, Pageable pageable);

    List<CurrencyConversionHistory> findByTransactionDate(LocalDateTime transactionDate, Pageable pageable);

    List<CurrencyConversionHistory> findByIdAndTransactionDate(UUID id, LocalDateTime transactionDate, Pageable pageable);
}
