package com.linkevich.bus_fallback_fare_service.mapper;

import com.linkevich.bus_fallback_fare_service.openapi.model.BusFare;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusFareMapper {

    BusFare toDto(com.linkevich.bus_fallback_fare_service.service.model.BusFare source);
}
