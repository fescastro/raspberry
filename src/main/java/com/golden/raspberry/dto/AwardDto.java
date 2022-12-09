package com.golden.raspberry.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor
@NoArgsConstructor
public class AwardDto {
	
	private Long movieYear;
	
	private String title;
	
	private String studios;
	
	private String producers;
	
	private boolean winner;
	
	private List<ProducerDto> producersList;

}
