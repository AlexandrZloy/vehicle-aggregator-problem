package com.linkevich.vehicle_aggregator_service.service.impl;

import com.linkevich.vehicle_aggregator_service.service.BusFareService;
import com.linkevich.vehicle_aggregator_service.service.model.BusFare;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.logging.Level;
import java.util.logging.Logger;

@Primary
@Service
public class BusFareServiceRobustImpl implements BusFareService {

    private static final Logger LOGGER = Logger.getLogger(BusFareServiceRobustImpl.class.getName());
    public final BusFareService busFareLiveService;
    public final BusFareService busFareFallbackService;

    public BusFareServiceRobustImpl(
            @Qualifier("busFareLiveService") BusFareService busFareLiveService,
            @Qualifier("busFareFallbackService") BusFareService busFareFallbackService
    ) {
        this.busFareLiveService = busFareLiveService;
        this.busFareFallbackService = busFareFallbackService;
    }

    @Override
    public Mono<BusFare> getFareById(Long id) {
        return busFareLiveService.getFareById(id)
                .onErrorResume(throwable -> {
                    LOGGER.log(Level.WARNING, throwable.getMessage());
                    return busFareFallbackService.getFareById(id);
                });
    }
}
