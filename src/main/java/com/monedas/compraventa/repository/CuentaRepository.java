package com.monedas.compraventa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.monedas.compraventa.entity.Cuenta;


@Repository
public interface CuentaRepository extends CrudRepository<Cuenta, Long> {
	
	public abstract Iterable<Cuenta> findByCbu(int numero);
	public abstract Iterable<Cuenta> findByIdUsuario(Long idUsuario);
}
