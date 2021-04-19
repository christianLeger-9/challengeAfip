package com.monedas.compraventa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.monedas.compraventa.controllers.CajaController;
import com.monedas.compraventa.entity.Caja;
import com.monedas.compraventa.entity.Cuenta;
import com.monedas.compraventa.service.CajaService;
import com.monedas.compraventa.service.CuentaService;
import com.monedas.compraventa.service.LogService;

@SpringBootTest
public class CajaApplicationTests {

	@InjectMocks
	private CajaController cajaController;
	
	@Mock
	private CuentaService cuentaService;
	
	@Mock
	private CajaService cajaService;
	
	@Mock
	private LogService logService;
	
	@Test
	void contextLoads() {
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testAgregarCajaACuenta() throws Exception { 
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Caja datos = new Caja();
        datos.setId(new Long(1));
        datos.setMoneda("ARS");
        datos.setMonto(1500);
        datos.setTipoCaja("Ahorro");
        
        int cbu = 12345;
		ResponseEntity<Cuenta> responseEntity = (ResponseEntity<Cuenta>) cajaController.agregarCajaACuenta(datos, cbu, request);
//		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
	}
	
}
