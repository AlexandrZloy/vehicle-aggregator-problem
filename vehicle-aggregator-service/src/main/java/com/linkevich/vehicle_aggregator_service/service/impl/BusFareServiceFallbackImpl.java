package com.linkevich.vehicle_aggregator_service.service.impl;

import com.linkevich.vehicle_aggregator_service.mapper.BusFareMapper;
import com.linkevich.vehicle_aggregator_service.service.BusFareService;
import com.linkevich.vehicle_aggregator_service.service.model.BusFare;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service("busFareFallbackService")
public class BusFareServiceFallbackImpl implements BusFareService {

    private final WebClient webClient;
    private final BusFareMapper busFareMapper;

    public BusFareServiceFallbackImpl(
            @Value("${service.bus_fallback_fare_service.url}") String serviceUrl,
            BusFareMapper busFareMapper
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(serviceUrl)
                .build();
        this.busFareMapper = busFareMapper;
    }

    @Override
    public Mono<BusFare> getFareById(Long id) {
        return webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.pathSegment("buses", "{id}", "fare").build(id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(com.linkevich.bus_fallback_fare_service.openapi.model.BusFare.class)
                .map(busFareMapper::toModel);
    }
}
