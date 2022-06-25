package com.fsk.airline.reservation.api;

import com.fsk.airline.reservation.model.TicketNumber;

public interface AddGuestSeatUseCase {

	void addGuestToReservation(String customerLogin, TicketNumber number);
}
