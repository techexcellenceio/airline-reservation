package com.fsk.airline.reservation.spi;

import com.fsk.airline.reservation.model.City;

import java.util.Optional;

public interface Cities {

	Optional<City> findOne(String cityName);
}
