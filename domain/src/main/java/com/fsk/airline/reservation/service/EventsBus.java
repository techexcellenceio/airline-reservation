package com.fsk.airline.reservation.service;

import com.fsk.airline.reservation.spi.EventConsumer;

import java.util.ArrayList;
import java.util.List;

public class EventsBus<T> {

	private final List<EventConsumer<T>> eventConsumers = new ArrayList<>();

	public void addConsumer(EventConsumer<T> consumer) {
		eventConsumers.add(consumer);
	}

	public void publishEvent(T event) {
		eventConsumers.forEach(c -> c.consume(event));
	}
}
