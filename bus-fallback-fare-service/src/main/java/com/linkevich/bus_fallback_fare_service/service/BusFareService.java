package com.linkevich.bus_fallback_fare_service.service;

import com.linkevich.bus_fallback_fare_service.service.model.BusFare;

public interface BusFareService {

    BusFare getFareById(Long id);
}
