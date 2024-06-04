package com.linkevich.bus_live_fare_n_location_service.mapper;

import com.linkevich.bus_live_fare_n_location_service.openapi.model.BusLocation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusLocationMapper {

    BusLocation toDto(com.linkevich.bus_live_fare_n_location_service.service.model.BusLocation source);
}
