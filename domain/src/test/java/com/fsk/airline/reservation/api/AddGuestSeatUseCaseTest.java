package com.fsk.airline.reservation.api;

import com.fsk.airline.reservation.command.ReserveTicketRequest;
import com.fsk.airline.reservation.command.ReserveTicketRequestBuilder;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;
import com.fsk.airline.reservation.service.GuestService;
import com.fsk.airline.reservation.service.ReservationService;
import com.fsk.airline.reservation.service.ReservedTicketPriceService;
import com.fsk.airline.reservation.spi.Cities;
import com.fsk.airline.reservation.spi.Guests;
import com.fsk.airline.reservation.spi.ReservedTickets;
import com.fsk.airline.reservation.spi.stub.CitiesInMemory;
import com.fsk.airline.reservation.spi.stub.GuestsInMemory;
import com.fsk.airline.reservation.spi.stub.ReservedTicketsInMemory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.stream.Stream;

import static java.time.temporal.TemporalAdjusters.firstInMonth;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddGuestSeatUseCaseTest {

	private final ReservedTickets reservedTickets = new ReservedTicketsInMemory();
	private final Cities cities = new CitiesInMemory();
	private final Guests guests = new GuestsInMemory();

	private final AddGuestSeatUseCase addGuestSeatUseCase = new GuestService(reservedTickets, guests);
	private final ReserveTicketUseCase reserveTicketUseCase = new ReservationService(reservedTickets, cities);
	private final GetReservedTicketPriceUseCase getReservedTicketPriceUseCase = new ReservedTicketPriceService(reservedTickets, guests);

	private final String CUSTOMER_LOGIN = "aCustomer";
	private final ReserveTicketRequest RESERVE_TICKET_REQUEST = new ReserveTicketRequestBuilder()
			.customerLogin(CUSTOMER_LOGIN)
			.cityFrom("Paris")
			.cityTo("New York")
			.departureDate(LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY)))
			.build();

	@Test
	void ticketNumberMustExist() {
		TicketNumber ticketNumber = TicketNumber.generate();

		IllegalStateException illegalStateException =
				assertThrows(IllegalStateException.class, () -> addGuestSeatUseCase.addGuestToReservation(CUSTOMER_LOGIN, ticketNumber));

		assertThat(illegalStateException.getMessage()).isEqualTo("Could not find ticket " + ticketNumber + " for customer " + CUSTOMER_LOGIN);
	}

	@Test
	void cannotAddGuestToAnotherCustomerReservation() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(RESERVE_TICKET_REQUEST);
		TicketNumber ticketNumber = reservedTicket.getNumber();

		IllegalStateException illegalStateException =
				assertThrows(IllegalStateException.class, () -> addGuestSeatUseCase.addGuestToReservation("anotherCustomer", ticketNumber));

		assertThat(illegalStateException.getMessage()).isEqualTo("Could not find ticket " + ticketNumber + " for customer anotherCustomer");
	}

	@ParameterizedTest
	@MethodSource("numberOfGuestsWithExpectedPrice")
	void addingGuestsReducesPrices(int numberOfGuests, BigDecimal expectedPrice) {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(RESERVE_TICKET_REQUEST);
		TicketNumber ticketNumber = reservedTicket.getNumber();

		addGuestToReservation(ticketNumber, numberOfGuests);

		BigDecimal reservedTicketPrice = getReservedTicketPriceUseCase.getReservedTicketPrice(CUSTOMER_LOGIN, ticketNumber);
		assertThat(reservedTicketPrice).isEqualTo(expectedPrice);
	}

	private static Stream<Arguments> numberOfGuestsWithExpectedPrice() {
		return Stream.of(
				Arguments.of(1, BigDecimal.valueOf(266080.49)),
				Arguments.of(2, BigDecimal.valueOf(239472.44)),
				Arguments.of(3, BigDecimal.valueOf(215525.19))
		);
	}

	@Test
	void cannotAddMoreThanThreeGuests() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(RESERVE_TICKET_REQUEST);
		TicketNumber ticketNumber = reservedTicket.getNumber();

		IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> addGuestToReservation(ticketNumber, 4));

		assertThat(illegalStateException.getMessage()).isEqualTo("Cannot add more than three guests to a reserved ticket");
	}

	private void addGuestToReservation(TicketNumber ticketNumber, int times) {
		for (int i = 0; i < times; i++) {
			addGuestSeatUseCase.addGuestToReservation(CUSTOMER_LOGIN, ticketNumber);
		}
	}
}