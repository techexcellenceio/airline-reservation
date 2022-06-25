package com.fsk.airline.reservation.service;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.api.SearchReservedTicketUseCase;
import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.spi.ReservedTickets;

import java.util.Optional;
import java.util.UUID;

public class ReservationService implements ReserveTicketUseCase, SearchReservedTicketUseCase {

	private final ReservedTickets reservedTickets;

	public ReservationService(ReservedTickets reservedTickets) {
		this.reservedTickets = reservedTickets;
	}

	@Override
	public ReservedTicket reserveTicket(String customerLogin, String cityFrom, String cityTo) {

		String ticketNumber = UUID.randomUUID().toString();
		ReservedTicket reservedTicket = new ReservedTicket(ticketNumber, new City(cityFrom), new City(cityTo));
		reservedTickets.save(reservedTicket);
		return reservedTicket;
	}

	@Override
	public Optional<ReservedTicket> findReservedTicket(String customerLogin, String ticketNbr) {

		return reservedTickets.findOne(ticketNbr);
	}
}
