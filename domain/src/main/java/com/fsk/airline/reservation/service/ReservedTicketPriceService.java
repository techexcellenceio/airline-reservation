package com.fsk.airline.reservation.service;

import com.fsk.airline.reservation.api.GetReservedTicketPriceUseCase;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.ReservedTicketPrice;
import com.fsk.airline.reservation.model.ReservedTicketPriceBuilder;
import com.fsk.airline.reservation.model.TicketNumber;
import com.fsk.airline.reservation.spi.ReservedTickets;

import java.math.BigDecimal;

public class ReservedTicketPriceService implements GetReservedTicketPriceUseCase {

	private final ReservedTickets reservedTickets;

	public ReservedTicketPriceService(ReservedTickets reservedTickets) {
		this.reservedTickets = reservedTickets;
	}

	@Override
	public BigDecimal getReservedTicketPrice(String customerLogin, TicketNumber ticketNumber) {
		ReservedTicket reservedTicket = reservedTickets.findOne(customerLogin, ticketNumber)
				.orElseThrow(() -> new IllegalArgumentException("Could not find reserved ticket with " + ticketNumber));

		ReservedTicketPrice reservedTicketPrice = new ReservedTicketPriceBuilder()
				.reservedTicket(reservedTicket)
				.build();

		return reservedTicketPrice.getPrice();
	}

}
