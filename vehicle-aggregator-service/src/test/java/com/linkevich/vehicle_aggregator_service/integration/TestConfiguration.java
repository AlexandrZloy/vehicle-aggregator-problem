package com.linkevich.vehicle_aggregator_service.integration;

import com.linkevich.vehicle_aggregator_service.service.AggregatedVehicleLocationService;
import com.linkevich.vehicle_aggregator_service.service.VehicleLocationService;
import com.linkevich.vehicle_aggregator_service.service.impl.AggregatedVehicleLocationServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class TestConfiguration {

    @MockBean(name = "mockedVehicleLocationService")
    private VehicleLocationService vehicleLocationService;

    @Bean
    @Primary
    public AggregatedVehicleLocationService aggregatedVehicleLocationService(
            @Qualifier("mockedVehicleLocationService") VehicleLocationService vehicleLocationService
    ) {
        return new AggregatedVehicleLocationServiceImpl(List.of(vehicleLocationService));
    }
}
