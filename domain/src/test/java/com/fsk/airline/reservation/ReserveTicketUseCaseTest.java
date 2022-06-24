package com.fsk.airline.reservation;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReserveTicketUseCaseTest {

	private final ReserveTicketUseCase reserveTicketUseCase = new ReservationService();

	@Test
	void reserveTicketFromParisToNewYork() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket("Paris", "New York");

		assertThat(reservedTicket).isNotNull();
		assertThat(reservedTicket.getFrom()).isEqualTo(new City("Paris"));
		assertThat(reservedTicket.getTo()).isEqualTo(new City("New York"));
	}

	@Test
	void reserveTicketFromBerlinToPrague() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket("Berlin", "Prague");

		assertThat(reservedTicket).isNotNull();
		assertThat(reservedTicket.getFrom()).isEqualTo(new City("Berlin"));
		assertThat(reservedTicket.getTo()).isEqualTo(new City("Prague"));
	}


}
