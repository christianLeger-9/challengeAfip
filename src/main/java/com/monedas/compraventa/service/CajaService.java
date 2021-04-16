package com.monedas.compraventa.service;

import com.monedas.compraventa.entity.Caja;

public interface CajaService {
	public Iterable<Caja> findByMoneda(String moneda);
	public Caja saveCaja(Caja caja);
}
