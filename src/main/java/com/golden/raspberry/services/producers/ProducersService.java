package com.golden.raspberry.services.producers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.golden.raspberry.dto.ProducerDto;
import com.golden.raspberry.entities.Producer;
import com.golden.raspberry.exceptionhandler.AwardsNotFoundException;
import com.golden.raspberry.repositories.IProducersRepository;

@Service
public class ProducersService implements IProducersService {

	private IProducersRepository repository;

	@Autowired
	public ProducersService(IProducersRepository repository) {
		this.repository = repository;
	}
	
	//mapeia o dto utilizado na leitura de arquivo para a entidade de produtor
	public Producer producersDtoToProducer(ProducerDto producerDto) {
		Producer producers = new Producer();
		producers.setName(producerDto.getName());
		return producers;
	}
	
	//busca por Id o produtor
	public Producer findByid(Long id) {
		return repository.findById(id).orElseThrow(() -> new AwardsNotFoundException());
	}
	
	//busca retorna todos produtores
	public List<Producer> findAll() {
		return repository.findAll();
	}
	
}
