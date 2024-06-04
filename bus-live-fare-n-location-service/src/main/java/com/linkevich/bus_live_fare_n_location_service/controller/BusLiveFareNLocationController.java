package com.linkevich.bus_live_fare_n_location_service.controller;

import com.linkevich.bus_live_fare_n_location_service.mapper.BusFareMapper;
import com.linkevich.bus_live_fare_n_location_service.mapper.BusLocationMapper;
import com.linkevich.bus_live_fare_n_location_service.openapi.api.BusesApi;
import com.linkevich.bus_live_fare_n_location_service.openapi.api.LocationStreamApi;
import com.linkevich.bus_live_fare_n_location_service.openapi.model.BusFare;
import com.linkevich.bus_live_fare_n_location_service.openapi.model.BusLocation;
import com.linkevich.bus_live_fare_n_location_service.service.BusFareService;
import com.linkevich.bus_live_fare_n_location_service.service.BusLocationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class BusLiveFareNLocationController implements BusesApi, LocationStreamApi {

    private final static Integer LOCATION_STREAM_LIMIT = 5;
    private final BusFareService busFareService;
    private final BusFareMapper busFareMapper;
    private final BusLocationService busLocationService;
    private final BusLocationMapper busLocationMapper;
    private final Integer busCount;
    private final AtomicInteger fareRequestCounter = new AtomicInteger(0);
    private final AtomicInteger locationRequestCounter = new AtomicInteger(0);

    public BusLiveFareNLocationController(
            BusFareService busFareService,
            BusFareMapper busFareMapper,
            BusLocationService busLocationService,
            BusLocationMapper busLocationMapper,
            @Value("${bus.location.service.count}") Integer busCount
    ) {
        this.busFareService = busFareService;
        this.busFareMapper = busFareMapper;
        this.busLocationService = busLocationService;
        this.busLocationMapper = busLocationMapper;
        this.busCount = busCount;
    }

    @Override
    public Mono<BusFare> busesIdFareGet(Long id, ServerWebExchange exchange) {
        if (fareRequestCounter.getAndIncrement() % 2 != 0) {
            throw new RuntimeException("50/50 - try next time");
        }
        return Mono.fromCallable(() -> busFareMapper.toDto(busFareService.getFareById(id)));
    }

    @Override
    public Flux<BusLocation> locationStreamGet(ServerWebExchange exchange) {
        return Flux.interval(Duration.ofSeconds(1L))
                .map(i -> {
                    if (i >= LOCATION_STREAM_LIMIT) {
                        throw new RuntimeException("No more locations for this request!");
                    }
                    return busLocationMapper.toDto(
                            busLocationService.getBusLocation(
                                    1L + (locationRequestCounter.getAndIncrement() % busCount)
                            )
                    );
                });
    }
}
