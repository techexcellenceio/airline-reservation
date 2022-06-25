package com.fsk.airline.reservation;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.api.SearchReservedTicketUseCase;
import com.fsk.airline.reservation.model.CityName;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;
import com.fsk.airline.reservation.service.ReservationService;
import com.fsk.airline.reservation.spi.Cities;
import com.fsk.airline.reservation.spi.stub.CitiesInMemory;
import com.fsk.airline.reservation.spi.stub.ReservedTicketsInMemory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SearchReservedTicketUseCaseTest {

	private final Cities cities = new CitiesInMemory();
	private final ReservationService reservationService = new ReservationService(new ReservedTicketsInMemory(), cities);
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

	@Test
	void customerCanViewDepartureAndDestinationCity() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(CUSTOMER_LOGIN, "Paris", "New York");

		TicketNumber ticketNumber = TicketNumber.of(reservedTicket.getNumber().getValue());
		Optional<ReservedTicket> foundTicket = searchReservedTicketUseCase.findReservedTicket(CUSTOMER_LOGIN, ticketNumber);

		assertThat(foundTicket).isPresent();
		assertThat(foundTicket.get().getFrom()).isEqualTo(CityName.of("Paris"));
		assertThat(foundTicket.get().getTo()).isEqualTo(CityName.of("New York"));
	}

	@ParameterizedTest
	@MethodSource("citiesAndDistance")
	void customerCanViewDistanceBetweenCities(String cityFrom, String cityTo, double distance) {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(CUSTOMER_LOGIN, cityFrom, cityTo);

		TicketNumber ticketNumber = TicketNumber.of(reservedTicket.getNumber().getValue());
		Optional<ReservedTicket> foundTicket = searchReservedTicketUseCase.findReservedTicket(CUSTOMER_LOGIN, ticketNumber);

		assertThat(foundTicket).isPresent();
		assertThat(foundTicket.get().getDistanceInKm()).isEqualTo(distance);
	}

	private static Stream<Arguments> citiesAndDistance() {
		return Stream.of(
				Arguments.of("Paris", "New York", 5831.262033439712),
				Arguments.of("New York", "Paris", 5831.262033439712),
				Arguments.of("Paris", "Berlin", 877.4633259175432),
				Arguments.of("Berlin", "Paris", 877.4633259175432),
				Arguments.of("Paris", "Prague", 882.8163086487457),
				Arguments.of("Prague", "Paris", 882.8163086487457),
				Arguments.of("New York", "Berlin", 6379.329836559427),
				Arguments.of("Berlin", "New York", 6379.329836559427),
				Arguments.of("New York", "Prague", 6566.678422533317),
				Arguments.of("Prague", "New York", 6566.678422533317),
				Arguments.of("Berlin", "Prague", 281.13299584651537),
				Arguments.of("Prague", "Berlin", 281.13299584651537)
				);
	}
}
