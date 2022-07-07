package com.fsk.airline.reservation.persistence.dao;

import com.fsk.airline.reservation.domain.model.City;
import com.fsk.airline.reservation.domain.spi.Cities;
import com.fsk.airline.reservation.persistence.entity.CityJpaEntity;
import com.fsk.airline.reservation.persistence.repository.CitiesJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CitiesDao implements Cities {

	private final CitiesJpaRepository citiesJpaRepository;

	public CitiesDao(CitiesJpaRepository citiesJpaRepository) {
		this.citiesJpaRepository = citiesJpaRepository;
	}

	@Override
	public Optional<City> findOne(String cityName) {
		return citiesJpaRepository.findById(cityName).map(this::toCity);
	}

	private City toCity(CityJpaEntity cityJpaEntity) {
		return City.of(cityJpaEntity.getName(), cityJpaEntity.getLatitude(), cityJpaEntity.getLongitude());
	}
}
