package com.golden.raspberry.services.awards;

import java.util.List;

import com.golden.raspberry.dto.AwardDto;
import com.golden.raspberry.dto.IntervalAwardsProducerDto;

public interface IAwardsService {


void addAwardsFileCsv(List<AwardDto> awardDto);

IntervalAwardsProducerDto getMinMaxIntervalAwardsProducer();
	
}
