package com.fsk.airline.reservation;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.command.ReserveTicketRequest;
import com.fsk.airline.reservation.command.ReserveTicketRequestBuilder;
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
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("Paris")
				.cityTo("New York")
				.build();
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(reserveTicketRequest);

		assertThat(reservedTicket).isNotNull();
		assertThat(reservedTicket.getFrom()).isEqualTo(CityName.of("Paris"));
		assertThat(reservedTicket.getTo()).isEqualTo(CityName.of("New York"));
	}

	@Test
	void reserveTicketFromBerlinToPrague() {
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("Berlin")
				.cityTo("Prague")
				.build();
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(reserveTicketRequest);

		assertThat(reservedTicket).isNotNull();
		assertThat(reservedTicket.getFrom()).isEqualTo(CityName.of("Berlin"));
		assertThat(reservedTicket.getTo()).isEqualTo(CityName.of("Prague"));
	}

	@Test
	void departureDateIsMandatory() {
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("Berlin")
				.cityTo("Prague")
				.departureDate(null)
				.build();

		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket(reserveTicketRequest));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("Departure date is mandatory");
	}

	@Test
	void destinationCityCannotBeNull() {
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("Berlin")
				.cityTo(null)
				.build();
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket(reserveTicketRequest));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("City name cannot be empty");
	}

	@Test
	void destinationCityCannotBeEmpty() {
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("Berlin")
				.cityTo("")
				.build();
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket(reserveTicketRequest));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("City name cannot be empty");
	}

	@Test
	void departureCityCannotBeNull() {
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom(null)
				.cityTo("Berlin")
				.build();
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket(reserveTicketRequest));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("City name cannot be empty");
	}

	@Test
	void departureCityCannotBeEmpty() {
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("")
				.cityTo("Berlin")
				.build();
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket(reserveTicketRequest));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("City name cannot be empty");
	}

	@Test
	void cityDestinationCannotBeTheSameAsDeparture() {
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("Berlin")
				.cityTo("Berlin")
				.build();
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket(reserveTicketRequest));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("Departure and destination cities cannot be the same");
	}

	@Test
	void unknownCityCannotBeUsedAsDeparture() {
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("sfdqsdfq")
				.cityTo("Berlin")
				.build();
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket(reserveTicketRequest));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("Unknown city sfdqsdfq");
	}

	@Test
	void unknownCityCannotBeUsedAsDestination() {
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom("Berlin")
				.cityTo("sfdqsdfq")
				.build();
		IllegalArgumentException illegalArgumentException =
				assertThrows(IllegalArgumentException.class, () -> reserveTicketUseCase.reserveTicket(reserveTicketRequest));

		assertThat(illegalArgumentException.getMessage()).isEqualTo("Unknown city sfdqsdfq");
	}

	@ParameterizedTest
	@MethodSource("citiesAndDistance")
	void customerCanViewDistanceBetweenCities(String cityFrom, String cityTo, double distance) {
		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin("aCustomer")
				.cityFrom(cityFrom)
				.cityTo(cityTo)
				.build();
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(reserveTicketRequest);

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
