package com.fsk.airline.reservation.spi;

import com.fsk.airline.reservation.model.TicketNumber;

public interface Guests {

	void addGuestToReservation(TicketNumber ticketNumber);

	Integer counttNumberOfGuests(TicketNumber ticketNumber);
}
