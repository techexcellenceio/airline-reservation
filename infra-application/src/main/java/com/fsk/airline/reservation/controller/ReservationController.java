package com.fsk.airline.reservation.controller;

import com.fsk.airline.reservation.api.AddGuestSeatUseCase;
import com.fsk.airline.reservation.api.GetReservedTicketPriceUseCase;
import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.api.SearchReservedTicketUseCase;
import com.fsk.airline.reservation.command.ReserveTicketRequest;
import com.fsk.airline.reservation.command.ReserveTicketRequestBuilder;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class ReservationController {

	private final ReserveTicketUseCase reserveTicketUseCase;
	private final SearchReservedTicketUseCase searchReservedTicketUseCase;
	private final GetReservedTicketPriceUseCase getReservedTicketPriceUseCase;
	private final AddGuestSeatUseCase addGuestSeatUseCase;

	public ReservationController(ReserveTicketUseCase reserveTicketUseCase,
	                             SearchReservedTicketUseCase searchReservedTicketUseCase,
	                             GetReservedTicketPriceUseCase getReservedTicketPriceUseCase,
	                             AddGuestSeatUseCase addGuestSeatUseCase) {
		this.reserveTicketUseCase = reserveTicketUseCase;
		this.searchReservedTicketUseCase = searchReservedTicketUseCase;
		this.getReservedTicketPriceUseCase = getReservedTicketPriceUseCase;
		this.addGuestSeatUseCase = addGuestSeatUseCase;
	}

	@PostMapping("/api/v1/customer/{customerLogin}/reservation")
	@ResponseStatus(CREATED)
	public ReservedTicketNumberDto reserveTicket(@PathVariable String customerLogin, @RequestBody ReserveTicketDto reserveTicketDto) {

		ReserveTicketRequest reserveTicketRequest = new ReserveTicketRequestBuilder()
				.customerLogin(customerLogin)
				.cityFrom(reserveTicketDto.getCityFrom())
				.cityTo(reserveTicketDto.getCityTo())
				.departureDate(reserveTicketDto.getDepartureDate())
				.build();
		ReservedTicket reservedTicket = reserveTicketUseCase.reserveTicket(reserveTicketRequest);

		ReservedTicketNumberDto reservedTicketDto = new ReservedTicketNumberDto();
		reservedTicketDto.setTicketNumber(reservedTicket.getNumber().getValue());
		return reservedTicketDto;
	}

	@GetMapping("/api/v1/customer/{customerLogin}/reservations/{ticketNumber}")
	public ReservedTicketDto getReservedTicket(@PathVariable String customerLogin, @PathVariable String ticketNumber) {
		Optional<ReservedTicket> optionalReservedTicket = searchReservedTicketUseCase.findReservedTicket(customerLogin, TicketNumber.of(ticketNumber));

		if (optionalReservedTicket.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find ticket " + ticketNumber);
		}

		BigDecimal ticketPrice = getReservedTicketPriceUseCase.getReservedTicketPrice(customerLogin, TicketNumber.of(ticketNumber));

		ReservedTicketDto reservedTicketDto = new ReservedTicketDto();
		reservedTicketDto.setTicketNumber(ticketNumber);
		reservedTicketDto.setCustomerLogin(customerLogin);
		ReservedTicket reservedTicket = optionalReservedTicket.get();
		reservedTicketDto.setFrom(reservedTicket.getFrom().getValue());
		reservedTicketDto.setTo(reservedTicket.getTo().getValue());
		reservedTicketDto.setDepartureDate(reservedTicket.getDepartureDate());
		reservedTicketDto.setDistanceInKm(reservedTicket.getDistanceInKm());
		reservedTicketDto.setPrice(ticketPrice.doubleValue());
		return reservedTicketDto;
	}

	@PostMapping("/api/v1/customer/{customerLogin}/reservations/{ticketNumber}/guests")
	public void addGuestToReservedTicket(@PathVariable String customerLogin, @PathVariable String ticketNumber) {
		addGuestSeatUseCase.addGuestToReservation(customerLogin, TicketNumber.of(ticketNumber));
	}
}
