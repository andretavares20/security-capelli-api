package com.andretavares.testesecurity.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.andretavares.testesecurity.dto.UserDto;
import com.andretavares.testesecurity.enums.UserRole;
import com.andretavares.testesecurity.source.RegistrationSource;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
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

    @JsonIgnore
    private String celular;
    @JsonIgnore
    private Boolean isActive;

    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private RegistrationSource source;

    private Date dataNascimento;

    private String genero;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    public User(Long id, String email, String password, String name, UserRole role,
            String celular,
            Boolean isActive, RegistrationSource source, Date dataNascimento, String genero) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.celular = celular;
        this.isActive = isActive;
        this.source = source;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    public User() {
    }

    public User(Long username) {
        this.id = username;
    }

    @JsonIgnore
    public UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setEmail(email);
        userDto.setDataNascimento(dataNascimento);
        userDto.setGenero(genero);
        userDto.setSource(source);
        userDto.setRole(role);
        userDto.setCelular(celular);
        userDto.setIsActive(isActive);
        userDto.setSource(source);
        userDto.setEnderecos(enderecos);
        return userDto;
    }

}
