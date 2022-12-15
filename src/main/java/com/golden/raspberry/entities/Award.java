package com.golden.raspberry.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter 
@AllArgsConstructor
@NoArgsConstructor
public class Awards {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private Long movieYear;
	
	@NotNull
	private String title;
	
	@NotNull
	private String studios;
	
	@NotNull
	private String producers;
	
	@NotNull
	private boolean winner;	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="AWARDS_PRODUCERS",
	           joinColumns={@JoinColumn(name="AWARDS_ID")},
	           inverseJoinColumns={@JoinColumn(name="PRODUCERS_ID")})
	private List<Producers> producerList;
	
}
