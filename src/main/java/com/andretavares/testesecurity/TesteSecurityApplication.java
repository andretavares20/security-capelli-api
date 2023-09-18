package com.andretavares.testesecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mercadopago.MercadoPagoConfig;

@SpringBootApplication
public class TesteSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteSecurityApplication.class, args);
		MercadoPagoConfig.setAccessToken("TEST-4044482508044042-091415-d2fee9a02d0db75bb6061a29363f3bb0-170994291");
	}

}
