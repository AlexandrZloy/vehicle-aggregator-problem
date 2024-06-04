package com.linkevich.bus_metadata_service.service.impl;

import com.linkevich.bus_metadata_service.mapper.BusMetadataMapper;
import com.linkevich.bus_metadata_service.repository.BusMetadataRepository;
import com.linkevich.bus_metadata_service.service.BusMetadataService;
import com.linkevich.bus_metadata_service.service.model.BusMetadata;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusMetadataServiceImpl implements BusMetadataService {

    private final BusMetadataRepository busMetadataRepository;
    private final BusMetadataMapper busMetadataMapper;

    public BusMetadataServiceImpl(
            BusMetadataRepository busMetadataRepository,
            BusMetadataMapper busMetadataMapper
    ) {
        this.busMetadataRepository = busMetadataRepository;
        this.busMetadataMapper = busMetadataMapper;
    }

    @Override
    public List<BusMetadata> getAllBusesMetadata() {
        return busMetadataRepository.findAll().stream()
                .map(busMetadataMapper::toModel)
                .toList();
    }
}
