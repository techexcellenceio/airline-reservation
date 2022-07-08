package com.fsk.airline.reservation.app.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsk.airline.reservation.app.controller.ReserveTicketDto;
import com.fsk.airline.reservation.app.controller.ReservedTicketNumberDto;
import com.fsk.airline.reservation.app.service.ReservedTicketMqMessage;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RabbitMqMessagingIT {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private Environment environment;

	public static final String CITY_FROM = "Paris";
	public static final String CITY_TO = "New York";
	public static final LocalDate DEPARTURE_DATE = LocalDate.now();
	public static final String CUSTOMER = "aCustomer";

	@Test
	void test() throws Exception {
		String ticketNumber = reserveAndGetTicketNumber();
		String rabbitMqQueueName = environment.getRequiredProperty("messaging.queue.name");

		ReservedTicketMqMessage message = rabbitTemplate.receiveAndConvert(rabbitMqQueueName, new ParameterizedTypeReference<>() {});
		assertThat(message).isNotNull();
		assertThat(message.ticketNumber()).isEqualTo(ticketNumber);
		assertThat(message.cityFrom()).isEqualTo(CITY_FROM);
		assertThat(message.cityTo()).isEqualTo(CITY_TO);
		assertThat(message.customerLogin()).isEqualTo(CUSTOMER);
		assertThat(message.departureDate()).isEqualTo(DEPARTURE_DATE);
	}

	private String reserveAndGetTicketNumber() throws Exception {
		ReserveTicketDto reserveTicketDto = new ReserveTicketDto();
		reserveTicketDto.setCityFrom(CITY_FROM);
		reserveTicketDto.setCityTo(CITY_TO);
		reserveTicketDto.setDepartureDate(DEPARTURE_DATE);

		String contentAsString = mockMvc.perform(post("/api/v1/customer/" + CUSTOMER + "/reservation")
				.content(objectMapper.writeValueAsString(reserveTicketDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		ReservedTicketNumberDto reservedTicketDto = objectMapper.readValue(contentAsString, ReservedTicketNumberDto.class);
		return reservedTicketDto.getTicketNumber();
	}

}
