package com.monedas.compraventa.model;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CotizacionResponse {
	
	 boolean success;
	 int timestamp;
	 String base;
	 String date;
	 
	 HashMap<String,Double> rates; 
}
