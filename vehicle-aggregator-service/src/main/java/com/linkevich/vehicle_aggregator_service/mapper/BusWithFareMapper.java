package com.linkevich.vehicle_aggregator_service.mapper;

import com.linkevich.vehicle_aggregator_service.openapi.model.BusWithFare;
import com.linkevich.vehicle_aggregator_service.service.model.BusFare;
import com.linkevich.vehicle_aggregator_service.service.model.BusMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BusWithFareMapper {

    @Mapping(target = "id", source = "metadata.id")
    BusWithFare toDto(BusMetadata metadata, BusFare fare);
}
