package com.monedas.compraventa.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monedas.compraventa.entity.Usuario;
import com.monedas.compraventa.repository.UsuarioRepository;
import com.monedas.compraventa.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Usuario saveUser(Usuario user){
		return usuarioRepository.save(user);
	}
	
	public Optional<Usuario> findById(Long idUsuario){
		return usuarioRepository.findById(idUsuario);
	}
}
