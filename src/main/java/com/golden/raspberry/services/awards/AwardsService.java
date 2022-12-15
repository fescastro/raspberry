package com.golden.raspberry.services.awards;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.golden.raspberry.dto.AwardDto;
import com.golden.raspberry.dto.AwardGroupProducerDto;
import com.golden.raspberry.dto.IntervalAwardsProducerDto;
import com.golden.raspberry.dto.MinMaxResult;
import com.golden.raspberry.entities.Award;
import com.golden.raspberry.entities.Producer;
import com.golden.raspberry.projection.IInterval;
import com.golden.raspberry.repositories.IAwardsRepository;
import com.golden.raspberry.services.producers.IProducersService;

@Service
public class AwardsService implements IAwardsService {

	private IAwardsRepository repository;
	
	private IProducersService producersService;

	@Autowired
	public AwardsService(IAwardsRepository awardRepositoty,
			IProducersService producersService) {
		this.repository = awardRepositoty;
		this.producersService = producersService;
	}
	
	
	//adiciona a lista das premiações lidas do arquivo
	public void addAwardsFileCsv(List<AwardDto> awardDto) {
		
		List<Award> awards = awardDto
				  .stream()
				  .map(this::awardDtoToAward)
				  .collect(Collectors.toList());
		
		repository.saveAll(awards);
	}
	
	//mapeia o dto utilizado na leitura de arquivo para a entidade de premiação
	private Award awardDtoToAward(AwardDto awardDto) {
		Award awards = new Award();
		awards.setMovieYear(awardDto.getMovieYear());
		awards.setProducers(awardDto.getProducers());
		awards.setStudios(awardDto.getStudios());
		awards.setTitle(awardDto.getTitle());
		awards.setWinner(awardDto.isWinner());
		awards.setProducerList(awardDto.getProducersList()
				  .stream()
				  .map(producer -> producersService.producersDtoToProducer(producer))
				  .collect(Collectors.toList()));
		return awards;
	}
	
	//retoma o dot com o resultado final da solução proposta no teste
	public IntervalAwardsProducerDto getMinMaxIntervalAwardsProducer() {
		
		//busca todos os produtores
		List<Producer> producers = producersService.findAll()
				.stream()
				.filter(distinctByKey(producer -> producer.getName()))
				.collect(Collectors.toList());
		
		List<IInterval> intervals = repository.findAwardForProducer();
		Map<String, List<AwardGroupProducerDto>> intervalMap = new HashMap<>();
		
		//percorre os produtores para separar em um map o intervalos por produtor
		producers.stream()
		.forEach(produce -> {
			List<AwardGroupProducerDto> intervalForProducer = intervals.stream()
			.filter(interval -> interval.getProducer().contains(produce.getName()))
			.map(interval -> new AwardGroupProducerDto(interval.getProducer(), interval.getMovieYear()))
			.collect(Collectors.toList());
			
			intervalMap.put(produce.getName(),intervalForProducer);
		});			
		
		List<MinMaxResult> interval = new ArrayList<>();
		
		//precorre o map para identificar o max e o min de todos os produtores
		for (Entry<String, List<AwardGroupProducerDto>> map : intervalMap.entrySet()) {
			for (int i = 0; i < map.getValue().size() - 1; i++) {
				AwardGroupProducerDto elemet = map.getValue().get(i);
				AwardGroupProducerDto nextElemet = map.getValue().get(i+1);
				Long min = Math.min(elemet.getMovieYear(), nextElemet.getMovieYear());
				Long max = Math.max(elemet.getMovieYear(), nextElemet.getMovieYear());
				MinMaxResult result = new MinMaxResult(map.getKey(), max - min, min, max);
				interval.add(result);
			}
		}				
		
		return new IntervalAwardsProducerDto(getMinList(interval), getMaxList(interval));
		
	}
	
	private List<MinMaxResult> getMinList(List<MinMaxResult> interval) {
		
		long min = interval
 	    .stream()
 	    .mapToLong(MinMaxResult::getInterval)
 	    .min()
 	    .getAsLong();		
		
		return interval
			   .stream()
			   .filter(element -> element.getInterval() == min)
			   .collect(Collectors.toList());
		
	}
	
	private List<MinMaxResult> getMaxList(List<MinMaxResult> interval) {
		
		long max = interval
 	    .stream()
 	    .mapToLong(MinMaxResult::getInterval)
 	    .max()
 	    .getAsLong();
		
		return interval
			   .stream()
			   .filter(element -> element.getInterval() == max)
			   .collect(Collectors.toList());
		
	}
	
	private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    KeySetView<Object, Boolean> seen = ConcurrentHashMap.newKeySet();
	    return t -> seen.add(keyExtractor.apply(t));
	}
	
}
