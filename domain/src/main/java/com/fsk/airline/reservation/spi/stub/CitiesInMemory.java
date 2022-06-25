package com.fsk.airline.reservation.spi.stub;

import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.model.CityName;
import com.fsk.airline.reservation.spi.Cities;

import java.util.List;
import java.util.Optional;

public class CitiesInMemory implements Cities {

	private static final City PARIS = City.of("Paris", 48.8566, 2.3522);
	private static final City BERLIN = City.of("Berlin", 52.5200, 13.4050);
	private static final City NEW_YORK = City.of("New York", 40.730610, -73.935242);
	private static final City PRAGUE = City.of("Prague", 50.0755, 14.4378);

	private final List<City> knownCities = List.of(
			PARIS,
			NEW_YORK,
			BERLIN,
			PRAGUE
	);

	@Override
	public Optional<City> findOne(String cityName) {
		return knownCities.stream()
				.filter(city -> city.getName().equals(CityName.of(cityName)))
				.findFirst();
	}

}
