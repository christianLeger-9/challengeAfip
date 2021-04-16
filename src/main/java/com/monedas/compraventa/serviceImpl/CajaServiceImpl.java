package com.monedas.compraventa.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monedas.compraventa.entity.Caja;
import com.monedas.compraventa.repository.CajaRepository;
import com.monedas.compraventa.service.CajaService;

@Service
public class CajaServiceImpl implements CajaService {

	@Autowired
	CajaRepository cajaRepository;
	
	public Iterable<Caja> findByMoneda(String moneda){
		return cajaRepository.findByMoneda(moneda);
	}
	
	public Caja saveCaja(Caja caja) {
		return cajaRepository.save(caja);
	}
}
