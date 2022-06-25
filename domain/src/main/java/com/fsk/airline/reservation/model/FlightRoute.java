package com.fsk.airline.reservation.model;

import java.util.Objects;

import static java.lang.Math.*;

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

	public double getDistance() {
		// Convert the latitudes and longitudes from degree to radians.
		double pointALatitudeInRadians = toRadians(pointA.getLatitude());
		double pointALongitudeInRadians = toRadians(pointA.getLongitude());
		double pointBLatitudeInRadians = toRadians(pointB.getLatitude());
		double pointBLongitudeInRadians = toRadians(pointB.getLongitude());

		// Haversine Formula
		double dlong = pointBLongitudeInRadians - pointALongitudeInRadians;
		double dlat = pointBLatitudeInRadians - pointALatitudeInRadians;

		double ans = pow(sin(dlat / 2), 2) +
				cos(pointALatitudeInRadians) * cos(pointBLatitudeInRadians) *
						pow(sin(dlong / 2), 2);

		ans = 2 * asin(sqrt(ans));

		// Radius of Earth in
		// Kilometers, radiusOfEarth = 6371
		// Use radiusOfEarth = 3956 for miles
		double radiusOfEarth = 6371;

		return ans * radiusOfEarth;
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

