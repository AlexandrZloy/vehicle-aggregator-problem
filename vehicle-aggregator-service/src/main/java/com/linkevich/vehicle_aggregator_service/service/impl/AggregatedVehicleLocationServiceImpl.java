package com.linkevich.vehicle_aggregator_service.service.impl;

import com.linkevich.vehicle_aggregator_service.service.AggregatedVehicleLocationService;
import com.linkevich.vehicle_aggregator_service.service.VehicleLocationService;
import com.linkevich.vehicle_aggregator_service.service.model.VehicleLocation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AggregatedVehicleLocationServiceImpl implements AggregatedVehicleLocationService {

    private static final Logger LOGGER = Logger.getLogger(AggregatedVehicleLocationServiceImpl.class.getName());
    private final List<VehicleLocationService> vehicleLocationServices;

    public AggregatedVehicleLocationServiceImpl(List<VehicleLocationService> vehicleLocationServices) {
        this.vehicleLocationServices = vehicleLocationServices;
    }

    @Override
    public Flux<VehicleLocation> getVehicleLocations() {
        return Flux.merge(
                vehicleLocationServices.stream()
                        .map(vehicleLocationService -> wrapWithResumeOnError(vehicleLocationService.getVehicleLocations()))
                        .toList()
        );
    }

    private Flux<VehicleLocation> wrapWithResumeOnError(Flux<VehicleLocation> source) {
        return source.onErrorResume(throwable -> {
            LOGGER.log(Level.WARNING, throwable.getMessage());
            return wrapWithResumeOnError(source);
        });
    }
}
