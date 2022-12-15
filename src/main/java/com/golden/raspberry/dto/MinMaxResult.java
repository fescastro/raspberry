package com.golden.raspberry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@AllArgsConstructor
@NoArgsConstructor
public class MinMaxResult {

	String producer;
	long interval;
	Long previousWin;
	Long followingWin;
	
}
