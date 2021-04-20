package com.monedas.compraventa.controllers;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.monedas.compraventa.entity.Cuenta;
import com.monedas.compraventa.entity.Cliente;
import com.monedas.compraventa.repository.ClienteRepository;
import com.monedas.compraventa.service.CuentaService;
import com.monedas.compraventa.service.LogService;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
	
	@Autowired
	CuentaService cuentaService;
	
	@Autowired
	ClienteRepository usuarioRepository;
	
	@Autowired
	LogService logService;
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	//agrega una nueva cuenta cargada previamente con su respectiva caja, validando que exista el usuario para asignarsela.
	public ResponseEntity<?> abrirCuenta(@RequestBody Cuenta cuenta) {
		Cuenta cu = null;
		try {
			Optional<Cliente> usu = usuarioRepository.findById(cuenta.getIdUsuario());
			if (usu.isPresent()) {
				//valido que el cbu no exista
				boolean ok = validarCbu(cuenta.getCbu());
				if(ok) {
					cu = cuentaService.saveCuenta(cuenta);
				}
			} else {
				throw new Exception("El idUsuario no existe para poder agregarle una nueva cuenta");
			}
		}
		catch( Exception e) { 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getMessage()));
		 }
		return new ResponseEntity<>(cu, HttpStatus.CREATED);
	}
	
	private boolean validarCbu(int cbu) throws Exception {
		//busco el cbu ingresado para saber si ya hay una cuenta .
		Iterable<Cuenta> cuentaCbu = cuentaService.findByCbu(cbu);
		Iterator<Cuenta> cuentasCbu = cuentaCbu.iterator();
		if(cuentasCbu.hasNext()) {
			throw new Exception("El cbu ya existe");
		} else {
			return true;
		}
	}
}
