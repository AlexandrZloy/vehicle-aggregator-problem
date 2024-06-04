package com.linkevich.vehicle_aggregator_service.mapper;

import com.linkevich.vehicle_aggregator_service.service.model.BusMetadata;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusMetadataMapper {

    BusMetadata toModel(com.linkevich.bus_metadata_service.openapi.model.BusMetadata source);
}
