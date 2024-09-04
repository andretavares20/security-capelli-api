package com.capellimegahair.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.mercadopago.MercadoPagoConfig;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class CapelliMegaHairApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapelliMegaHairApplication.class, args);
		MercadoPagoConfig.setAccessToken("APP_USR-4044482508044042-091415-4c050743d2e1388029b384704b4ec264-170994291");
	}

}
