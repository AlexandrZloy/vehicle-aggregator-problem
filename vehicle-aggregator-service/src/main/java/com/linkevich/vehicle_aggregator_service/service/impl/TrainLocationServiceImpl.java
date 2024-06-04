package com.linkevich.vehicle_aggregator_service.service.impl;

import com.linkevich.vehicle_aggregator_service.mapper.VehicleLocationMapper;
import com.linkevich.vehicle_aggregator_service.service.VehicleLocationService;
import com.linkevich.vehicle_aggregator_service.service.model.VehicleLocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service("trainLocationService")
public class TrainLocationServiceImpl implements VehicleLocationService {

    private final WebClient webClient;
    private final VehicleLocationMapper vehicleLocationMapper;

    public TrainLocationServiceImpl(
            @Value("${service.train_live_location_service.url}") String serviceUrl,
            VehicleLocationMapper vehicleLocationMapper
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(serviceUrl)
                .build();
        this.vehicleLocationMapper = vehicleLocationMapper;
    }

    @Override
    public Flux<VehicleLocation> getVehicleLocations() {
        return webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.pathSegment("location-stream").build())
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(com.linkevich.train_live_location_service.openapi.model.TrainLocation.class)
                .map(vehicleLocationMapper::toModel);
    }
}
