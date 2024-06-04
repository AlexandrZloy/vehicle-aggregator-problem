package com.linkevich.vehicle_aggregator_service.service.impl;

import com.linkevich.vehicle_aggregator_service.mapper.BusMetadataMapper;
import com.linkevich.vehicle_aggregator_service.service.BusMetadataService;
import com.linkevich.vehicle_aggregator_service.service.model.BusMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class BusMetadataServiceImpl implements BusMetadataService {

    private final WebClient webClient;
    private final BusMetadataMapper busMetadataMapper;

    public BusMetadataServiceImpl(
            @Value("${service.bus_metadata_service.url}") String serviceUrl,
            BusMetadataMapper busMetadataMapper
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(serviceUrl)
                .build();
        this.busMetadataMapper = busMetadataMapper;
    }

    @Override
    public Flux<BusMetadata> getAllBusesMetadata() {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.pathSegment("metadata").build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(com.linkevich.bus_metadata_service.openapi.model.BusMetadata.class)
                .map(busMetadataMapper::toModel);
    }
}
