package com.fsk.airline.reservation.app.configuration;

import com.fsk.airline.reservation.domain.spi.Cities;
import com.fsk.airline.reservation.domain.spi.Guests;
import com.fsk.airline.reservation.domain.spi.ReservedTickets;
import com.fsk.airline.reservation.domain.spi.stub.CitiesInMemory;
import com.fsk.airline.reservation.domain.spi.stub.GuestsInMemory;
import com.fsk.airline.reservation.domain.spi.stub.ReservedTicketsInMemory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.fsk.airline.reservation.app.configuration.SpringProfiles.STUBS;

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
