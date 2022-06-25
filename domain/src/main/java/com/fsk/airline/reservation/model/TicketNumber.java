package com.fsk.airline.reservation.model;

import java.util.Objects;
import java.util.UUID;

public class TicketNumber {

	private final UUID value;

	private TicketNumber(UUID value) {
		Objects.requireNonNull(value);
		this.value = value;
	}

	private TicketNumber(String value) {
		this(UUID.fromString(value));
	}

	public static TicketNumber of(String value) {
		return new TicketNumber(value);
	}

	public static TicketNumber generate() {
		UUID uuid = UUID.randomUUID();
		return new TicketNumber(uuid);
	}

	public String getValue() {
		return value.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TicketNumber that = (TicketNumber) o;
		return value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
