package com.monedas.compraventa.service;

import java.util.Optional;

import com.monedas.compraventa.entity.Cliente;

public interface ClienteService {
	public Cliente saveUser(Cliente user);
	public Optional<Cliente> findById(Long idUsuario);
}
