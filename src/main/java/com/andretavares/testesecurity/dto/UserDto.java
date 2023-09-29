package com.andretavares.testesecurity.dto;

import java.util.Date;

import com.andretavares.testesecurity.enums.UserRole;
import com.andretavares.testesecurity.source.RegistrationSource;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    
    private String email;

    private String password;

    private String name;

    private UserRole role;

    private byte[] img;

    private String endereço;

    private String celular;

    private Boolean isActive;

    private RegistrationSource source;

    private Date dataNascimento;

    private String genero;
    
}
