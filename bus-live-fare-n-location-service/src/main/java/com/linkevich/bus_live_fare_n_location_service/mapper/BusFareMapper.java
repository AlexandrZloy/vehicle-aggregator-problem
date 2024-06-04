package com.linkevich.bus_live_fare_n_location_service.mapper;

import com.linkevich.bus_live_fare_n_location_service.openapi.model.BusFare;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusFareMapper {

    BusFare toDto(com.linkevich.bus_live_fare_n_location_service.service.model.BusFare source);
}
