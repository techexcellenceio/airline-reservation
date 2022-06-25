package com.fsk.airline.reservation.model;

public class ReservedTicket {

	private final TicketNumber ticketNumber;
	private final String customerLogin;
	private final City from;
	private final City to;

	public ReservedTicket(String customerLogin, City from, City to) {
		this.ticketNumber = TicketNumber.generate();
		this.customerLogin = customerLogin;
		this.from = from;
		this.to = to;
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
}
