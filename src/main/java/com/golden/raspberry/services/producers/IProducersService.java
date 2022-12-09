package com.golden.raspberry.services.producers;

import java.util.List;

import com.golden.raspberry.dto.ProducerDto;
import com.golden.raspberry.entities.Producers;

public interface IProducersService {
	
	void addProducersFileCsv(List<ProducerDto> producerDto);
	
	Producers producersDtoToProducer(ProducerDto producerDto);
	
	Producers findByid(Long id);
}
