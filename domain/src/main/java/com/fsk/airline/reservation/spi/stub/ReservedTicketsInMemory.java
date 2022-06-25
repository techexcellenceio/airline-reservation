package com.fsk.airline.reservation.spi.stub;

import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;
import com.fsk.airline.reservation.spi.ReservedTickets;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReservedTicketsInMemory implements ReservedTickets {

	private final Map<TicketNumber, ReservedTicket> reservedTickets = new HashMap<>();

	@Override
	public Optional<ReservedTicket> findOne(TicketNumber ticketNumber) {
		ReservedTicket reservedTicket1 = reservedTickets.get(ticketNumber);
		return Optional.ofNullable(reservedTicket1);
	}

	@Override
	public void save(ReservedTicket reservedTicket) {
		reservedTickets.put(reservedTicket.getNumber(), reservedTicket);
	}
}
