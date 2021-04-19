package com.monedas.compraventa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.monedas.compraventa.controllers.CuentaController;
import com.monedas.compraventa.entity.Caja;
import com.monedas.compraventa.entity.Cuenta;
import com.monedas.compraventa.service.CuentaService;
import com.monedas.compraventa.service.LogService;

@SpringBootTest
public class CuentaApplicationTests {

	@InjectMocks
	private CuentaController cuentaController;
	
	@Mock
	private CuentaService cuentaService;
	
	@Mock
	private LogService logService;
	
	@Test
	void contextLoads() {
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testAbrirCuenta() throws Exception { 
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Cuenta datos = new Cuenta();
        datos.setIdUsuario(new Long(1));
        datos.setId(new Long(1));
        datos.setCbu(12345);
        Caja caja = new Caja();
        caja.setId(new Long(1));
        caja.setMoneda("ARS");
        caja.setMonto(1250.9);
        caja.setTipoCaja("Ahorro");
        
        List<Caja> cajas = new ArrayList<Caja>();
        cajas.add(caja);
        datos.setCaja(cajas);
        
		ResponseEntity<Cuenta> responseEntity = (ResponseEntity<Cuenta>) cuentaController.abrirCuenta(datos);
//		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
	}
}
