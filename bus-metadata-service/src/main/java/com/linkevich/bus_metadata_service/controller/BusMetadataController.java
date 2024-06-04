package com.linkevich.bus_metadata_service.controller;

import com.linkevich.bus_metadata_service.mapper.BusMetadataMapper;
import com.linkevich.bus_metadata_service.openapi.api.MetadataApi;
import com.linkevich.bus_metadata_service.openapi.model.BusMetadata;
import com.linkevich.bus_metadata_service.service.BusMetadataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BusMetadataController implements MetadataApi {

    private final BusMetadataService busMetadataService;
    private final BusMetadataMapper busMetadataMapper;

    public BusMetadataController(
            BusMetadataService busMetadataService,
            BusMetadataMapper busMetadataMapper
    ) {
        this.busMetadataService = busMetadataService;
        this.busMetadataMapper = busMetadataMapper;
    }

    @Override
    public ResponseEntity<List<BusMetadata>> metadataGet() {
        return ResponseEntity.ok(
                busMetadataService.getAllBusesMetadata().stream()
                .map(busMetadataMapper::toDto)
                .toList()
        );
    }
}
