insert into bus_metadata (id, license_plate) SELECT x, 'BUS' || x FROM generate_series(1,${busCount});
