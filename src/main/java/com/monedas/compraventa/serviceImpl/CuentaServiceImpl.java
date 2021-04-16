package com.monedas.compraventa.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monedas.compraventa.entity.Cuenta;
import com.monedas.compraventa.repository.CuentaRepository;
import com.monedas.compraventa.repository.UsuarioRepository;
import com.monedas.compraventa.service.CuentaService;

@Service
public class CuentaServiceImpl implements CuentaService {

	@Autowired
	CuentaRepository cuentaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Cuenta saveCuenta(Cuenta cuenta) {
		return cuentaRepository.save(cuenta);
	}
	
	public Optional<Cuenta> findById(Long id){
		return cuentaRepository.findById(id);
	}
	
	public Iterable<Cuenta> findByIdUsuario(Long id){
		return cuentaRepository.findByIdUsuario(id);
	}
	
	public Iterable<Cuenta> findByCbu(int numero){
		return cuentaRepository.findByCbu(numero);
	}
}
