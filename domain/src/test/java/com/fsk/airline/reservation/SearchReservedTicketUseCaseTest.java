package com.fsk.airline.reservation;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.api.SearchReservedTicketUseCase;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;
import com.fsk.airline.reservation.service.ReservationService;
import com.fsk.airline.reservation.spi.stub.ReservedTicketsInMemory;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SearchReservedTicketUseCaseTest {

	private final ReservationService reservationService = new ReservationService(new ReservedTicketsInMemory());
	private final SearchReservedTicketUseCase searchReservedTicketUseCase = reservationService;
	private final ReserveTicketUseCase reserveTicketUseCase = reservationService;

	private final String CUSTOMER_LOGIN = "aCustomer";

	@Test
	void withoutReservationCustomerCannotFindTicket() {
		TicketNumber unknownTicketNumber = TicketNumber.generate();
		Optional<ReservedTicket> reservedTicket = searchReservedTicketUseCase.findReservedTicket(CUSTOMER_LOGIN, unknownTicketNumber);
		assertThat(reservedTicket).isEmpty();
	}

	@Test
	void afterReservationCustomerCanFindTicket() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(CUSTOMER_LOGIN, "Paris", "New York");

		TicketNumber ticketNumber = TicketNumber.of(reservedTicket.getNumber().getValue());
		Optional<ReservedTicket> foundTicket = searchReservedTicketUseCase.findReservedTicket(CUSTOMER_LOGIN, ticketNumber);

		assertThat(foundTicket).isPresent();
	}

	@Test
	void customerCannotFindAnotherCustomerTicket() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket("anotherCustomer", "Paris", "New York");

		TicketNumber ticketNumber = TicketNumber.of(reservedTicket.getNumber().getValue());
		Optional<ReservedTicket> foundTicket = searchReservedTicketUseCase.findReservedTicket(CUSTOMER_LOGIN, ticketNumber);

		assertThat(foundTicket).isEmpty();
	}
}
