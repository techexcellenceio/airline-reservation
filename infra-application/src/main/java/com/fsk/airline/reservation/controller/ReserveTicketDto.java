package com.fsk.airline.reservation.controller;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ReserveTicketDto {

	private String cityFrom;
	private String cityTo;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate departureDate;

	public String getCityFrom() {
		return cityFrom;
	}

	public void setCityFrom(String cityFrom) {
		this.cityFrom = cityFrom;
	}

	public String getCityTo() {
		return cityTo;
	}

	public void setCityTo(String cityTo) {
		this.cityTo = cityTo;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}
}
