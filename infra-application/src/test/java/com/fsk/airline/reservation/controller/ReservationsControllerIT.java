package com.fsk.airline.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationsControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@ParameterizedTest
	@MethodSource("successfulReservationsData")
	void customerCanCreateReservation(String customerLogin, String cityFrom, String cityTo, LocalDate departureDate) throws Exception {
		String ticketNumber = reserveAndGetTicketNumber(customerLogin, cityFrom, cityTo, departureDate);
		assertThat(ticketNumber).isNotNull();
	}

	@ParameterizedTest
	@MethodSource("successfulReservationsData")
	void getReservation(String customerLogin, String cityFrom, String cityTo, LocalDate departureDate) throws Exception {
		String ticketNumber = reserveAndGetTicketNumber(customerLogin, cityFrom, cityTo, departureDate);

		ReservedTicketDto reservedTicketDto = getReservedTicket(customerLogin, ticketNumber);

		assertThat(reservedTicketDto).isNotNull();
		assertThat(reservedTicketDto.getFrom()).isEqualTo(cityFrom);
		assertThat(reservedTicketDto.getTo()).isEqualTo(cityTo);
		assertThat(reservedTicketDto.getDepartureDate()).isEqualTo(departureDate);
		assertThat(reservedTicketDto.getDistanceInKm()).isNotZero();
		assertThat(reservedTicketDto.getPrice()).isNotZero();
	}

	@ParameterizedTest
	@MethodSource("successfulReservationsData")
	void addGuestToReservation(String customerLogin, String cityFrom, String cityTo, LocalDate departureDate) throws Exception {
		String ticketNumber = reserveAndGetTicketNumber(customerLogin, cityFrom, cityTo, departureDate);

		mockMvc.perform(post("/api/v1/customer/" + customerLogin + "/reservations/" + ticketNumber + "/guests")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private static Stream<Arguments> successfulReservationsData() {
		return Stream.of(
				Arguments.of("aCustomer", "Paris", "New York", LocalDate.now()),
				Arguments.of("aCustomer", "New York", "Paris", LocalDate.now().plusDays(5)),
				Arguments.of("aCustomer2", "Paris", "Berlin", LocalDate.now().plusDays(10)),
				Arguments.of("aCustomer2", "Berlin", "Paris", LocalDate.now().plusDays(12))
		);
	}

	@ParameterizedTest
	@MethodSource("failingReservationsData")
	void cannotCreateReservation(String customerLogin, String cityFrom, String cityTo, LocalDate departureDate) throws Exception {
		ReserveTicketDto reserveTicketDto = new ReserveTicketDto();
		reserveTicketDto.setCityFrom(cityFrom);
		reserveTicketDto.setCityTo(cityTo);
		reserveTicketDto.setDepartureDate(departureDate);

		mockMvc.perform(post("/api/v1/customer/" + customerLogin + "/reservation")
				.content(objectMapper.writeValueAsString(reserveTicketDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void cannotFindReservationWithUnknownTicketNumber() throws Exception {
		mockMvc.perform(get("/api/v1/customer/aCustomer/reservations/" + UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn().getResponse().getContentAsString();
	}

	private static Stream<Arguments> failingReservationsData() {
		return Stream.of(
				Arguments.of("aCustomer", "Paris", "New York", null),
				Arguments.of("aCustomer", null, "Paris", LocalDate.now().plusDays(5)),
				Arguments.of("aCustomer2", "Paris", null, LocalDate.now().plusDays(10)),
				Arguments.of("aCustomer2", "Berlin", "Berlin", LocalDate.now().plusDays(12)),
				Arguments.of("aCustomer2", "sdfsdfsdf", "Berlin", LocalDate.now().plusDays(12))
		);
	}

	private String reserveAndGetTicketNumber(String customerLogin, String cityFrom, String cityTo, LocalDate departureDate) throws Exception {
		ReserveTicketDto reserveTicketDto = new ReserveTicketDto();
		reserveTicketDto.setCityFrom(cityFrom);
		reserveTicketDto.setCityTo(cityTo);
		reserveTicketDto.setDepartureDate(departureDate);

		String contentAsString = mockMvc.perform(post("/api/v1/customer/" + customerLogin + "/reservation")
				.content(objectMapper.writeValueAsString(reserveTicketDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		ReservedTicketNumberDto reservedTicketDto = objectMapper.readValue(contentAsString, ReservedTicketNumberDto.class);
		return reservedTicketDto.getTicketNumber();
	}

	private ReservedTicketDto getReservedTicket(String customerLogin, String ticketNumber) throws Exception {
		String contentAsString = mockMvc.perform(get("/api/v1/customer/" + customerLogin + "/reservations/" + ticketNumber)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		return objectMapper.readValue(contentAsString, ReservedTicketDto.class);
	}

}
