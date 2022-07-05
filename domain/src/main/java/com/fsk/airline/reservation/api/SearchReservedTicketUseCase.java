package com.fsk.airline.reservation.api;

import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;

import java.util.Optional;
@FunctionalInterface
public interface SearchReservedTicketUseCase {

	Optional<ReservedTicket> findReservedTicket(String customerLogin, TicketNumber ticketNbr);
}
