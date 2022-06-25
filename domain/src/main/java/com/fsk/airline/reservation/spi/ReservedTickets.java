package com.fsk.airline.reservation.spi;

import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;

import java.util.Optional;

public interface ReservedTickets {

	Optional<ReservedTicket> findOne(TicketNumber ticketNumber);

	void save(ReservedTicket reservedTicket);
}
