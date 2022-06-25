package com.fsk.airline.reservation.command;

import java.time.LocalDate;

public class ReserveTicketRequestBuilder {
	private String customerLogin;
	private String cityFrom;
	private String cityTo;
	private LocalDate departureDate = LocalDate.now();

	public ReserveTicketRequestBuilder customerLogin(String customerLogin) {
		this.customerLogin = customerLogin;
		return this;
	}

	public ReserveTicketRequestBuilder cityFrom(String cityFrom) {
		this.cityFrom = cityFrom;
		return this;
	}

	public ReserveTicketRequestBuilder cityTo(String cityTo) {
		this.cityTo = cityTo;
		return this;
	}

	public ReserveTicketRequestBuilder departureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
		return this;
	}

	public ReserveTicketRequest build() {
		return new ReserveTicketRequest(customerLogin, cityFrom, cityTo, departureDate);
	}
}