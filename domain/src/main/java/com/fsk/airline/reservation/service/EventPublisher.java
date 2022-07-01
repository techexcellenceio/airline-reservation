package com.fsk.airline.reservation.service;

import com.fsk.airline.reservation.spi.EventConsumer;

public interface EventPublisher<T> {

	void subscribe(EventConsumer<T> consumer);

	void publish(T event);
}
