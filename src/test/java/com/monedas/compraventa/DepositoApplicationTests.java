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

import com.monedas.compraventa.controllers.DepositoController;
import com.monedas.compraventa.entity.Cuenta;
import com.monedas.compraventa.model.Deposito;
import com.monedas.compraventa.service.CuentaService;
import com.monedas.compraventa.service.LogService;

@SpringBootTest
public class DepositoApplicationTests {

	@InjectMocks
	private DepositoController depositoController;
	
	@Mock
	private CuentaService cuentaService;
	
	@Mock
	private LogService logService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testDepositar() throws Exception { 
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Deposito datos = new Deposito();
        datos.setCbu(1234);
        datos.setPesos(110.50);
		@SuppressWarnings("unchecked")
		ResponseEntity<Cuenta> responseEntity = (ResponseEntity<Cuenta>) depositoController.depositar(datos, request);
//		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
	}
	
}
