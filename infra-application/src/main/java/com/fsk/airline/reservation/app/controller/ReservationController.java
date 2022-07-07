package com.fsk.airline.reservation.app.controller;

import com.fsk.airline.reservation.app.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class ReservationController {

	private final ApplicationService applicationService;

	public ReservationController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	@PostMapping("/api/v1/customer/{customerLogin}/reservation")
	@ResponseStatus(CREATED)
	public ReservedTicketNumberDto reserveTicket(@PathVariable String customerLogin, @RequestBody ReserveTicketDto reserveTicketDto) {
		return applicationService.reserveTicket(customerLogin, reserveTicketDto);
	}

	@GetMapping("/api/v1/customer/{customerLogin}/reservations/{ticketNumber}")
	public ReservedTicketDto getReservedTicket(@PathVariable String customerLogin, @PathVariable String ticketNumber) {
		return applicationService.getReservedTicket(customerLogin, ticketNumber);
	}

	@PostMapping("/api/v1/customer/{customerLogin}/reservations/{ticketNumber}/guests")
	public void addGuestToReservedTicket(@PathVariable String customerLogin, @PathVariable String ticketNumber) {
		applicationService.addGuestToReservedTicket(customerLogin, ticketNumber);
	}
}
