package com.monedas.compraventa.service;

import java.util.Date;

import com.monedas.compraventa.entity.Log;

public interface LogService {
	public void saveLog(Long usuario, String ip, Date fecha, String accion, String subsistema, String descripcion, String hostname);
	public Iterable<Log> findAll();
}
