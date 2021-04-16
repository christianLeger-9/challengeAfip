package com.monedas.compraventa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatosExchange {
	
	Long idUsuario;
	String monedaOrigen;
	String monedaDestino;
	Double monto;
}
