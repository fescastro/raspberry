package com.golden.raspberry.projection;

//projeção para mapear o resultado do select
public interface ICountAwardsProduce {

	Long getTimeInterval();
	String getProducer();
	Long getFollowingWins();
	Long getpreviousWin();
}
