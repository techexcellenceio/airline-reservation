package com.fsk.airline.reservation.service;

import com.fsk.airline.reservation.api.AddGuestSeatUseCase;
import com.fsk.airline.reservation.model.TicketNumber;
import com.fsk.airline.reservation.spi.Guests;
import com.fsk.airline.reservation.spi.ReservedTickets;

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
