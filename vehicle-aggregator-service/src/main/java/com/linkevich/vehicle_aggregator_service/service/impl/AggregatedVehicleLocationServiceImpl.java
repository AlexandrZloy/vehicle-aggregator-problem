package com.linkevich.vehicle_aggregator_service.service.impl;

import com.linkevich.vehicle_aggregator_service.service.AggregatedVehicleLocationService;
import com.linkevich.vehicle_aggregator_service.service.VehicleLocationService;
import com.linkevich.vehicle_aggregator_service.service.model.VehicleLocation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.util.List;

@Service
public class AggregatedVehicleLocationServiceImpl implements AggregatedVehicleLocationService {

    private final List<VehicleLocationService> vehicleLocationServices;

    public AggregatedVehicleLocationServiceImpl(List<VehicleLocationService> vehicleLocationServices) {
        this.vehicleLocationServices = vehicleLocationServices;
    }

    @Override
    public Flux<VehicleLocation> getVehicleLocations() {
        return Flux.merge(
                vehicleLocationServices.stream()
                        .map(vehicleLocationService -> vehicleLocationService.getVehicleLocations()
                                .retryWhen(Retry.indefinitely()))
                        .toList()
        );
    }
}
