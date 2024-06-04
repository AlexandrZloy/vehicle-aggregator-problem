package com.linkevich.bus_metadata_service.service;

import com.linkevich.bus_metadata_service.service.model.BusMetadata;

import java.util.List;

public interface BusMetadataService {

    List<BusMetadata> getAllBusesMetadata();
}
