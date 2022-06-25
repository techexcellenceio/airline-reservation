package com.fsk.airline.reservation.model;

public class ReservedTicket {

	private final TicketNumber ticketNumber;
	private final String customerLogin;
	private final City from;
	private final City to;
	private final double distanceInKm;

	public ReservedTicket(String customerLogin, City from, City to, double distanceInKm) {
		if (from.equals(to)) {
			throw new IllegalArgumentException("Departure and destination cities cannot be the same");
		}
		this.ticketNumber = TicketNumber.generate();
		this.customerLogin = customerLogin;
		this.from = from;
		this.to = to;
		this.distanceInKm = distanceInKm;
	}

	public City getFrom() {
		return from;
	}

	public City getTo() {
		return to;
	}

	public TicketNumber getNumber() {
		return ticketNumber;
	}

	public String getCustomerLogin() {
		return customerLogin;
	}

	public double getDistanceInKm() {
		return distanceInKm;
	}
}
