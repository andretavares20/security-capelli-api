package com.andretavares.testesecurity.dto;

import lombok.Data;

@Data
public class UserGoogleProviderDto {
    private String aud;
    private String azp;
    private String email;
    private boolean emailVerified;
    private long exp;
    private String familyName;
    private String givenName;
    private long iat;
    private String iss;
    private String jti;
    private String locale;
    private String name;
    private long nbf;
    private String picture;
    private String sub;

    // Getters e Setters aqui

    // Você também pode adicionar construtores, métodos auxiliares, etc.
} 
