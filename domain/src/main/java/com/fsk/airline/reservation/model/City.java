package com.fsk.airline.reservation.model;

import java.util.Objects;

public class City {

	private final String name;

	public static final City PARIS = City.of("Paris");
	public static final City BERLIN = City.of("Berlin");
	public static final City NEW_YORK = City.of("New York");
	public static final City PRAGUE = City.of("Prague");

	private City(String name) {
		checkCityNameIsNotEmpty(name);
		this.name = name;
	}

	public static City of(String name) {
		return new City(name);
	}

	private void checkCityNameIsNotEmpty(String name) {
		if (name == null || "".equals(name)) {
			throw new IllegalArgumentException("City name cannot be empty");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		City city = (City) o;
		return Objects.equals(name, city.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return "City{" +
				"name='" + name + '\'' +
				'}';
	}
}
