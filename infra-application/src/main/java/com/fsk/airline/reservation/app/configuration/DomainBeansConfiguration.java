package com.fsk.airline.reservation.app.configuration;

import com.fsk.airline.reservation.domain.hexarch.DomainService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
		basePackages = "com.fsk.airline.reservation.domain",
		includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = DomainService.class)}
)
public class DomainBeansConfiguration {
}
