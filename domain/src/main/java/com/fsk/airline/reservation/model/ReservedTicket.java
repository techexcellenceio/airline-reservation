package com.fsk.airline.reservation.model;

import java.time.LocalDate;

public class ReservedTicket {

	private final TicketNumber ticketNumber;
	private final String customerLogin;
	private final City from;
	private final City to;
	private final LocalDate departureDate;

	public ReservedTicket(String customerLogin, City from, City to, LocalDate departureDate) {
		checkCustomerIsNotEmpty(customerLogin);
		checkDestinationAndDepartureAreNotTheSame(from, to);
		checkDepartureDateIsPresent(departureDate);
		this.ticketNumber = TicketNumber.generate();
		this.customerLogin = customerLogin;
		this.from = from;
		this.to = to;
		this.departureDate = departureDate;
	}

	private void checkCustomerIsNotEmpty(String customerLogin) {
		if (customerLogin == null || customerLogin.equals("")) {
			throw new IllegalArgumentException("Customer login is mandatory");
		}
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

	public LocalDate getDepartureDate() {
		return departureDate;
	}
}
