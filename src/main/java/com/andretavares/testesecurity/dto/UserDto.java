package com.andretavares.testesecurity.dto;

import java.time.LocalDate;
import java.util.Date;

import com.andretavares.testesecurity.enums.UserRole;
import com.andretavares.testesecurity.source.RegistrationSource;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;

    private LocalDate dataNascimento;

    private String address;

    private String gender;

    private UserRole role;

    private RegistrationSource source;

}
