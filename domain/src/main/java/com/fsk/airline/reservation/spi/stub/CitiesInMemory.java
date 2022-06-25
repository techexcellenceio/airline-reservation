package com.fsk.airline.reservation.spi.stub;

import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.spi.Cities;

import java.util.List;
import java.util.Optional;

public class CitiesInMemory implements Cities {
	
	private final List<City> knownCities = List.of(
			City.of("Paris"),
			City.of("New York"),
			City.of("Berlin"),
			City.of("Prague")
	);
	
	@Override
	public Optional<City> findOne(String cityName) {
		City city = City.of(cityName);
		if (knownCities.contains(city)) {
			return Optional.of(city);
		}
		return Optional.empty();
	}
}
