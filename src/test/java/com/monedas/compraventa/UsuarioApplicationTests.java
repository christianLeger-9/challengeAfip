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

import com.monedas.compraventa.controllers.UsuarioController;
import com.monedas.compraventa.entity.Cuenta;
import com.monedas.compraventa.entity.Usuario;
import com.monedas.compraventa.service.CuentaService;
import com.monedas.compraventa.service.LogService;
import com.monedas.compraventa.service.UsuarioService;

@SpringBootTest
public class UsuarioApplicationTests {

	@InjectMocks
	private UsuarioController usuarioController;
	
	@Mock
	private CuentaService cuentaService;
	
	@Mock
	private LogService logService;
	
	@Mock
	private UsuarioService usuarioService;

	@Test
	void contextLoads() {
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testCrearUsuario() throws Exception { 
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Usuario datos = new Usuario();
        datos.setId(new Long(1));
        datos.setNombre("Christian");
        datos.setDni("33713210");
        datos.setApellido("Leger");
        datos.setCuentas(new ArrayList<Cuenta>());
		ResponseEntity<Usuario> responseEntity = (ResponseEntity<Usuario>) usuarioController.crearUsuario(datos, request);
		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.CREATED.value());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void testCuentasUsuario() throws Exception { 
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Long idUsuarioABuscar = new Long(1);
		ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) usuarioController.cuentasUsuario(idUsuarioABuscar);
//		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
		assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
	}
}
