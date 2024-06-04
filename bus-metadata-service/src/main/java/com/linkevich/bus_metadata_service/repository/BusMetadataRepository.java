package com.linkevich.bus_metadata_service.repository;

import com.linkevich.bus_metadata_service.repository.model.BusMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusMetadataRepository extends JpaRepository<BusMetadataEntity, Long> {
}
