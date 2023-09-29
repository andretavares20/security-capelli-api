package com.andretavares.testesecurity.dto;

import java.util.Date;

import com.andretavares.testesecurity.enums.UserRole;
import com.andretavares.testesecurity.source.RegistrationSource;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    
    private String email;

    private String password;

    private String name;

    private UserRole role;

    @JsonIgnore
    private byte[] img;

    private String endereco;

    private String celular;

    private Boolean isActive;

    @JsonIgnore
    private RegistrationSource source;

    private Date dataNascimento;

    private String genero;
    
}
