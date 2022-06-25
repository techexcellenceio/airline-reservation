package com.fsk.airline.reservation.spi.stub;

import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.spi.ReservedTickets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservedTicketsInMemory implements ReservedTickets {

	private final List<ReservedTicket> reservedTickets = new ArrayList<>();

	@Override
	public Optional<ReservedTicket> findOne(String ticketNumber) {
		return reservedTickets.stream()
				.filter(t -> t.getNumber().equals(ticketNumber))
				.findFirst();
	}

	@Override
	public void save(ReservedTicket reservedTicket) {
		reservedTickets.add(reservedTicket);
	}
}
