package com.fsk.airline.reservation.domain.service;

import com.fsk.airline.reservation.domain.spi.EventConsumer;

import java.util.ArrayList;
import java.util.List;

class EventsBus<T> {

	private final List<EventConsumer<T>> eventConsumers = new ArrayList<>();

	public void addConsumer(EventConsumer<T> consumer) {
		eventConsumers.add(consumer);
	}

	public void publishEvent(T event) {
		eventConsumers.forEach(c -> c.consume(event));
	}
}
