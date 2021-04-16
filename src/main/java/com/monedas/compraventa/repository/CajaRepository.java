package com.monedas.compraventa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.monedas.compraventa.entity.Caja;


@Repository
public interface CajaRepository extends CrudRepository<Caja, Long> {

	public abstract Iterable<Caja> findByMoneda(String moneda);
}
