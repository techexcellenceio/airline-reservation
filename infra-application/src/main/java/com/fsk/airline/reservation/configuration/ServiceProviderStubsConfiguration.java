package com.fsk.airline.reservation.configuration;

import com.fsk.airline.reservation.spi.Cities;
import com.fsk.airline.reservation.spi.Guests;
import com.fsk.airline.reservation.spi.ReservedTickets;
import com.fsk.airline.reservation.spi.stub.CitiesInMemory;
import com.fsk.airline.reservation.spi.stub.GuestsInMemory;
import com.fsk.airline.reservation.spi.stub.ReservedTicketsInMemory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.fsk.airline.reservation.configuration.SpringProfiles.STUBS;

@Profile(STUBS)
@Configuration
public class ServiceProviderStubsConfiguration {

	@ConditionalOnMissingBean
	@Bean
	public ReservedTickets reservedTickets() {
		return new ReservedTicketsInMemory();
	}

	@ConditionalOnMissingBean
	@Bean
	public Cities cities() {
		return new CitiesInMemory();
	}

	@ConditionalOnMissingBean
	@Bean
	public Guests guests() {
		return new GuestsInMemory();
	}
}
