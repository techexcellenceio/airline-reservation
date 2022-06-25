package com.fsk.airline.reservation;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.api.SearchReservedTicketUseCase;
import com.fsk.airline.reservation.command.ReserveTicketRequest;
import com.fsk.airline.reservation.command.ReserveTicketRequestBuilder;
import com.fsk.airline.reservation.model.CityName;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;
import com.fsk.airline.reservation.service.ReservationService;
import com.fsk.airline.reservation.spi.Cities;
import com.fsk.airline.reservation.spi.stub.CitiesInMemory;
import com.fsk.airline.reservation.spi.stub.ReservedTicketsInMemory;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SearchReservedTicketUseCaseTest {

	private final Cities cities = new CitiesInMemory();
	private final ReservationService reservationService = new ReservationService(new ReservedTicketsInMemory(), cities);
	private final SearchReservedTicketUseCase searchReservedTicketUseCase = reservationService;
	private final ReserveTicketUseCase reserveTicketUseCase = reservationService;

	private final String CUSTOMER_LOGIN = "aCustomer";
	private final ReserveTicketRequest RESERVE_TICKET_REQUEST = new ReserveTicketRequestBuilder()
			.customerLogin(CUSTOMER_LOGIN)
			.cityFrom("Paris")
			.cityTo("New York")
			.build();

	@Test
	void withoutReservationCustomerCannotFindTicket() {
		TicketNumber unknownTicketNumber = TicketNumber.generate();
		Optional<ReservedTicket> reservedTicket = searchReservedTicketUseCase.findReservedTicket(CUSTOMER_LOGIN, unknownTicketNumber);
		assertThat(reservedTicket).isEmpty();
	}

	@Test
	void afterReservationCustomerCanFindTicket() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(RESERVE_TICKET_REQUEST);

		TicketNumber ticketNumber = TicketNumber.of(reservedTicket.getNumber().getValue());
		Optional<ReservedTicket> foundTicket = searchReservedTicketUseCase.findReservedTicket(CUSTOMER_LOGIN, ticketNumber);

		assertThat(foundTicket).isPresent();
	}

	@Test
	void customerCannotFindAnotherCustomerTicket() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(RESERVE_TICKET_REQUEST);

		TicketNumber ticketNumber = TicketNumber.of(reservedTicket.getNumber().getValue());
		Optional<ReservedTicket> foundTicket = searchReservedTicketUseCase.findReservedTicket("anotherCustomer", ticketNumber);

		assertThat(foundTicket).isEmpty();
	}

	@Test
	void customerCanViewDepartureAndDestinationCity() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(RESERVE_TICKET_REQUEST);

		TicketNumber ticketNumber = TicketNumber.of(reservedTicket.getNumber().getValue());
		Optional<ReservedTicket> foundTicket = searchReservedTicketUseCase.findReservedTicket(CUSTOMER_LOGIN, ticketNumber);

		assertThat(foundTicket).isPresent();
		assertThat(foundTicket.get().getFrom()).isEqualTo(CityName.of("Paris"));
		assertThat(foundTicket.get().getTo()).isEqualTo(CityName.of("New York"));
	}

}
