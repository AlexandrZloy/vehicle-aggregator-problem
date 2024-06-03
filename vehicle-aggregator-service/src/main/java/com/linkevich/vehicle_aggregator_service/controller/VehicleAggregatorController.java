package com.linkevich.vehicle_aggregator_service.controller;

import com.linkevich.vehicle_aggregator_service.openapi.api.BusesApi;
import com.linkevich.vehicle_aggregator_service.openapi.api.VehicleLocationStreamApi;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleAggregatorController implements BusesApi, VehicleLocationStreamApi {

}
