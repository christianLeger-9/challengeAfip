package com.monedas.compraventa.serviceImpl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monedas.compraventa.entity.Log;
import com.monedas.compraventa.repository.LogRepository;
import com.monedas.compraventa.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired 
	private LogRepository logRepository;

	@Transactional
	public void saveLog(Long usuario, String ip, Date fecha, String accion, String subsistema, String descripcion, String hostname) {
		Log log = new Log(usuario, ip, fecha, accion, subsistema, descripcion, hostname);
		logRepository.save(log);
	}
	
	public Iterable<Log> findAll() {
		return logRepository.findAll();
	}
}