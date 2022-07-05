package com.fsk.airline.reservation.model;

import java.util.Objects;

public class City {

	private final CityName name;
	private final double latitude;
	private final double longitude;

	private City(String name, double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = CityName.of(name);
	}

	public static City of(String name, double latitude, double longitude) {
		return new City(name, latitude, longitude);
	}

	@Override
	public String toString() {
		return "City{" +
				"name=" + name +
				", latitude=" + latitude +
				", longitude=" + longitude +
				'}';
	}

	double getLatitude() {
		return latitude;
	}

	double getLongitude() {
		return longitude;
	}

	public CityName getName() {
		return name;
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
}
