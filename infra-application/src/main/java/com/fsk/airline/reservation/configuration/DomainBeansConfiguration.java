package com.fsk.airline.reservation.configuration;

import com.fsk.airline.reservation.service.GuestService;
import com.fsk.airline.reservation.service.ReservationService;
import com.fsk.airline.reservation.service.ReservedTicketPriceService;
import com.fsk.airline.reservation.spi.Cities;
import com.fsk.airline.reservation.spi.Guests;
import com.fsk.airline.reservation.spi.ReservedTickets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeansConfiguration {

	@Bean
	public ReservationService reservationService(ReservedTickets reservedTickets, Cities cities) {
		return new ReservationService(reservedTickets, cities);
	}

	@Bean
	public ReservedTicketPriceService reservedTicketPriceService(ReservedTickets reservedTickets, Guests guests) {
		return new ReservedTicketPriceService(reservedTickets, guests);
	}

	@Bean
	public GuestService guestService(ReservedTickets reservedTickets, Guests guests) {
		return new GuestService(reservedTickets, guests);
	}
}
