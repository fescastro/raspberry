package com.golden.raspberry.services.producers;

import java.util.List;

import com.golden.raspberry.dto.ProducerDto;
import com.golden.raspberry.entities.Producer;

public interface IProducersService {
	
	Producer producersDtoToProducer(ProducerDto producerDto);
	
	Producer findByid(Long id);
	
	List<Producer> findAll();
}
