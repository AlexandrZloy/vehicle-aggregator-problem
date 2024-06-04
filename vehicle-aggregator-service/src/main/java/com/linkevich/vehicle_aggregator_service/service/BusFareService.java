package com.linkevich.vehicle_aggregator_service.service;

import com.linkevich.vehicle_aggregator_service.service.model.BusFare;
import reactor.core.publisher.Mono;

public interface BusFareService {

    Mono<BusFare> getFareById(Long id);
}
