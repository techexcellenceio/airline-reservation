package com.fsk.airline.reservation;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.service.ReservationService;
import com.fsk.airline.reservation.spi.ReservedTickets;
import com.fsk.airline.reservation.spi.stub.ReservedTicketsInMemory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	@Test
	void destinationCityCannotBeNull() {
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket("aCustomer", "Berlin", null));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("City name cannot be empty");
	}

	@Test
	void destinationCityCannotBeEmpty() {
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket("aCustomer", "Berlin", ""));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("City name cannot be empty");
	}

	@Test
	void departureCityCannotBeNull() {
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket("aCustomer", null, "Berlin"));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("City name cannot be empty");
	}

	@Test
	void departureCityCannotBeEmpty() {
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket("aCustomer", "", "Berlin"));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("City name cannot be empty");
	}

	@Test
	void cityDestinationCannotBeTheSameAsDeparture() {
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket("aCustomer", "Berlin", "Berlin"));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("Departure and destination cities cannot be the same");
	}

}
