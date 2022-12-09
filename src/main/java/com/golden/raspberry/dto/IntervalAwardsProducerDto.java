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
public class IntervalAwardsProducerDto {
	
	private List<CountAwardsProduceDto> max;
	
	private List<CountAwardsProduceDto> min;

}
