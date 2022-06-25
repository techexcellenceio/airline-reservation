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
	public Optional<ReservedTicket> findOne(String customerLogin, TicketNumber ticketNumber) {
		ReservedTicket reservedTicket = reservedTickets.get(ticketNumber);
		if (ticketExistsAndBelongsToCustomer(customerLogin, reservedTicket)) {
			return Optional.of(reservedTicket);
		}
		return Optional.empty();
	}

	private boolean ticketExistsAndBelongsToCustomer(String customerLogin, ReservedTicket reservedTicket) {
		return reservedTicket != null && reservedTicket.getCustomerLogin().equals(customerLogin);
	}

	@Override
	public void save(ReservedTicket reservedTicket) {
		reservedTickets.put(reservedTicket.getNumber(), reservedTicket);
	}
}
