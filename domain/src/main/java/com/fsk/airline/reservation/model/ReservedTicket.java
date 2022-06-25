package com.fsk.airline.reservation.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class ReservedTicket {

	private final TicketNumber ticketNumber;
	private final String customerLogin;
	private final City from;
	private final City to;
	private final LocalDate departureDate;

	public ReservedTicket(String customerLogin, City from, City to, LocalDate departureDate) {
		checkDestinationAndDepartureAreNotTheSame(from, to);
		checkDepartureDateIsPresent(departureDate);
		this.ticketNumber = TicketNumber.generate();
		this.customerLogin = customerLogin;
		this.from = from;
		this.to = to;
		this.departureDate = departureDate;
	}

	private void checkDestinationAndDepartureAreNotTheSame(City from, City to) {
		if (from.equals(to)) {
			throw new IllegalArgumentException("Departure and destination cities cannot be the same");
		}
	}

	private void checkDepartureDateIsPresent(LocalDate departureDate) {
		if (departureDate == null) {
			throw new IllegalArgumentException("Departure date is mandatory");
		}
	}

	public CityName getFrom() {
		return from.getName();
	}

	public CityName getTo() {
		return to.getName();
	}

	public TicketNumber getNumber() {
		return ticketNumber;
	}

	public String getCustomerLogin() {
		return customerLogin;
	}

	public double getDistanceInKm() {
		return FlightRoute.of(from, to).getDistance();
	}

	public BigDecimal getPrice() {
		BigDecimal distance = BigDecimal.valueOf(getDistanceInKm());
		BigDecimal coefficient = determinePriceCoefficient();
		return distance.multiply(coefficient).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal determinePriceCoefficient() {
		return switch (departureDate.getDayOfWeek()) {
			case MONDAY -> new BigDecimal("50.7");
			case TUESDAY -> distanceSubtractedBy10();
			case WEDNESDAY -> sumOfTheLengthOfTheTwoCities();
			default -> new BigDecimal("1");
		};
	}

	private BigDecimal sumOfTheLengthOfTheTwoCities() {
		return BigDecimal.valueOf(from.getCityNameLength()).add(BigDecimal.valueOf(to.getCityNameLength()));
	}

	private BigDecimal distanceSubtractedBy10() {
		return BigDecimal.valueOf(getDistanceInKm()).subtract(new BigDecimal(10));
	}
}
