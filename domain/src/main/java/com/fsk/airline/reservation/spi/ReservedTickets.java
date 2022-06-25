package com.fsk.airline.reservation.spi;

import com.fsk.airline.reservation.model.ReservedTicket;

import java.util.Optional;

public interface ReservedTickets {

	Optional<ReservedTicket> findOne(String ticketNumber);

	void save(ReservedTicket reservedTicket);
}
