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
	
	List<MinMaxResult> min;
	List<MinMaxResult> max;

}
