package com.fsk.airline.reservation.domain.api;

import com.fsk.airline.reservation.domain.model.TicketNumber;

import java.math.BigDecimal;

@FunctionalInterface
public interface GetReservedTicketPriceUseCase {
	BigDecimal getReservedTicketPrice(String customerLogin, TicketNumber ticketNumber);
}
