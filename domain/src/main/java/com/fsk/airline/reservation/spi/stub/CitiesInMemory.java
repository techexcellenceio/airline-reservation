package com.fsk.airline.reservation.spi.stub;

import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.model.FlightRoute;
import com.fsk.airline.reservation.spi.Cities;

import java.util.List;
import java.util.Optional;

import static com.fsk.airline.reservation.model.City.*;

public class CitiesInMemory implements Cities {

	private final List<City> knownCities = List.of(
			PARIS,
			NEW_YORK,
			BERLIN,
			PRAGUE
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
		FlightRoute flightRoute = FlightRoute.of(cityFrom, cityTo);
		if (FlightRoute.of(PARIS, NEW_YORK).equals(flightRoute)) {
			return 5837.20;
		}
		if (FlightRoute.of(PARIS, BERLIN).equals(flightRoute)) {
			return 878.08;
		}
		if (FlightRoute.of(PARIS, PRAGUE).equals(flightRoute)) {
			return 881.77;
		}
		if (FlightRoute.of(NEW_YORK, BERLIN).equals(flightRoute)) {
			return 6385.28;
		}
		if (FlightRoute.of(NEW_YORK, PRAGUE).equals(flightRoute)) {
			return 6570.78;
		}
		if (FlightRoute.of(BERLIN, PRAGUE).equals(flightRoute)) {
			return 279.76;
		}
		throw new IllegalStateException("No Distance registered between cities " + cityFrom + " and " + cityTo);
	}
}
