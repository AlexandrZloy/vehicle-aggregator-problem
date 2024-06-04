package com.linkevich.bus_live_fare_n_location_service.service.impl;

import com.linkevich.bus_live_fare_n_location_service.service.BusFareService;
import com.linkevich.bus_live_fare_n_location_service.service.model.BusFare;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BusFareServiceImpl implements BusFareService {

    private final static Long MOCK_FARE_BASE = 100L;

    @Override
    public BusFare getFareById(Long id) {
        return new BusFare(
                id,
                MOCK_FARE_BASE + Math.abs(id) % 100
        );
    }
}
