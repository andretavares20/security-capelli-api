package com.andretavares.testesecurity.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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
public class OrdemLog implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn
    @ManyToOne
    private Ordem ordem;
    @JoinColumn
    @ManyToOne
    private User user;
    private Integer logType;
    private String logMessage;
    private LocalDateTime time;

}
