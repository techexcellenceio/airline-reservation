package com.fsk.airline.reservation;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.model.CityName;
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

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReserveTicketUseCaseTest {

	private final ReservedTickets reservedTickets = new ReservedTicketsInMemory();
	private final Cities cities = new CitiesInMemory();
	private final ReserveTicketUseCase reserveTicketUseCase = new ReservationService(reservedTickets, cities);

	@Test
	void reserveTicketFromParisToNewYork() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket("aCustomer", "Paris", "New York");

		assertThat(reservedTicket).isNotNull();
		assertThat(reservedTicket.getFrom()).isEqualTo(CityName.of("Paris"));
		assertThat(reservedTicket.getTo()).isEqualTo(CityName.of("New York"));
	}

	@Test
	void reserveTicketFromBerlinToPrague() {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket("aCustomer", "Berlin", "Prague");

		assertThat(reservedTicket).isNotNull();
		assertThat(reservedTicket.getFrom()).isEqualTo(CityName.of("Berlin"));
		assertThat(reservedTicket.getTo()).isEqualTo(CityName.of("Prague"));
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

	@Test
	void unknownCityCannotBeUsedAsDeparture() {
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket("aCustomer", "sfdqsdfq", "Berlin"));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("Unknown city sfdqsdfq");
	}

	@ParameterizedTest
	@MethodSource("citiesAndDistance")
	void customerCanViewDistanceBetweenCities(String cityFrom, String cityTo, double distance) {
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket("aCustomer", cityFrom, cityTo);

		assertThat(reservedTicket.getDistanceInKm()).isEqualTo(distance);
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
