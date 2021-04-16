package com.monedas.compraventa.service;

import java.util.Optional;

import com.monedas.compraventa.entity.Cuenta;

public interface CuentaService {
	public Cuenta saveCuenta(Cuenta cuenta);
	public Optional<Cuenta> findById(Long id);
	public Iterable<Cuenta> findByIdUsuario(Long id);
	public Iterable<Cuenta> findByCbu(int numero);
}
