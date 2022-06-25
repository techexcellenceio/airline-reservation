package com.fsk.airline.reservation;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.service.ReservationService;
import com.fsk.airline.reservation.spi.ReservedTickets;
import com.fsk.airline.reservation.spi.stub.ReservedTicketsInMemory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReserveTicketUseCaseTest {

	private final ReservedTickets reservedTickets = new ReservedTicketsInMemory();
	private final ReserveTicketUseCase reserveTicketUseCase = new ReservationService(reservedTickets);

	@Test
	void reserveTicketFromParisToNewYork() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket("aCustomer", "Paris", "New York");

		assertThat(reservedTicket).isNotNull();
		assertThat(reservedTicket.getFrom()).isEqualTo(new City("Paris"));
		assertThat(reservedTicket.getTo()).isEqualTo(new City("New York"));
	}

	@Test
	void reserveTicketFromBerlinToPrague() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket("aCustomer", "Berlin", "Prague");

		assertThat(reservedTicket).isNotNull();
		assertThat(reservedTicket.getFrom()).isEqualTo(new City("Berlin"));
		assertThat(reservedTicket.getTo()).isEqualTo(new City("Prague"));
	}

	//city destination cannot be the same as departure

}
