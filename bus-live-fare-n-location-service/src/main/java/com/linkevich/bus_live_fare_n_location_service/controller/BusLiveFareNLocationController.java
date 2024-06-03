package com.linkevich.bus_live_fare_n_location_service.controller;

import com.linkevich.bus_live_fare_n_location_service.openapi.api.BusesApi;
import com.linkevich.bus_live_fare_n_location_service.openapi.api.LocationStreamApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusLiveFareNLocationController implements BusesApi, LocationStreamApi {
}
