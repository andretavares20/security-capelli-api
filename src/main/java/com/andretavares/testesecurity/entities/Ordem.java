package com.andretavares.testesecurity.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.andretavares.testesecurity.enums.StatusOrdem;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Ordem implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn
    @ManyToOne
    private User user;
    private String enderecoEnvio;
    private BigDecimal quantidade;
    private BigDecimal envio;
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    private StatusOrdem statusOrdem;
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaMensagem;
    

}
