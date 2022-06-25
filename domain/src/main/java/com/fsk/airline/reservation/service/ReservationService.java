package com.fsk.airline.reservation.service;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.api.SearchReservedTicketUseCase;
import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;
import com.fsk.airline.reservation.spi.Cities;
import com.fsk.airline.reservation.spi.ReservedTickets;

import java.util.Optional;

public class ReservationService implements ReserveTicketUseCase, SearchReservedTicketUseCase {

	private final ReservedTickets reservedTickets;
	private final Cities cities;

	public ReservationService(ReservedTickets reservedTickets, Cities cities) {
		this.reservedTickets = reservedTickets;
		this.cities = cities;
	}

	@Override
	public ReservedTicket reserveTicket(String customerLogin, String cityFromValue, String cityToValue) {
		City cityFrom = findCity(cityFromValue);
		City cityTo = findCity(cityToValue);

		ReservedTicket reservedTicket = new ReservedTicket(customerLogin, cityFrom, cityTo);
		reservedTickets.save(reservedTicket);
		return reservedTicket;
	}

	private City findCity(String cityValue) {
		return cities.findOne(cityValue).orElseThrow(() -> new IllegalArgumentException("Unknown city " + cityValue));
	}

	@Override
	public Optional<ReservedTicket> findReservedTicket(String customerLogin, TicketNumber ticketNbr) {

		return reservedTickets.findOne(customerLogin, ticketNbr);
	}
}
