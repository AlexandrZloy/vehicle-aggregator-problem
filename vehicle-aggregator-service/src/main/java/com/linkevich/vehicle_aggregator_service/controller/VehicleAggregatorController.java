package com.linkevich.vehicle_aggregator_service.controller;

import com.linkevich.vehicle_aggregator_service.mapper.BusWithFareMapper;
import com.linkevich.vehicle_aggregator_service.mapper.VehicleLocationMapper;
import com.linkevich.vehicle_aggregator_service.openapi.api.BusesApi;
import com.linkevich.vehicle_aggregator_service.openapi.api.VehicleLocationStreamApi;
import com.linkevich.vehicle_aggregator_service.openapi.model.BusWithFare;
import com.linkevich.vehicle_aggregator_service.openapi.model.VehicleLocation;
import com.linkevich.vehicle_aggregator_service.service.AggregatedVehicleLocationService;
import com.linkevich.vehicle_aggregator_service.service.BusFareService;
import com.linkevich.vehicle_aggregator_service.service.BusMetadataService;
import com.linkevich.vehicle_aggregator_service.service.model.BusFare;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VehicleAggregatorController implements BusesApi, VehicleLocationStreamApi {

    private final BusMetadataService busMetadataService;
    private final BusFareService busFareService;
    private final BusWithFareMapper busWithFareMapper;
    private final AggregatedVehicleLocationService aggregatedVehicleLocationService;
    private final VehicleLocationMapper vehicleLocationMapper;

    public VehicleAggregatorController(
            BusMetadataService busMetadataService,
            BusFareService busFareService,
            BusWithFareMapper busWithFareMapper,
            AggregatedVehicleLocationService aggregatedVehicleLocationService,
            VehicleLocationMapper vehicleLocationMapper
    ) {
        this.busMetadataService = busMetadataService;
        this.busFareService = busFareService;
        this.busWithFareMapper = busWithFareMapper;
        this.aggregatedVehicleLocationService = aggregatedVehicleLocationService;
        this.vehicleLocationMapper = vehicleLocationMapper;
    }

    @Override
    public Flux<BusWithFare> busesGet(ServerWebExchange exchange) {
        return busMetadataService.getAllBusesMetadata()
                .flatMap(busMetadata -> {
                    Mono<BusFare> busFareMono = busFareService.getFareById(busMetadata.id());
                    return busFareMono.map(busFare -> busWithFareMapper.toDto(busMetadata, busFare));
                });
    }

    @Override
    public Flux<VehicleLocation> vehicleLocationStreamGet(ServerWebExchange exchange) {
        return aggregatedVehicleLocationService.getVehicleLocations()
                .map(vehicleLocationMapper::toDto);
    }
}
