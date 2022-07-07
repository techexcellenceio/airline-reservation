package com.fsk.airline.reservation.domain.api;

import com.fsk.airline.reservation.domain.command.ReserveTicketRequest;
import com.fsk.airline.reservation.domain.model.ReservedTicket;

@FunctionalInterface
public interface ReserveTicketUseCase {

	ReservedTicket reserveTicket(ReserveTicketRequest request);
}
