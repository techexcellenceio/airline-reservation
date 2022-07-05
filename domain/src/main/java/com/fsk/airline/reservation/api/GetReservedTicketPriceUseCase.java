package com.fsk.airline.reservation.api;

import com.fsk.airline.reservation.model.TicketNumber;

import java.math.BigDecimal;

@FunctionalInterface
public interface GetReservedTicketPriceUseCase {
	BigDecimal getReservedTicketPrice(String customerLogin, TicketNumber ticketNumber);
}
