package com.fsk.airline.reservation.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDate;

public record ReservedTicketMqMessage(@JsonProperty("ticketNumber") String ticketNumber,
                                      @JsonProperty("customerLogin") String customerLogin,
                                      @JsonProperty("cityFrom") String cityFrom,
                                      @JsonProperty("cityTo") String cityTo,
                                      @JsonProperty("departureDate") @JsonFormat(pattern="yyyy-MM-dd") LocalDate departureDate)
		implements Serializable {
}
