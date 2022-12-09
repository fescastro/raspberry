package com.golden.raspberry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor
@NoArgsConstructor
public class CountAwardsProduceDto {
	
	private Long timeInterval;
	
	private String producer;
	
	private Long followingWins;
	
	private Long previousWin;

}
