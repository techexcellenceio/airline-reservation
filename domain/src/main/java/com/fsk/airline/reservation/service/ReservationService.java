package com.fsk.airline.reservation.service;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.model.ReservedTicket;

public class ReservationService implements ReserveTicketUseCase {

	@Override
	public ReservedTicket reserveTicket(String cityFrom, String cityTo) {

		return new ReservedTicket(new City(cityFrom), new City(cityTo));
	}
}
