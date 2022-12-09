package com.golden.raspberry.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.golden.raspberry.dto.IntervalAwardsProducerDto;
import com.golden.raspberry.services.awards.IAwardsService;

@RestController
@RequestMapping(value = "/awards")
public class AwardsController {
	
	
	private IAwardsService service;	

	@Autowired
	public AwardsController(IAwardsService service) {
		super();
		this.service = service;
	}
	
	@GetMapping(value = "/interval-awards-producers")
    public ResponseEntity<IntervalAwardsProducerDto> getMinMaxIntervalAwardsProducer()
    {
		return new ResponseEntity<>(service.getMinMaxIntervalAwardsProducer(), HttpStatus.OK);
    }
	
}