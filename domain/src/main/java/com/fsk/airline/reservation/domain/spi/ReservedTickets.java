package com.fsk.airline.reservation.domain.spi;

import com.fsk.airline.reservation.domain.model.ReservedTicket;
import com.fsk.airline.reservation.domain.model.TicketNumber;

import java.util.Optional;

public interface ReservedTickets {

	Optional<ReservedTicket> findOne(String customerLogin, TicketNumber ticketNumber);

	boolean exists(String customerLogin, TicketNumber ticketNumber);

	void save(ReservedTicket reservedTicket);
}
