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

import com.monedas.compraventa.controllers.CompraVentaController;
import com.monedas.compraventa.model.DatosExchange;
import com.monedas.compraventa.service.CajaService;
import com.monedas.compraventa.service.CuentaService;
import com.monedas.compraventa.service.LogService;

@SpringBootTest
class CompraVentaApplicationTests {
	
	@InjectMocks
	private CompraVentaController compraVentaController;
	
	@Mock
	private CuentaService cuentaService;
	
	@Mock
	private LogService logService;
	
	@Mock
	private CajaService cajaService;

	@Test
	void contextLoads() {
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testExchange() throws Exception { 
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        DatosExchange datos = new DatosExchange();
        datos.setIdUsuario(new Long(1));
        datos.setMonedaDestino("USD");
        datos.setMonedaOrigen("ARS");
        datos.setMonto(10000.0);
		ResponseEntity<Double> responseEntity = (ResponseEntity<Double>) compraVentaController.exchange(datos, request);
//		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
	}

}
