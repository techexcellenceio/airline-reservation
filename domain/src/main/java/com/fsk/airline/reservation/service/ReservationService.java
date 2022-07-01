package com.fsk.airline.reservation.service;

import com.fsk.airline.reservation.api.ReserveTicketUseCase;
import com.fsk.airline.reservation.api.SearchReservedTicketUseCase;
import com.fsk.airline.reservation.command.ReserveTicketRequest;
import com.fsk.airline.reservation.model.City;
import com.fsk.airline.reservation.model.ReservedTicket;
import com.fsk.airline.reservation.model.TicketNumber;
import com.fsk.airline.reservation.spi.Cities;
import com.fsk.airline.reservation.spi.EventConsumer;
import com.fsk.airline.reservation.spi.ReservedTickets;

import java.util.Optional;

public class ReservationService implements ReserveTicketUseCase, SearchReservedTicketUseCase, EventPublisher<ReservedTicket> {

	private final ReservedTickets reservedTickets;
	private final Cities cities;
	private final EventsBus<ReservedTicket> eventsBus = new EventsBus<>();

	public ReservationService(ReservedTickets reservedTickets, Cities cities) {
		this.reservedTickets = reservedTickets;
		this.cities = cities;
	}

	@Override
	public ReservedTicket reserveTicket(ReserveTicketRequest request) {
		City cityFrom = findCity(request.getCityFrom());
		City cityTo = findCity(request.getCityTo());

		ReservedTicket reservedTicket = new ReservedTicket(request.getCustomerLogin(), cityFrom, cityTo, request.getDepartureDate());
		reservedTickets.save(reservedTicket);
		publish(reservedTicket);
		return reservedTicket;
	}

	private City findCity(String cityValue) {
		return cities.findOne(cityValue).orElseThrow(() -> new IllegalArgumentException("Unknown city " + cityValue));
	}

	@Override
	public Optional<ReservedTicket> findReservedTicket(String customerLogin, TicketNumber ticketNbr) {

		return reservedTickets.findOne(customerLogin, ticketNbr);
	}

	public void subscribe(EventConsumer<ReservedTicket> eventConsumer) {
		eventsBus.addConsumer(eventConsumer);
	}

	public void publish(ReservedTicket reservedTicket) {
		eventsBus.publishEvent(reservedTicket);
	}
}
