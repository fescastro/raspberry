package com.golden.raspberry.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.golden.raspberry.dto.AwardGroupProducerDto;
import com.golden.raspberry.entities.Award;
import com.golden.raspberry.projection.IInterval;

public interface IAwardsRepository extends JpaRepository<Award, Long> {

	@Query(
	  value = " select b.name producer, " 
			+ " a.movie_year movieYear "  
			+ " from AWARDS_PRODUCERS c " 
			+ " inner join AWARD a on a.ID = c.AWARDS_ID " 
			+ " inner join PRODUCER b on b.id = c.PRODUCERS_ID "
			+ " order by a.movie_year ",
	  nativeQuery = true)
	List<IInterval> findAwardForProducer();
}

