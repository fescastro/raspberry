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
public class CsvDto {

	private List<AwardDto> awards;
	private List<ProducerDto> producers;
}
