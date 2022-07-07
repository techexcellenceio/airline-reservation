package com.fsk.airline.reservation.app.service;

import com.fsk.airline.reservation.domain.model.ReservedTicket;
import com.fsk.airline.reservation.domain.service.EventPublisher;
import com.fsk.airline.reservation.domain.spi.EventConsumer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingService implements EventConsumer<ReservedTicket> {

	private final RabbitTemplate rabbitTemplate;
	private final Queue queue;

	public MessagingService(EventPublisher<ReservedTicket> reservedTicketPublisher, RabbitTemplate rabbitTemplate, Queue queue) {
		this.rabbitTemplate = rabbitTemplate;
		this.queue = queue;
		reservedTicketPublisher.subscribe(this);
	}

	@Override
	public void consume(ReservedTicket consumedEvent) {
		ReservedTicketMqMessage message = new ReservedTicketMqMessage(
				consumedEvent.getNumber().getValue(),
				consumedEvent.getCustomerLogin(),
				consumedEvent.getFrom().getValue(),
				consumedEvent.getTo().getValue(),
				consumedEvent.getDepartureDate()
		);
		rabbitTemplate.convertAndSend(queue.getName(), message);
	}
}
