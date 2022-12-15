package com.golden.raspberry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.golden.raspberry.entities.Producer;

public interface IProducersRepository  extends JpaRepository<Producer, Long> {
	
}
