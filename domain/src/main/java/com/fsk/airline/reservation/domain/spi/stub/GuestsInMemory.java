package com.fsk.airline.reservation.domain.spi.stub;

import com.fsk.airline.reservation.domain.model.TicketNumber;
import com.fsk.airline.reservation.domain.spi.Guests;

import java.util.HashMap;
import java.util.Map;

public class GuestsInMemory implements Guests {

	private final Map<TicketNumber, Integer> guestsByTicketNumber = new HashMap<>();

	@Override
	public void addGuestToReservation(TicketNumber ticketNumber) {
		if (guestsByTicketNumber.containsKey(ticketNumber)) {
			Integer oldValue = guestsByTicketNumber.get(ticketNumber);
			guestsByTicketNumber.put(ticketNumber, oldValue + 1);
		} else {
			guestsByTicketNumber.put(ticketNumber,  1);
		}
	}

	@Override
	public Integer counttNumberOfGuests(TicketNumber ticketNumber) {
		if (guestsByTicketNumber.containsKey(ticketNumber)) {
			return guestsByTicketNumber.get(ticketNumber);
		}
		return 0;
	}
}
