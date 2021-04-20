package com.monedas.compraventa.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monedas.compraventa.entity.Cliente;
import com.monedas.compraventa.repository.ClienteRepository;
import com.monedas.compraventa.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	ClienteRepository usuarioRepository;
	
	public Cliente saveUser(Cliente user){
		return usuarioRepository.save(user);
	}
	
	public Optional<Cliente> findById(Long idUsuario){
		return usuarioRepository.findById(idUsuario);
	}
}
