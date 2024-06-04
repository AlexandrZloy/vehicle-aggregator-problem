package com.linkevich.bus_live_fare_n_location_service.service.impl;

import com.linkevich.bus_live_fare_n_location_service.service.BusLocationService;
import com.linkevich.bus_live_fare_n_location_service.service.model.BusLocation;
import com.linkevich.bus_live_fare_n_location_service.service.model.Location;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class BusLocationServiceImpl implements BusLocationService {

    private final Map<Long, LocationSalt> idToSaltMap = new HashMap<>();
    private final Random random = new Random();

    @Override
    public BusLocation getBusLocation(Long id) {
        LocationSalt salt = idToSaltMap.computeIfAbsent(
                id,
                k -> new LocationSalt(random.nextLong(0L, 10000L))
        );
        double currentAngle = Math.toRadians(Instant.now().getEpochSecond() % 360);
        Location location = new Location(
                Math.round(salt.radius * Math.cos(currentAngle)),
                Math.round(salt.radius * Math.sin(currentAngle))
        );
        return new BusLocation(id, location);
    }

    private record LocationSalt(
            Long radius
    ) {}
}
