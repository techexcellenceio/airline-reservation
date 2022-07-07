package com.fsk.airline.reservation.domain.spi.stub;

import com.fsk.airline.reservation.domain.spi.EventConsumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventConsumerInMemory<T> implements EventConsumer<T> {

	private final List<T> consumedEvents = new ArrayList<>();

	@Override
	public void consume(T consumedEvent) {
		consumedEvents.add(consumedEvent);
	}

	public List<T> getConsumedEvents() {
		return Collections.unmodifiableList(consumedEvents);
	}
}
