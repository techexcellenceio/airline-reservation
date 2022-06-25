package com.fsk.airline.reservation.model;

public class ReservedTicket {

	private final String ticketNumber;
	private final City from;
	private final City to;

	public ReservedTicket(String ticketNumber, City from, City to) {
		this.ticketNumber = ticketNumber;
		this.from = from;
		this.to = to;
	}

	public City getFrom() {
		return from;
	}

	public City getTo() {
		return to;
	}

	public String getNumber() {
		return ticketNumber;
	}
}
