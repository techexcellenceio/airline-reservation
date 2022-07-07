package com.fsk.airline.reservation.domain.api;

import com.fsk.airline.reservation.domain.model.ReservedTicket;
import com.fsk.airline.reservation.domain.model.TicketNumber;

import java.util.Optional;
@FunctionalInterface
public interface SearchReservedTicketUseCase {

	Optional<ReservedTicket> findReservedTicket(String customerLogin, TicketNumber ticketNbr);
}
