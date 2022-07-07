package com.fsk.airline.reservation.domain.spi;

import com.fsk.airline.reservation.domain.model.TicketNumber;

public interface Guests {

	void addGuestToReservation(TicketNumber ticketNumber);

	Integer counttNumberOfGuests(TicketNumber ticketNumber);
}
