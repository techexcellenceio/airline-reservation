package com.fsk.airline.reservation.model;

public class ReservedTicket {

	private final City from;
	private final City to;

	public ReservedTicket(City from, City to) {
		this.from = from;
		this.to = to;
	}

	public City getFrom() {
		return from;
	}

	public City getTo() {
		return to;
	}
}
