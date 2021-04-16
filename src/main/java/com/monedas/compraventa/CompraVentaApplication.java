package com.monedas.compraventa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching 
public class CompraVentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompraVentaApplication.class, args);
	}

}
