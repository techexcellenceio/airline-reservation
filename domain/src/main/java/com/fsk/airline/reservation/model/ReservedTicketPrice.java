package com.fsk.airline.reservation.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class ReservedTicketPrice {

	private final BigDecimal distanceInKm;
	private final LocalDate departureDate;
	private final CityName from;
	private final CityName to;

	private static final BigDecimal DEFAULT_COEFFICIENT = new BigDecimal("1");
	private static final BigDecimal MONDAY_COEFFICIENT = new BigDecimal("50.7");

	public ReservedTicketPrice(double distanceInKm, LocalDate departureDate, CityName from, CityName to) {
		this.distanceInKm = BigDecimal.valueOf(distanceInKm);
		this.departureDate = departureDate;
		this.from = from;
		this.to = to;
	}

	public BigDecimal getPrice() {
		BigDecimal coefficient = determinePriceCoefficient();
		return distanceInKm.multiply(coefficient).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal determinePriceCoefficient() {
		return switch (departureDate.getDayOfWeek()) {
			case MONDAY -> MONDAY_COEFFICIENT;
			case TUESDAY -> distanceSubtractedBy10();
			case WEDNESDAY -> sumOfTheLengthOfTheTwoCities();
			default -> DEFAULT_COEFFICIENT;
		};
	}

	private BigDecimal sumOfTheLengthOfTheTwoCities() {
		int fromCityNameLength = from.getCityNameLength();
		int toCityNameLength = to.getCityNameLength();
		return BigDecimal.valueOf(fromCityNameLength).add(BigDecimal.valueOf(toCityNameLength));
	}

	private BigDecimal distanceSubtractedBy10() {
		return distanceInKm.subtract(new BigDecimal(10));
	}
}
