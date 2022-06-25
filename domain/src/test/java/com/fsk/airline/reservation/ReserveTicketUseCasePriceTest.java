package com.fsk.airline.reservation;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.command.ReserveTicketRequest;
import com.fsk.airline.reservation.command.ReserveTicketRequestBuilder;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.service.ReservationService;
import com.fsk.airline.reservation.spi.Cities;
import com.fsk.airline.reservation.spi.ReservedTickets;
import com.fsk.airline.reservation.spi.stub.CitiesInMemory;
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

class ReserveTicketUseCasePriceTest {

	private final ReservedTickets reservedTickets = new ReservedTicketsInMemory();
	private final Cities cities = new CitiesInMemory();
	private final ReserveTicketUseCase reserveTicketUseCase = new ReservationService(reservedTickets, cities);

	@Test
	void reserveTicketFromParisToNewYorkOnMonday() {
		LocalDate firstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("Paris")
				.cityTo("New York")
				.departureDate(firstMonday)
				.build();
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(reserveTicketRequest);

		assertThat(reservedTicket.getPrice()).isEqualTo(BigDecimal.valueOf(295644.99));
	}

	@Test
	void reserveTicketFromParisToNewYorkOnTuesday() {
		LocalDate firstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.TUESDAY));
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("Paris")
				.cityTo("New York")
				.departureDate(firstMonday)
				.build();
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(reserveTicketRequest);

		assertThat(reservedTicket.getPrice()).isEqualTo(BigDecimal.valueOf(33945304.28));
	}

	@Test
	void reserveTicketFromParisToNewYorkOnWednesday() {
		LocalDate firstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.WEDNESDAY));
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("Paris")
				.cityTo("New York")
				.departureDate(firstMonday)
				.build();
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(reserveTicketRequest);

		assertThat(reservedTicket.getPrice()).isEqualTo(BigDecimal.valueOf(75806.41));
	}

	@ParameterizedTest
	@MethodSource("datesWithCorrespondingPrice")
	void byDefaultTicketPriceIsEqualToDistance(String cityFrom, String cityTo, LocalDate departureDate, BigDecimal price) {
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom(cityFrom)
				.cityTo(cityTo)
				.departureDate(departureDate)
				.build();
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(reserveTicketRequest);

		assertThat(reservedTicket.getPrice()).isEqualTo(price);
	}

	private static Stream<Arguments> datesWithCorrespondingPrice() {
		return Stream.of(
				Arguments.of("Paris", "New York", LocalDate.now().with(firstInMonth(DayOfWeek.THURSDAY)), BigDecimal.valueOf(5831.26)),
				Arguments.of("Paris", "Berlin", LocalDate.now().with(firstInMonth(DayOfWeek.FRIDAY)), BigDecimal.valueOf(877.46)),
				Arguments.of("Paris", "Prague", LocalDate.now().with(firstInMonth(DayOfWeek.SUNDAY)), BigDecimal.valueOf(882.82)),
				Arguments.of("New York", "Berlin", LocalDate.now().with(firstInMonth(DayOfWeek.SATURDAY)), BigDecimal.valueOf(6379.33))
		);
	}

}
