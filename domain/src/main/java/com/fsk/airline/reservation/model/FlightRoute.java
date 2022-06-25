package com.fsk.airline.reservation.model;

import java.util.Objects;

public class FlightRoute {

	private final City pointA;
	private final City pointB;

	private FlightRoute(City pointA, City pointB) {
		Objects.requireNonNull(pointA);
		Objects.requireNonNull(pointB);
		this.pointA = pointA;
		this.pointB = pointB;
	}

	public static FlightRoute of(City pointA, City pointB) {
		return new FlightRoute(pointA, pointB);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FlightRoute that = (FlightRoute) o;
		return (pointA.equals(that.pointA) && pointB.equals(that.pointB)) ||
				(pointA.equals(that.pointB) && pointB.equals(that.pointA));
	}

	@Override
	public int hashCode() {
		return Objects.hash(pointA, pointB);
	}
}

