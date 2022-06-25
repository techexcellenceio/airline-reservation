package com.fsk.airline.reservation.command;

public class ReserveTicketRequest {

	private final String customerLogin;
	private final String cityFrom;
	private final String cityTo;

	ReserveTicketRequest(String customerLogin, String cityFrom, String cityTo) {
		this.customerLogin = customerLogin;
		this.cityFrom = cityFrom;
		this.cityTo = cityTo;
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
}
