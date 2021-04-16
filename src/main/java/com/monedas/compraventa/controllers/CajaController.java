package com.monedas.compraventa.controllers;

import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.monedas.compraventa.constants.Constants;
import com.monedas.compraventa.entity.Caja;
import com.monedas.compraventa.entity.Cuenta;
import com.monedas.compraventa.service.CajaService;
import com.monedas.compraventa.service.CuentaService;
import com.monedas.compraventa.service.LogService;

@RestController
@RequestMapping("/cajaController")
public class CajaController {

	@Autowired
	CajaService cajaService;
	
	@Autowired
	LogService logService;
	
	@Autowired
	CuentaService cuentaService;
	
	@RequestMapping(value="/{cbu}/", method=RequestMethod.POST)
	//agrega una nueva cuenta cargada previamente con su respectiva caja, validando que exista el usuario para asignarsela.
	public ResponseEntity<?> agregarCajaACuenta(@RequestBody Caja caja, @PathVariable(required=true,value="cbu") int cbu, HttpServletRequest request) {
		Iterable<Cuenta> cuentas = null;
		try {
			String ip = (request.getHeader("X-FORWARDED-FOR") == null) ? request.getRemoteAddr() : request.getHeader("X-FORWARDED-FOR");
			cuentas = cuentaService.findByCbu(cbu);
			Iterator<Cuenta> cuentasCbu = cuentas.iterator();
			if(!cuentasCbu.hasNext()) {
				throw new Exception("El cbu no existe ");
			} else {
				Cuenta c = cuentasCbu.next();
				c.getCaja().add(caja);
				cuentaService.saveCuenta(c);
				logService.saveLog(c.getIdUsuario(), ip, new Date(), Constants.LOG_ACCION_EXITO, Constants.LOG_SUBSISTEMA, 
						"Nueva caja en cbu " +cbu+ " con monto" + caja.getMonto(), request.getLocalName());
			}
		}
		catch( Exception e) { 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getMessage()));
		 }
		return new ResponseEntity<>(cuentas, HttpStatus.OK);
	}
}
