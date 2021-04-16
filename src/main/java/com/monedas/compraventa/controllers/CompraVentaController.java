package com.monedas.compraventa.controllers;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.monedas.compraventa.constants.Constants;
import com.monedas.compraventa.entity.Caja;
import com.monedas.compraventa.entity.Cuenta;
import com.monedas.compraventa.entity.Usuario;
import com.monedas.compraventa.model.CotizacionResponse;
import com.monedas.compraventa.model.DatosExchange;
import com.monedas.compraventa.repository.UsuarioRepository;
import com.monedas.compraventa.service.CajaService;
import com.monedas.compraventa.service.CuentaService;
import com.monedas.compraventa.service.LogService;

@RestController
@RequestMapping("/compraVenta")
public class CompraVentaController {

	@Autowired
	CuentaService cuentaService;
	
	@Autowired
	CajaService cajaService;
	
	@Autowired
	LogService logService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Cacheable("cotizaciones")
	private CotizacionResponse obtenerCotizacion(String url) throws Exception {
		HttpEntity<CotizacionResponse> response = restTemplate.getForEntity(url, CotizacionResponse.class);
		CotizacionResponse cotizacion = null;
		if (response.getBody().isSuccess()) {
			cotizacion = response.getBody();
		} else {
			throw new Exception("Error al buscar cotizaciones del dia");
		}
		return cotizacion;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	//Se envia un monto a cambiar
	public ResponseEntity<?> exchange(@RequestBody DatosExchange datos, HttpServletRequest request) {
		Iterable<Cuenta> cu = null;
		Double valorFinal = 0.0;
		boolean ok = false;
		try {
			String ip = (request.getHeader("X-FORWARDED-FOR") == null) ? request.getRemoteAddr() : request.getHeader("X-FORWARDED-FOR");
			//evaluo si existe el usuario que quiere hacer el cambio de moneda.
			Optional<Usuario> usu = usuarioRepository.findById(datos.getIdUsuario());
			if (usu.isPresent()) {
				//busco las cuentas asociadas a ese usuario y me quedo con las cajas.
				cu = cuentaService.findByIdUsuario(datos.getIdUsuario());
				Iterator<Cuenta> cuentas = cu.iterator();
				Cuenta c = cuentas.next();
				Iterator<Caja> cajas = c.getCaja().iterator();
				while(!ok && cajas.hasNext()) {
					Caja caja = cajas.next();
					//evaluo si la moneda origen esta en alguna de las cajas del usuario.
					if(caja.getMoneda().equals(datos.getMonedaOrigen())) {
						if(caja.getMonto() >= datos.getMonto()) {
							//evaluo si el usuario tiene alguna caja con la moneda destino.
							Caja cajaDestino = poseeCajaConMonedaDestino(datos.getMonedaDestino());
							//busco las cotizaciones del dia y guardo el valor de cada uno de los que vienen como parametro.
							CotizacionResponse coti = obtenerCotizacion(Constants.URL_FIXER_IO);
					    	Double valorCotiOrigen = buscarOrigen(coti, datos.getMonedaOrigen());
					    	Double valorCotiDestino = buscarDestino(coti, datos.getMonedaDestino());
					    	valorFinal = (datos.getMonto() * valorCotiDestino) / valorCotiOrigen;
					    	
					    	//Le tengo que restar el saldo a la caja origen y el valorFinal agregarlo a la caja destino.
					    	caja.setMonto(caja.getMonto()-datos.getMonto());
					    	cajaDestino.setMonto(cajaDestino.getMonto()+valorFinal);
					    	cajaService.saveCaja(caja);
					    	cajaService.saveCaja(cajaDestino);
					    	ok = true;
					    	logService.saveLog(datos.getIdUsuario(), ip, new Date(), Constants.LOG_ACCION_EXITO, Constants.LOG_SUBSISTEMA, 
								"Cambio de moneda " +datos.getMonedaOrigen()+ " a " +datos.getMonedaDestino()+ "por el monto" + datos.getMonto(), request.getLocalName());
						} else {
							throw new Exception("Usted no posee el monto seleccionado para realizar la operación");
						}
					}
				}
				if (!ok) {
					throw new Exception("Usted no posee una cuenta en " + datos.getMonedaOrigen()) ;
				}
			} else {
				throw new Exception("El idUsuario no existe en la Base de Datos");
			}
		}
		catch( Exception e) { 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getMessage()));
		 }
		return new ResponseEntity<>(valorFinal, HttpStatus.OK);
	}
	
	private Caja poseeCajaConMonedaDestino(String monedaDestino) throws Exception {
		Iterable<Caja> cajaDestino = cajaService.findByMoneda(monedaDestino);
		Iterator<Caja> cajaMonedaDestino = cajaDestino.iterator();
		Caja caja = null;
		if(!cajaMonedaDestino.hasNext()) {
			throw new Exception("Usted no posee una caja en " + monedaDestino + " para realizar la compra." ) ;
		} else {
			caja = cajaMonedaDestino.next();
		}
		return caja;
	}

	private Double buscarOrigen(CotizacionResponse coti, String origen) {
		Iterator rate = coti.getRates().entrySet().iterator();
		boolean ok = false;
		Double valor = 0.0;
		while (!ok && rate.hasNext()) {
	    	Map.Entry<String,Double> rateOrigen = (Map.Entry<String, Double>) rate.next();
	    	if (rateOrigen.getKey().equals(origen)) {
	    		valor = rateOrigen.getValue();
	    	}
		}
		return valor;
	}
	
	private Double buscarDestino(CotizacionResponse coti, String destino) {
		Iterator rate = coti.getRates().entrySet().iterator();
		boolean ok = false;
		Double valor = 0.0;
		while (!ok && rate.hasNext()) {
	    	Map.Entry<String,Double> rateDestino = (Map.Entry<String, Double>) rate.next();
	    	if (rateDestino.getKey().equals(destino)) {
	    		valor = rateDestino.getValue();
	    	}
		}
		return valor;
	}
}