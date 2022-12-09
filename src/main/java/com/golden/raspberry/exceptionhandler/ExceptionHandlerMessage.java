package com.golden.raspberry.exceptionhandler;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//classe para mapear a excessão de NOT FOUND e mostra uma mensagem amigável ao usuairo 
@Getter 
@Setter 
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionHandlerMessage {

	private Date timestamp;
	private Integer status;
	private String message;
	
}
