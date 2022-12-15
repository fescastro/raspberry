package com.golden.raspberry;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.golden.raspberry.dto.AwardDto;
import com.golden.raspberry.dto.CsvDto;
import com.golden.raspberry.dto.ProducerDto;
import com.golden.raspberry.services.awards.AwardsService;
import com.golden.raspberry.services.producers.ProducersService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@SpringBootApplication
public class RaspberryApiApplication {
	
	@Autowired
	private AwardsService awardsService;
	
	@Autowired
	private ProducersService producersService;
	
	public static void main(String[] args) {
		SpringApplication.run(RaspberryApiApplication.class, args);
	}
	
	@Bean
	public void addregistersFileCsv() {		
		
		//readerFileCsv() retorna um dto mapeando os produtores e as premiações para
		//inserir em suas respectivas tabelas
		CsvDto csvDto = readerFileCsv();
		
		//csvDto.getAwards() retorna uma lista de premiações com seus respectivos produtores
		//para inserir na tabela Awards e na tabela mapeada de muitos para muitos Awards_producers
		awardsService.addAwardsFileCsv(csvDto.getAwards());
		
	}
	
	private CsvDto readerFileCsv() {		
		
		CsvDto csvDto =  new CsvDto(new ArrayList<>(), new ArrayList<>());
		try {
			
			//lê o arquivo no diretório especificado
			Reader reader = Files.newBufferedReader(Paths.get("filecsv\\movielist.csv"));
		    CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
	        CSVReader csvReader = new CSVReaderBuilder(reader)
	        		.withCSVParser(parser)
	        		.withSkipLines(1)
	        		.build();

	        //csvReader.readAll() da biblioteca OpenCsv retorna uma lista com as linhas mapeadas do arquivo;
	        List<String[]> goldenAwards = csvReader.readAll();
	        
	        //percorre o array mapeado, onde cada posição do array é uma linha do arquivo
	        for (String[] award : goldenAwards) {	
	        	
	        	//mapeia em variaveis cada coluna do arquivo do registro atual do laço.
	        	Long years = Long.parseLong(award[0]);
	        	String title = award[1];
	        	String studios = award[2];
	        	String producers = award[3];
				Boolean winner = "yes".equalsIgnoreCase(award[4]);			
				
				//retorna uma lista com todos os produtores responsaveis pela produção do filme do registro atual do laço.
				List<String> list = Arrays.stream(producers.replace(",", " and ").split(" and "))
						.collect(Collectors.toList());
				
				//elimina valores nulos da lista caso encontrado
				List<ProducerDto> producerList = producerSingle(list);
				
				//só adicioona na lista o filme que ganhouo o premio
				if(winner) {
					//adicona na lista os produtores responsaveis pelo filme do registro atual do laço.
					csvDto.getProducers().addAll(producerList);		
					
					//adiciona a premiação com seua respectivos valores lido do arquivo e seus produtores responsáveis.
					csvDto.getAwards().add(new AwardDto(years,title,studios,producers,winner,producerList));
				}
	        }
			
		}
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		return csvDto;
	}
	
	private List<ProducerDto> producerSingle(List<String> producers) {		
		return producers.stream()
				.filter(producer -> !producer.isEmpty() && !producer.isBlank())
				.map(producer -> {
			return new ProducerDto(producer.trim());
		}).collect(Collectors.toList());
	}

}
