package com.fsk.airline.reservation.domain.spi;

import com.fsk.airline.reservation.domain.model.City;

import java.util.Optional;

public interface Cities {

	Optional<City> findOne(String cityName);
}
