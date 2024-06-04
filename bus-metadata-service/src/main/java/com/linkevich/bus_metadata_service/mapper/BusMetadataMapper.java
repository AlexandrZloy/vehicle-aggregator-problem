package com.linkevich.bus_metadata_service.mapper;

import com.linkevich.bus_metadata_service.openapi.model.BusMetadata;
import com.linkevich.bus_metadata_service.repository.model.BusMetadataEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusMetadataMapper {

    BusMetadata toDto(com.linkevich.bus_metadata_service.service.model.BusMetadata source);

    com.linkevich.bus_metadata_service.service.model.BusMetadata toModel(BusMetadataEntity source);
}
