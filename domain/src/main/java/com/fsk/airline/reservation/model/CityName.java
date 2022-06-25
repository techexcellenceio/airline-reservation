package com.fsk.airline.reservation.model;

import java.util.Objects;

public class CityName {

	private final String value;

	private CityName(String value) {
		checkCityNameIsNotEmpty(value);
		this.value = value;
	}

	public static CityName of(String name) {
		return new CityName(name);
	}

	private void checkCityNameIsNotEmpty(String name) {
		if (name == null || "".equals(name)) {
			throw new IllegalArgumentException("City name cannot be empty");
		}
	}

	int getCityNameLength() {
		return value.length();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CityName cityName = (CityName) o;
		return Objects.equals(value, cityName.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
