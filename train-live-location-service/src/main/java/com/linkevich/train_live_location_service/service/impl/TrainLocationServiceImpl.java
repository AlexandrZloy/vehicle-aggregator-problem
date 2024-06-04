package com.linkevich.train_live_location_service.service.impl;

import com.linkevich.train_live_location_service.service.TrainLocationService;
import com.linkevich.train_live_location_service.service.model.Location;
import com.linkevich.train_live_location_service.service.model.TrainLocation;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TrainLocationServiceImpl implements TrainLocationService {

    private final Map<Long, LocationSalt> idToSaltMap = new HashMap<>();
    private final Random random = new Random();

    @Override
    public TrainLocation getTrainLocation(Long id) {
        LocationSalt salt = idToSaltMap.computeIfAbsent(
                id,
                k -> new LocationSalt(random.nextLong(0L, 10000L))
        );
        double currentAngle = Math.toRadians(Instant.now().getEpochSecond() % 360);
        Location location = new Location(
                Math.round(salt.radius * Math.cos(currentAngle)),
                Math.round(salt.radius * Math.sin(currentAngle))
        );
        return new TrainLocation(id, location);
    }

    private record LocationSalt(
            Long radius
    ) {}
}
