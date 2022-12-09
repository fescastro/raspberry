package com.golden.raspberry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.golden.raspberry.entities.Producers;

public interface IProducersRepository  extends JpaRepository<Producers, Long> {
	
}
