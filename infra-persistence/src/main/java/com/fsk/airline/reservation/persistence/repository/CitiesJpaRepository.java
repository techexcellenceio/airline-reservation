package com.fsk.airline.reservation.persistence.repository;

import com.fsk.airline.reservation.persistence.entity.CityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitiesJpaRepository extends JpaRepository<CityJpaEntity, String> {

}
