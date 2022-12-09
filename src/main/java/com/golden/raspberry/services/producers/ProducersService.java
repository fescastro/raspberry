package com.golden.raspberry.services.producers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.golden.raspberry.dto.ProducerDto;
import com.golden.raspberry.entities.Producers;
import com.golden.raspberry.exceptionhandler.AwardsNotFoundException;
import com.golden.raspberry.repositories.IProducersRepository;

@Service
public class ProducersService implements IProducersService {

	private IProducersRepository repository;

	@Autowired
	public ProducersService(IProducersRepository repository) {
		this.repository = repository;
	}
	
	//adiciona a lista dos produtores lidos do arquivo
	public void addProducersFileCsv(List<ProducerDto> producerDto) {
		
		List<Producers> producers = producerDto
				  .stream()
				  .map(this::producersDtoToProducer)
				  .collect(Collectors.toList());
		
		repository.saveAllAndFlush(producers);
		
	}
	
	//mapeia o dto utilizado na leitura de arquivo para a entidade de produtor
	public Producers producersDtoToProducer(ProducerDto producerDto) {
		Producers producers = new Producers();
		producers.setName(producerDto.getName());
		return producers;
	}
	
	//busca por Id o produtor
	public Producers findByid(Long id) {
		return repository.findById(id).orElseThrow(() -> new AwardsNotFoundException());
	}
	
}
