package com.fsk.airline.reservation.service;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.api.SearchReservedTicketUseCase;
import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;
import com.fsk.airline.reservation.spi.ReservedTickets;

import java.util.Optional;

public class ReservationService implements ReserveTicketUseCase, SearchReservedTicketUseCase {

	private final ReservedTickets reservedTickets;

	public ReservationService(ReservedTickets reservedTickets) {
		this.reservedTickets = reservedTickets;
	}

	@Override
	public ReservedTicket reserveTicket(String customerLogin, String cityFrom, String cityTo) {

		ReservedTicket reservedTicket = new ReservedTicket(new City(cityFrom), new City(cityTo));
		reservedTickets.save(reservedTicket);
		return reservedTicket;
	}

	@Override
	public Optional<ReservedTicket> findReservedTicket(String customerLogin, TicketNumber ticketNbr) {

		return reservedTickets.findOne(ticketNbr);
	}
}
