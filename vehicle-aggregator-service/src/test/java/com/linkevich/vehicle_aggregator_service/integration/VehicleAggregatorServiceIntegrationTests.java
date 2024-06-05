package com.linkevich.vehicle_aggregator_service.integration;

import com.linkevich.vehicle_aggregator_service.mapper.BusWithFareMapper;
import com.linkevich.vehicle_aggregator_service.mapper.VehicleLocationMapper;
import com.linkevich.vehicle_aggregator_service.openapi.model.BusWithFare;
import com.linkevich.vehicle_aggregator_service.service.AggregatedVehicleLocationService;
import com.linkevich.vehicle_aggregator_service.service.BusFareService;
import com.linkevich.vehicle_aggregator_service.service.BusMetadataService;
import com.linkevich.vehicle_aggregator_service.service.VehicleLocationService;
import com.linkevich.vehicle_aggregator_service.service.model.BusFare;
import com.linkevich.vehicle_aggregator_service.service.model.BusMetadata;
import com.linkevich.vehicle_aggregator_service.service.model.Location;
import com.linkevich.vehicle_aggregator_service.service.model.VehicleLocation;
import com.linkevich.vehicle_aggregator_service.service.model.VehicleType;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class VehicleAggregatorServiceIntegrationTests {

    @MockBean
    private BusMetadataService busMetadataService;
    @MockBean
    private BusFareService busFareService;
    @Autowired
    private AggregatedVehicleLocationService aggregatedVehicleLocationService;

    @Autowired
    @Qualifier("mockedVehicleLocationService")
    private VehicleLocationService vehicleLocationService;
    @Autowired
    private BusWithFareMapper busWithFareMapper;
    @Autowired
    private VehicleLocationMapper vehicleLocationMapper;
    @Autowired
    private ApplicationContext context;

    private WebTestClient client;

    @BeforeEach
    void beforeEach() {
        client = WebTestClient.bindToApplicationContext(context)
                .build();
    }

    @Test
    void buses_endpoint_happy_path() {

        Collection<BusMetadata> expectedMetadata = List.of(
                new BusMetadata(1L, "BUS1"),
                new BusMetadata(2L, "BUS2")
        );
        Map<Long, BusFare> expectedFare = Stream.of(
                new BusFare(1L, 201L),
                new BusFare(2L, 202L)
        ).collect(Collectors.toMap(BusFare::id, Function.identity()));

        when(busMetadataService.getAllBusesMetadata())
                .thenReturn(Flux.fromIterable(expectedMetadata));

        when(busFareService.getFareById(any(Long.class)))
                .thenAnswer(invocationOnMock -> Mono.just(expectedFare.get(invocationOnMock.getArguments()[0])));

        client.get()
                .uri(uriBuilder ->
                        uriBuilder.pathSegment("buses").build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(BusWithFare.class)
                .consumeWith(result -> {
                    Assertions.assertNotNull(result.getResponseBody());
                    Assertions.assertTrue(
                            CollectionUtils.isEqualCollection(
                                    result.getResponseBody(),
                                    expectedMetadata.stream()
                                            .map(metadata -> busWithFareMapper.toDto(metadata, expectedFare.get(metadata.id())))
                                            .toList()
                            )
                    );
                });
    }

    @Test
    void vehicle_location_stream_endpoint_happy_path() {

        Collection<VehicleLocation> expected = List.of(
                new VehicleLocation(1L, VehicleType.BUS, new Location(1L, 1L)),
                new VehicleLocation(2L, VehicleType.TRAIN, new Location(2L, 2L))
        );

        when(aggregatedVehicleLocationService.getVehicleLocations())
                .thenReturn(Flux.fromIterable(expected));

        client.get()
                .uri(uriBuilder ->
                        uriBuilder.pathSegment("vehicle-location-stream").build())
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(com.linkevich.vehicle_aggregator_service.openapi.model.VehicleLocation.class)
                .consumeWith(result -> {
                    Assertions.assertNotNull(result.getResponseBody());
                    Assertions.assertTrue(
                            CollectionUtils.isEqualCollection(
                                    result.getResponseBody(),
                                    expected.stream()
                                            .map(vehicleLocationMapper::toDto)
                                            .toList()
                            )
                    );
                });
    }

    @Test
    void vehicle_location_stream_endpoint_happy_path_with_retry() {

        List<VehicleLocation> expected = List.of(
                new VehicleLocation(1L, VehicleType.BUS, new Location(1L, 1L)),
                new VehicleLocation(2L, VehicleType.TRAIN, new Location(2L, 2L)),
                new VehicleLocation(3L, VehicleType.TRAIN, new Location(3L, 3L))
        );

        AtomicInteger counter = new AtomicInteger();
        AtomicInteger index = new AtomicInteger();
        when(vehicleLocationService.getVehicleLocations())
                .thenReturn(Flux.range(0, expected.size())
                        .takeWhile(i -> index.intValue() <= expected.size())
                        .map(i -> {
                            if (counter.getAndIncrement() % 2 != 0) {
                                throw new RuntimeException("No more locations for this request!");
                            }
                            return expected.get(index.getAndIncrement());
                        })
                );

        client.get()
                .uri(uriBuilder ->
                        uriBuilder.pathSegment("vehicle-location-stream").build())
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(com.linkevich.vehicle_aggregator_service.openapi.model.VehicleLocation.class)
                .consumeWith(result -> {
                    Assertions.assertNotNull(result.getResponseBody());
                    Assertions.assertTrue(
                            CollectionUtils.isEqualCollection(
                                    result.getResponseBody(),
                                    expected.stream()
                                            .map(vehicleLocationMapper::toDto)
                                            .toList()
                            )
                    );
                });
    }
}
