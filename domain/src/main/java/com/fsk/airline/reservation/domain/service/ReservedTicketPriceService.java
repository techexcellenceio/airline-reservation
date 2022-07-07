package com.fsk.airline.reservation.domain.service;

import com.fsk.airline.reservation.domain.api.GetReservedTicketPriceUseCase;
import com.fsk.airline.reservation.domain.hexarch.DomainService;
import com.fsk.airline.reservation.domain.model.ReservedTicket;
import com.fsk.airline.reservation.domain.model.ReservedTicketPrice;
import com.fsk.airline.reservation.domain.model.ReservedTicketPriceBuilder;
import com.fsk.airline.reservation.domain.model.TicketNumber;
import com.fsk.airline.reservation.domain.spi.Guests;
import com.fsk.airline.reservation.domain.spi.ReservedTickets;

import java.math.BigDecimal;

@DomainService
public class ReservedTicketPriceService implements GetReservedTicketPriceUseCase {

	private final ReservedTickets reservedTickets;
	private final Guests guests;

	public ReservedTicketPriceService(ReservedTickets reservedTickets, Guests guests) {
		this.reservedTickets = reservedTickets;
		this.guests = guests;
	}

	@Override
	public BigDecimal getReservedTicketPrice(String customerLogin, TicketNumber ticketNumber) {
		ReservedTicket reservedTicket = reservedTickets.findOne(customerLogin, ticketNumber)
				.orElseThrow(() -> new IllegalArgumentException("Could not find reserved ticket with " + ticketNumber));

		Integer numberOfGuests = guests.counttNumberOfGuests(ticketNumber);

		ReservedTicketPrice reservedTicketPrice = new ReservedTicketPriceBuilder()
				.reservedTicket(reservedTicket)
				.numberOfGuests(numberOfGuests)
				.build();

		return reservedTicketPrice.getPrice();
	}

}
