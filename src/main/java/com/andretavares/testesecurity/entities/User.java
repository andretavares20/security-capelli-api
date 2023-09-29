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
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    private String endereço;

    private String celular;

    private Boolean isActive;

    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private RegistrationSource source;

    private Date dataNascimento;

    private String genero;

    public User(Long id, String email, String password, String name, UserRole role, byte[] img, String endereço,
            String celular,
            Boolean isActive, RegistrationSource source) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.img = img;
        this.endereço = endereço;
        this.celular = celular;
        this.isActive = isActive;
        this.source = source;
    }

    public User() {
    }

    public UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setEmail(email);
        userDto.setEndereço(endereço);
        userDto.setDataNascimento(dataNascimento);
        userDto.setGenero(genero);
        userDto.setSource(source);
        return userDto;
    }

}
