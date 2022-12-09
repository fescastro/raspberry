package com.golden.raspberry.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.golden.raspberry.entities.Awards;
import com.golden.raspberry.projection.ICountAwardsProduce;

public interface IAwardsRepository extends JpaRepository<Awards, Long> {

	//query que retorna o intervalo de tempo,maior ano, menos ano e o produtor das premiações 
	//agrupada poe produtor onde o campeão sejá verdadeito
	@Query(
	  value = " select max(a.movie_year) - min(a.movie_year) timeInterval, "
	  		+ " max(a.movie_year) followingWins, "
	  		+ " min(a.movie_year) previousWin, "
	  		+ " b.name producer "
	  		+ " from AWARDS_PRODUCERS c "
	  		+ " inner join AWARDS a on a.ID = c.AWARDS_ID "
	  		+ " inner join PRODUCERS b on b.id = c.PRODUCERS_ID "
	  		+ " group by b.name ", 
	  nativeQuery = true)
	List<ICountAwardsProduce> findCountAwardsProducer();
}

