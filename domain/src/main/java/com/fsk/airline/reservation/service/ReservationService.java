package com.fsk.airline.reservation.service;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.api.SearchReservedTicketUseCase;
import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;
import com.fsk.airline.reservation.spi.ReservedTickets;

import java.util.List;
import java.util.Optional;

public class ReservationService implements ReserveTicketUseCase, SearchReservedTicketUseCase {

	private final ReservedTickets reservedTickets;

	public ReservationService(ReservedTickets reservedTickets) {
		this.reservedTickets = reservedTickets;
	}

	@Override
	public ReservedTicket reserveTicket(String customerLogin, String cityFromValue, String cityToValue) {
		City cityFrom = new City(cityFromValue);
		City cityTo = new City(cityToValue);

		checkCityExists(cityFromValue);
		checkCityExists(cityToValue);

		ReservedTicket reservedTicket = new ReservedTicket(customerLogin, cityFrom, cityTo);
		reservedTickets.save(reservedTicket);
		return reservedTicket;
	}

	private void checkCityExists(String city) {
		List<String> knownCities = List.of("Paris", "New York", "Berlin", "Prague");
		if(!knownCities.contains(city)) {
			throw new IllegalArgumentException("Unknown city " + city);
		}
	}

	@Override
	public Optional<ReservedTicket> findReservedTicket(String customerLogin, TicketNumber ticketNbr) {

		return reservedTickets.findOne(customerLogin, ticketNbr);
	}
}
