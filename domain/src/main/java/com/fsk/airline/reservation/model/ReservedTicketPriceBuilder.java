package com.fsk.airline.reservation.model;

import java.time.LocalDate;

public class ReservedTicketPriceBuilder {

	private double distanceInKm;
	private LocalDate departureDate;
	private CityName from;
	private CityName to;
	private int numberOfGuests;

	public ReservedTicketPriceBuilder reservedTicket(ReservedTicket reservedTicket) {
		this.distanceInKm = reservedTicket.getDistanceInKm();
		this.departureDate = reservedTicket.getDepartureDate();
		this.from = reservedTicket.getFrom();
		this.to = reservedTicket.getTo();
		return this;
	}

	public ReservedTicketPriceBuilder numberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
		return this;
	}

	public ReservedTicketPrice build() {
		return new ReservedTicketPrice(distanceInKm, departureDate, from, to, numberOfGuests);
	}
}