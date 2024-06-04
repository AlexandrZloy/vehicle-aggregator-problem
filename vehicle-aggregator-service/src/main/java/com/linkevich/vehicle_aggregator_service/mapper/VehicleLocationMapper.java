package com.linkevich.vehicle_aggregator_service.mapper;

import com.linkevich.bus_live_fare_n_location_service.openapi.model.BusLocation;
import com.linkevich.train_live_location_service.openapi.model.TrainLocation;
import com.linkevich.vehicle_aggregator_service.openapi.model.VehicleLocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleLocationMapper {

    @Mapping(target = "type", constant = "TRAIN")
    com.linkevich.vehicle_aggregator_service.service.model.VehicleLocation toModel(TrainLocation source);

    @Mapping(target = "type", constant = "BUS")
    com.linkevich.vehicle_aggregator_service.service.model.VehicleLocation toModel(BusLocation source);

    VehicleLocation toDto(com.linkevich.vehicle_aggregator_service.service.model.VehicleLocation source);
}
