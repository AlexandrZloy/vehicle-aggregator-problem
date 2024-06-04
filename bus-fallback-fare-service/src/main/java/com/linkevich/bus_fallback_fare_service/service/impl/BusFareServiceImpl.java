package com.linkevich.bus_fallback_fare_service.service.impl;

import com.linkevich.bus_fallback_fare_service.service.BusFareService;
import com.linkevich.bus_fallback_fare_service.service.model.BusFare;
import org.springframework.stereotype.Service;

@Service
public class BusFareServiceImpl implements BusFareService {

    private final static Long MOCK_FARE_BASE = 200L;

    @Override
    public BusFare getFareById(Long id) {
        return new BusFare(
                id,
                MOCK_FARE_BASE + Math.abs(id) % 100
        );
    }
}
