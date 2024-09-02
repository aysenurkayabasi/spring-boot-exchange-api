package com.openpayd.exchange.mapper;

import com.openpayd.exchange.entity.CurrencyConversionHistory;
import com.openpayd.exchange.model.response.ConversionHistoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ConversionHistoryMapper {
    ConversionHistoryMapper INSTANCE = Mappers.getMapper(ConversionHistoryMapper.class);

    List<ConversionHistoryResponseDto> toDTOList(List<CurrencyConversionHistory> conversionHistory);

}