package com.fsk.airline.reservation.api;

import com.fsk.airline.reservation.model.ReservedTicket;

import java.util.Optional;

public interface SearchReservedTicketUseCase {

	Optional<ReservedTicket> findReservedTicket(String customerLogin, String ticketNbr);
}
