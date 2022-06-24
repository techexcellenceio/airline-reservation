package com.fsk.airline.reservation.api;

import com.fsk.airline.reservation.model.ReservedTicket;

@FunctionalInterface
public interface ReserveTicketUseCase {

	ReservedTicket reserveTicket(String cityFrom, String cityTo);
}
