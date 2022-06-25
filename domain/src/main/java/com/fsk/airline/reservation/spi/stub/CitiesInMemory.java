package com.fsk.airline.reservation.spi.stub;

import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.spi.Cities;

import java.util.List;
import java.util.Optional;

public class CitiesInMemory implements Cities {
	
	private final List<City> knownCities = List.of(
			City.PARIS,
			City.NEW_YORK,
			City.BERLIN,
			City.PRAGUE
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
		if(cityFrom.equals(City.PARIS) && cityTo.equals(City.NEW_YORK)) {
			return 5837.20;
		}
		if(cityFrom.equals(City.PARIS) && cityTo.equals(City.BERLIN)) {
			return 878.08;
		}
		if(cityFrom.equals(City.PARIS) && cityTo.equals(City.PRAGUE)) {
			return 881.77;
		}
		if(cityFrom.equals(City.NEW_YORK) && cityTo.equals(City.BERLIN)) {
			return 6385.28;
		}
		if(cityFrom.equals(City.NEW_YORK) && cityTo.equals(City.PRAGUE)) {
			return 6570.78;
		}
		if(cityFrom.equals(City.BERLIN) && cityTo.equals(City.PRAGUE)) {
			return 279.76;
		}
		throw new IllegalStateException("No Distance registered between cities " + cityFrom + " and " + cityTo);
	}
}
