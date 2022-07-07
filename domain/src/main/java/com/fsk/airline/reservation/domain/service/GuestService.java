package com.fsk.airline.reservation.domain.service;

import com.fsk.airline.reservation.domain.api.AddGuestSeatUseCase;
import com.fsk.airline.reservation.domain.hexarch.DomainService;
import com.fsk.airline.reservation.domain.model.TicketNumber;
import com.fsk.airline.reservation.domain.spi.Guests;
import com.fsk.airline.reservation.domain.spi.ReservedTickets;

@DomainService
public class GuestService implements AddGuestSeatUseCase {

	private final ReservedTickets reservedTickets;
	private final Guests guests;

	private static final int MAX_NUMBER_OF_GUESTS = 3;

	public GuestService(ReservedTickets reservedTickets, Guests guests) {
		this.reservedTickets = reservedTickets;
		this.guests = guests;
	}

	@Override
	public void addGuestToReservation(String customerLogin, TicketNumber ticketNumber) {
		if (!reservedTickets.exists(customerLogin, ticketNumber)) {
			throw new IllegalStateException("Could not find ticket " + ticketNumber + " for customer " + customerLogin);
		}
		Integer numberOfGuests = guests.counttNumberOfGuests(ticketNumber);
		if (numberOfGuests == MAX_NUMBER_OF_GUESTS) {
			throw new IllegalStateException("Cannot add more than three guests to a reserved ticket");
		}
		guests.addGuestToReservation(ticketNumber);
	}
}
