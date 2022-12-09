package com.golden.raspberry.exceptionhandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = "com.golden.raspberry.controller")
public class AwardsControllerAdvice {

	//intercepta na requisicões no controler a exceção de NOT_FOUD
	@ResponseBody
	@ExceptionHandler(AwardsNotFoundException.class)
	public ResponseEntity<ExceptionHandlerMessage> awardsCreatedNotFound(AwardsNotFoundException awardsNotFound) {
		ExceptionHandlerMessage error = new ExceptionHandlerMessage(
				new Date(), HttpStatus.NOT_FOUND.value(), "Awards not found");
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		
	}
	
	//intercepta na requisicões no controler o exceção invalidade de algum campo
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionHandlerMessage> awardsNotValid(MethodArgumentNotValidException notValid) {
		
		BindingResult result = notValid.getBindingResult();
		
		StringBuilder message = new StringBuilder(" The following fields cannot be null");
		result.getFieldErrors().forEach(fieldError -> {
			message.append(" (");
			message.append(fieldError.getField());
			message.append(")");
		});
		
		ExceptionHandlerMessage error = new ExceptionHandlerMessage(
				new Date(), HttpStatus.BAD_REQUEST.value(), message.toString());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
