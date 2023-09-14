package com.andretavares.testesecurity.entities;

import java.util.Date;

import com.andretavares.testesecurity.dto.UserDto;
import com.andretavares.testesecurity.enums.UserRole;
import com.andretavares.testesecurity.source.RegistrationSource;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="user_entity")
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    private Date dob;

    private String address;

    private String gender;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name="source")
    @Enumerated(EnumType.STRING)
    private RegistrationSource source;

    public UserDto getUserDto(){
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setEmail(email);
        userDto.setAddress(address);
        userDto.setDob(dob);
        userDto.setGender(gender);
        userDto.setSource(source);
        return userDto;
    }

}
