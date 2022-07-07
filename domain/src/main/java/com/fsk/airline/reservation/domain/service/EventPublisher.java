package com.fsk.airline.reservation.domain.service;

import com.fsk.airline.reservation.domain.spi.EventConsumer;

public interface EventPublisher<T> {

	void subscribe(EventConsumer<T> consumer);

	void publish(T event);
}
