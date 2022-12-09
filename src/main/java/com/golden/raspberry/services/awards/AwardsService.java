package com.golden.raspberry.services.awards;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.golden.raspberry.dto.AwardDto;
import com.golden.raspberry.dto.CountAwardsProduceDto;
import com.golden.raspberry.dto.IntervalAwardsProducerDto;
import com.golden.raspberry.entities.Awards;
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
		
		List<Awards> awards = awardDto
				  .stream()
				  .map(this::awardDtoToAward)
				  .collect(Collectors.toList());
		
		repository.saveAll(awards);
	}
	
	//mapeia o dto utilizado na leitura de arquivo para a entidade de premiação
	private Awards awardDtoToAward(AwardDto awardDto) {
		Awards awards = new Awards();
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
		
		//busca do banco de dados os rsgistro contendo o maios e o menos tempo de premiações
		//todos os produtores
		List<CountAwardsProduceDto> countAwardsProduce = repository.findCountAwardsProducer()
				.stream()
				.filter(register -> register.getTimeInterval() > 0)
				.map(register -> new CountAwardsProduceDto(
						register.getTimeInterval(), 
						register.getProducer(),
						register.getFollowingWins(),
						register.getpreviousWin()))
				.collect(Collectors.toList());
		
				
		//agrupa por intevalo do tempo de premiação o resultado do banco de dados em uma treeMap 
		TreeMap<Long, List<CountAwardsProduceDto>> collectCountAwardsProduceDto = countAwardsProduce.stream()
				.collect(Collectors.groupingBy(CountAwardsProduceDto::getTimeInterval, 
		        		 TreeMap::new, Collectors.toList()));
		
		//Busca o maior intervalo do tempo de premiação do produtores armazenado na treeMap
		List<CountAwardsProduceDto> maxCountAwardsProduce = collectCountAwardsProduceDto
		        .lastEntry()
		        .getValue();
		
		//Busca o menor intervalo do tempo de premiação do produtores armazenado na treeMap
		List<CountAwardsProduceDto> minCountAwardsProduce = collectCountAwardsProduceDto
		        .firstEntry()
		        .getValue();
		
		return new IntervalAwardsProducerDto(maxCountAwardsProduce, minCountAwardsProduce);
	}
	
}
