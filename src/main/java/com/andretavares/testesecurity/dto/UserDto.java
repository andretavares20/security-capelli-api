package com.andretavares.testesecurity.dto;

import java.util.Date;

import com.andretavares.testesecurity.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;

    private Date dob;

    private String address;

    private String gender;

    private UserRole role;

}
