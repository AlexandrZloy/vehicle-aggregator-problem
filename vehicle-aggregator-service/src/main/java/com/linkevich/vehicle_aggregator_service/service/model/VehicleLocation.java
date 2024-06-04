package com.linkevich.vehicle_aggregator_service.service.model;

public record VehicleLocation(
        Long id,
        VehicleType type,
        Location location
) {
}
