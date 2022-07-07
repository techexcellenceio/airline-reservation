package com.fsk.airline.reservation.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class ReservedTicketPrice {

	private final BigDecimal distanceInKm;
	private final LocalDate departureDate;
	private final CityName from;
	private final CityName to;
	private final int numberOfGuests;

	private static final BigDecimal DEFAULT_COEFFICIENT = new BigDecimal("1");
	private static final BigDecimal MONDAY_COEFFICIENT = new BigDecimal("50.7");
	private static final BigDecimal DISCOUNT_PER_GUEST = new BigDecimal("0.9");

	public ReservedTicketPrice(double distanceInKm, LocalDate departureDate, CityName from, CityName to, int numberOfGuests) {
		this.distanceInKm = BigDecimal.valueOf(distanceInKm);
		this.departureDate = departureDate;
		this.from = from;
		this.to = to;
		this.numberOfGuests = numberOfGuests;
	}

	public BigDecimal getPrice() {
		BigDecimal coefficient = determinePriceCoefficient();
		BigDecimal precisePrice = distanceInKm.multiply(coefficient);
		precisePrice = applyDiscountForGuests(precisePrice);
		return roundToTwoDigits(precisePrice);
	}

	private BigDecimal applyDiscountForGuests(BigDecimal precisePrice) {
		for (int numberOfDiscounts = 0 ; numberOfDiscounts < numberOfGuests ; numberOfDiscounts++) {
			precisePrice = precisePrice.multiply(DISCOUNT_PER_GUEST);
		}
		return precisePrice;
	}

	private BigDecimal roundToTwoDigits(BigDecimal precisePrice) {
		return precisePrice.setScale(2, RoundingMode.HALF_UP);
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
