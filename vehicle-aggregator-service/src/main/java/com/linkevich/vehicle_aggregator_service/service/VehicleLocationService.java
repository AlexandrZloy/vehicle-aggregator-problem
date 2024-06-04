package com.linkevich.vehicle_aggregator_service.service;

import com.linkevich.vehicle_aggregator_service.service.model.VehicleLocation;
import reactor.core.publisher.Flux;

public interface VehicleLocationService {

    Flux<VehicleLocation> getVehicleLocations();
}
