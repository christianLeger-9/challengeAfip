package com.monedas.compraventa.controllers;

import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.monedas.compraventa.constants.Constants;
import com.monedas.compraventa.entity.Caja;
import com.monedas.compraventa.entity.Cuenta;
import com.monedas.compraventa.model.Deposito;
import com.monedas.compraventa.service.CuentaService;
import com.monedas.compraventa.service.LogService;

@RestController
@RequestMapping("/depositoController")
public class DepositoController {

	@Autowired
	CuentaService cuentaService;
	
	@Autowired
	LogService logService;
	
	@RequestMapping(value="/", method=RequestMethod.PUT)
	//agrega la cantidad de pesos enviada por parametro a la cuenta enviada por parametro, validando que exista la cuenta
	public ResponseEntity<?> depositar(@RequestBody Deposito deposito, HttpServletRequest request) {
		Iterable<Cuenta> cu = null;
		boolean ok = false;
		try {
			String ip = (request.getHeader("X-FORWARDED-FOR") == null) ? request.getRemoteAddr() : request.getHeader("X-FORWARDED-FOR");
			cu = cuentaService.findByCbu(deposito.getCbu());
			Iterator<Cuenta> cuentas = cu.iterator();
			if(!cuentas.hasNext()) {
				throw new Exception("El cbu no existe para depositar el saldo pedido : " + deposito.getPesos());
			} else {
				Cuenta c = cuentas.next();
				Iterator<Caja> cajas = c.getCaja().iterator();
				while(!ok && cajas.hasNext()) {
					Caja caja = cajas.next();
					if(caja.getMoneda().equals("ARS")) {
						//poner en el readme q cada vez q se agregue una caja en mopneda va ARS O USD O EUR 
						caja.setMonto(caja.getMonto() + deposito.getPesos());
						ok = true;
					}
				}
				if (ok) {
					cuentaService.saveCuenta(c);
					logService.saveLog(c.getIdUsuario(), ip, new Date(), Constants.LOG_ACCION_EXITO, Constants.LOG_SUBSISTEMA, "Se deposito $" 
					+ deposito.getPesos() + " en la cuenta con cbu " + deposito.getCbu(), request.getLocalName());
				} else {
					throw new Exception("El cbu seleccionado no posee cajas en ARS, por lo tanto no se puede depositar el monto pedido : $"+deposito.getPesos());
				}
			}
		}
		catch( Exception e) { 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getMessage()));
		 }
		return new ResponseEntity<>(cu, HttpStatus.OK);
	}
}