package com.monedas.compraventa.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.monedas.compraventa.entity.Cuenta;
import com.monedas.compraventa.entity.Cliente;
import com.monedas.compraventa.service.CuentaService;
import com.monedas.compraventa.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	ClienteService clienteService;
	
	@Autowired
	CuentaService cuentaService;
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	//agrega una nueva cuenta cargada previamente
	public ResponseEntity<?> crear(@RequestBody Cliente usuario, HttpServletRequest request) {
		Cliente usu = new Cliente();
		try {
			//el array de cuentas de usuario, cuando este se crea, se crea como array vacio ya que hay otro enpoint
			//en el cual se crea una cuenta a un usuario existente.
			usuario.setCuentas(new ArrayList<Cuenta>());
			usu = clienteService.saveUser(usuario);
		} catch( Exception e) { 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getMessage()));
		}
		return new ResponseEntity<>(usu, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{idUsuario}", method=RequestMethod.GET)
	//busca las cuentas asociadas al usuario, validando que exista
	public ResponseEntity<?> cuentas(@PathVariable(required=true,value="idUsuario")Long idUsuario) {
		Iterable<Cuenta> cu = null;
		try {
			Optional<Cliente> usu = clienteService.findById(idUsuario);
			if (usu.isPresent()) {
				cu = cuentaService.findByIdUsuario(idUsuario);
				Iterator<Cuenta> cuentas = cu.iterator();
				if (!cuentas.hasNext()) {
					throw new Exception("El idUsuario aún no posee cuentas.");
				}
			} else {
				throw new Exception("El idUsuario no existe en la Base de Datos.");
			}
		}
		catch( Exception e) { 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getMessage()));
		 }
		return new ResponseEntity<>(cu, HttpStatus.OK);
	}
}
