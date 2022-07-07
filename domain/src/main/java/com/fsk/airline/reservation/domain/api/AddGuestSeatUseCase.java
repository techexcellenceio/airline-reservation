package com.fsk.airline.reservation.domain.api;

import com.fsk.airline.reservation.domain.model.TicketNumber;

@FunctionalInterface
public interface AddGuestSeatUseCase {
	void addGuestToReservation(String customerLogin, TicketNumber number);
}
