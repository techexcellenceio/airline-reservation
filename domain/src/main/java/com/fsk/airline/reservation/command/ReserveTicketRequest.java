package com.fsk.airline.reservation.command;

import java.time.LocalDate;

public class ReserveTicketRequest {

	private final String customerLogin;
	private final String cityFrom;
	private final String cityTo;
	private final LocalDate departureDate;

	ReserveTicketRequest(String customerLogin, String cityFrom, String cityTo, LocalDate departureDate) {
		this.customerLogin = customerLogin;
		this.cityFrom = cityFrom;
		this.cityTo = cityTo;
		this.departureDate = departureDate;
	}

	public String getCustomerLogin() {
		return customerLogin;
	}

	public String getCityFrom() {
		return cityFrom;
	}

	public String getCityTo() {
		return cityTo;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}
}
