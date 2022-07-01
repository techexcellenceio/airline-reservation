package com.fsk.airline.reservation.spi;

public interface EventConsumer<T> {

	void consume(T consumedEvent);

}
