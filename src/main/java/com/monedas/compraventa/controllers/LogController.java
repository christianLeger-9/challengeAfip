package com.monedas.compraventa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.monedas.compraventa.entity.Log;
import com.monedas.compraventa.serviceImpl.LogServiceImpl;

@RestController
@RequestMapping("/logController")
public class LogController {

	@Autowired
	LogServiceImpl logService;
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	//Devuelve todos los logs
	public ResponseEntity<?> logs() {
		Iterable<Log> logs = null;
		try {
			logs = logService.findAll();
		}
		catch( Exception e) { 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getMessage()));
		 }
		return new ResponseEntity<>(logs, HttpStatus.OK);
	}
}
