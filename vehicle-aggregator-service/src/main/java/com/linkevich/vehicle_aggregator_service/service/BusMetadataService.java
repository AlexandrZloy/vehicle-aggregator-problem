package com.linkevich.vehicle_aggregator_service.service;

import com.linkevich.vehicle_aggregator_service.service.model.BusMetadata;
import reactor.core.publisher.Flux;

public interface BusMetadataService {

    Flux<BusMetadata> getAllBusesMetadata();
}
