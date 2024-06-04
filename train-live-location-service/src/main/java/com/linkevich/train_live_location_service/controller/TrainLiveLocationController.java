package com.linkevich.train_live_location_service.controller;

import com.linkevich.train_live_location_service.mapper.TrainLocationMapper;
import com.linkevich.train_live_location_service.openapi.api.LocationStreamApi;
import com.linkevich.train_live_location_service.openapi.model.TrainLocation;
import com.linkevich.train_live_location_service.service.TrainLocationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class TrainLiveLocationController implements LocationStreamApi {

    private final TrainLocationService trainLocationService;
    private final TrainLocationMapper trainLocationMapper;
    private final Integer trainsCount;

    public TrainLiveLocationController(
            TrainLocationService trainLocationService,
            TrainLocationMapper trainLocationMapper,
            @Value("${train.location.service.count}") Integer trainsCount
    ) {
        this.trainLocationService = trainLocationService;
        this.trainLocationMapper = trainLocationMapper;
        this.trainsCount = trainsCount;
    }

    @Override
    public Flux<TrainLocation> locationStreamGet(ServerWebExchange exchange) {
        return Flux.interval(Duration.ofSeconds(1L))
                .map(i -> trainLocationMapper.toDto(trainLocationService.getTrainLocation(1L + (i % trainsCount))));
    }
}
