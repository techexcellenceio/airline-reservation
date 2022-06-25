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

	@Override
	public double getDistanceInKm(City cityFrom, City cityTo) {
		if (cityFrom.equals(cityTo)) {
			return 0;
		}
		if(cityFrom.equals(City.of("Paris")) && cityTo.equals(City.of("New York"))) {
			return 5837.20;
		}
		if(cityFrom.equals(City.of("Paris")) && cityTo.equals(City.of("Berlin"))) {
			return 878.08;
		}
		if(cityFrom.equals(City.of("Paris")) && cityTo.equals(City.of("Prague"))) {
			return 881.77;
		}
		if(cityFrom.equals(City.of("New York")) && cityTo.equals(City.of("Berlin"))) {
			return 6385.28;
		}
		if(cityFrom.equals(City.of("New York")) && cityTo.equals(City.of("Prague"))) {
			return 6570.78;
		}
		if(cityFrom.equals(City.of("Berlin")) && cityTo.equals(City.of("Prague"))) {
			return 279.76;
		}
		throw new IllegalStateException("No Distance registered between cities " + cityFrom + " and " + cityTo);
	}
}
