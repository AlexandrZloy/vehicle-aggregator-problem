package com.linkevich.train_live_location_service.mapper;

import com.linkevich.train_live_location_service.openapi.model.TrainLocation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainLocationMapper {

    TrainLocation toDto(com.linkevich.train_live_location_service.service.model.TrainLocation source);
}
