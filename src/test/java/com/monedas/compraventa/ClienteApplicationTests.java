package com.monedas.compraventa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.monedas.compraventa.controllers.ClienteController;
import com.monedas.compraventa.entity.Cliente;
import com.monedas.compraventa.entity.Cuenta;
import com.monedas.compraventa.service.ClienteService;
import com.monedas.compraventa.service.CuentaService;
import com.monedas.compraventa.service.LogService;


@SpringBootTest
public class ClienteApplicationTests {

	@InjectMocks
	private ClienteController usuarioController;
	
	@Mock
	private CuentaService cuentaService;
	
	@Mock
	private LogService logService;
	
	@Mock
	private ClienteService clienteService;

	@Test
	void contextLoads() {
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testCrearUsuario() throws Exception { 
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Cliente datos = new Cliente();
        datos.setId(new Long(1));
        datos.setNombre("Christian");
        datos.setDni("33713210");
        datos.setApellido("Leger");
        datos.setCuentas(new ArrayList<Cuenta>());
		ResponseEntity<Cliente> responseEntity = (ResponseEntity<Cliente>) usuarioController.crear(datos, request);
		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.CREATED.value());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testCuentasUsuario() throws Exception { 
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Long idUsuarioABuscar = new Long(1);
		ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) usuarioController.cuentas(idUsuarioABuscar);
//		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
	}
}
