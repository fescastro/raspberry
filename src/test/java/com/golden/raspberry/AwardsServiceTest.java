package com.golden.raspberry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.util.ReflectionTestUtils;

import com.golden.raspberry.dto.IntervalAwardsProducerDto;
import com.golden.raspberry.dto.MinMaxResult;
import com.golden.raspberry.entities.Producer;
import com.golden.raspberry.projection.IInterval;
import com.golden.raspberry.repositories.IAwardsRepository;
import com.golden.raspberry.services.awards.AwardsService;
import com.golden.raspberry.services.awards.IAwardsService;
import com.golden.raspberry.services.producers.IProducersService;

@SpringBootTest
@AutoConfigureMockMvc
public class AwardsServiceTest {

	@InjectMocks
    private AwardsService service;
	
	@Mock
	IProducersService producersService;
	
	@Mock
    private IAwardsRepository repository;
	
	@Test
    public void getMinMaxIntervalAwardsProducer() {
		 
		 List<Producer> producers =  getProducers();
		 when(producersService.findAll()).thenReturn(producers);
		 
		List<IInterval> intervals =  getintervals();
		 when(repository.findAwardForProducer()).thenReturn(intervals);
		 
		 IntervalAwardsProducerDto result = service.getMinMaxIntervalAwardsProducer();
		 
		 assertEquals(getresult().getMin().size(), result.getMin().size());
		 assertEquals(getresult().getMax().size(), result.getMin().size());
		 assertEquals(getresult().getMin().get(0).getProducer(), result.getMin().get(0).getProducer());
		 assertEquals(getresult().getMax().get(0).getProducer(), result.getMax().get(0).getProducer());
		 assertEquals(getresult().getMin().get(1).getProducer(), result.getMin().get(1).getProducer());
		 assertEquals(getresult().getMax().get(1).getProducer(), result.getMax().get(1).getProducer());
		 
    }
	
	private List<Producer> getProducers() {
		 
		List<Producer> producers = new ArrayList();				
		producers.add(new Producer("Steven Perry"));
		producers.add(new Producer("Joel Silver"));
		producers.add(new Producer("Matthew Vaughn"));
		producers.add(new Producer("Simon Kinberg"));
		producers.add(new Producer("Hutch Parker"));
		producers.add(new Producer("Robert Kulzer"));
		producers.add(new Producer("Gregory Goodman"));
		
		return producers;
	}
	
	private List<IInterval> getintervals() {
		 
		List<IInterval> iIntervals = new ArrayList<>();
		iIntervals.add(getInterval("Steven Perry",1990L));
		iIntervals.add(getInterval("Joel Silver",1990L));
		iIntervals.add(getInterval("Joel Silver",1991L));
		iIntervals.add(getInterval("Matthew Vaughn",2002L));
		iIntervals.add(getInterval("Simon Kinberg",2015L));
		iIntervals.add(getInterval("Matthew Vaughn",2015L));
		iIntervals.add(getInterval("Hutch Parker",2015L));
		iIntervals.add(getInterval("Robert Kulzer",2015L));
		iIntervals.add(getInterval("Gregory Goodman",2015L));
		iIntervals.add(getInterval("Simon Kinberg",2028L));
		iIntervals.add(getInterval("Steven Perry",1991L));		
		
		return iIntervals;
	}
	
	private IInterval getInterval(String nome, Long movieyesr) {
			
		ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
		IInterval iInterval = factory.createProjection(IInterval.class);
		iInterval.setProducer(nome);
		iInterval.setMovieYear(movieyesr);
		
		return iInterval; 
	}
	
	private IntervalAwardsProducerDto getresult() {
		
		List<MinMaxResult> min = new ArrayList<>();
		min.add(new MinMaxResult("Joel Silver", 1, 1990L, 1991L));
		min.add(new MinMaxResult("Steven Perry", 1, 1990L, 1991L));
		
		List<MinMaxResult> max = new ArrayList<>();
		max.add(new MinMaxResult("Simon Kinberg", 13, 2015L, 2028L));
		max.add(new MinMaxResult("Matthew Vaughn", 13, 2002L, 2015L));
		
		return new IntervalAwardsProducerDto(min, max);
	}
}
