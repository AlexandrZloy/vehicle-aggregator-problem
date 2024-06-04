package com.linkevich.vehicle_aggregator_service.mapper;

import com.linkevich.vehicle_aggregator_service.service.model.BusFare;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusFareMapper {

    BusFare toModel(com.linkevich.bus_fallback_fare_service.openapi.model.BusFare source);

    BusFare toModel(com.linkevich.bus_live_fare_n_location_service.openapi.model.BusFare source);
}
