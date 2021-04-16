package com.monedas.compraventa.service;

import java.util.Optional;

import com.monedas.compraventa.entity.Usuario;

public interface UsuarioService {
	public Usuario saveUser(Usuario user);
	public Optional<Usuario> findById(Long idUsuario);
}
