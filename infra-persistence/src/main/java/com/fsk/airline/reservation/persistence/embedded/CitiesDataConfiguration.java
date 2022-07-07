package com.fsk.airline.reservation.persistence.embedded;

import com.fsk.airline.reservation.persistence.entity.CityJpaEntity;
import com.fsk.airline.reservation.persistence.repository.CitiesJpaRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CitiesDataConfiguration implements ApplicationRunner {

	private final CitiesJpaRepository citiesJpaRepository;

	public CitiesDataConfiguration(CitiesJpaRepository citiesJpaRepository) {
		this.citiesJpaRepository = citiesJpaRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		createAndSaveCity("Paris", 48.8566, 2.3522);
		createAndSaveCity("Berlin", 52.5200, 13.4050);
		createAndSaveCity("New York", 40.730610, -73.935242);
		createAndSaveCity("Prague", 50.0755, 14.4378);
	}

	private void createAndSaveCity(String cityName, double latitude, double longitude) {
		CityJpaEntity city = createCityJpaEntity(cityName, latitude, longitude);
		citiesJpaRepository.save(city);
	}

	private CityJpaEntity createCityJpaEntity(String cityName, double latitude, double longitude) {
		CityJpaEntity city = new CityJpaEntity();
		city.setName(cityName);
		city.setLatitude(latitude);
		city.setLongitude(longitude);
		return city;
	}
}
