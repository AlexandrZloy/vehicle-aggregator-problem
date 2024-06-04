package com.linkevich.bus_fallback_fare_service.controller;

import com.linkevich.bus_fallback_fare_service.mapper.BusFareMapper;
import com.linkevich.bus_fallback_fare_service.openapi.api.BusesApi;
import com.linkevich.bus_fallback_fare_service.openapi.model.BusFare;
import com.linkevich.bus_fallback_fare_service.service.BusFareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusFallbackFareController implements BusesApi {

    private final BusFareService busFareService;
    private final BusFareMapper busFareMapper;

    public BusFallbackFareController(
            BusFareService busFareService,
            BusFareMapper busFareMapper
    ) {
        this.busFareService = busFareService;
        this.busFareMapper = busFareMapper;
    }

    @Override
    public ResponseEntity<BusFare> busesIdFareGet(Long id) {
        return ResponseEntity.ok(busFareMapper.toDto(busFareService.getFareById(id)));
    }
}
