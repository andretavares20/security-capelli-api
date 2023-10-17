package com.andretavares.testesecurity.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.andretavares.testesecurity.entities.Endereco;
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

    private String celular;

    private Boolean isActive;

    @JsonIgnore
    private RegistrationSource source;

    private Date dataNascimento;

    private String genero;

    private List<Endereco> enderecos = new ArrayList<>();
    
}
