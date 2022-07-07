package com.fsk.airline.reservation.domain.spi;

public interface EventConsumer<T> {

	void consume(T consumedEvent);

}
