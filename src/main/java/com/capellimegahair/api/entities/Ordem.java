package com.capellimegahair.api.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import com.capellimegahair.api.enums.StatusOrdem;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class Ordem implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime data;
    @JoinColumn
    @ManyToOne
    private User user;
    private String enderecoEnvio;
    private BigDecimal quantia;
    private BigDecimal envio;
    private BigDecimal total;
    @Transient
    private String totalStr;
    @Enumerated(EnumType.STRING)
    private StatusOrdem statusOrdem;
    private LocalDateTime horaMensagem;
    @OneToMany(fetch = FetchType.EAGER)
    private List<OrdemItem> ordemItems;

    public String getFormattedTotal() {
        // Define o formato desejado
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale.Builder().setLanguage("pt").setRegion("BR").build());
        decimalFormat.applyPattern("#,##0.00");
        return decimalFormat.format(total);
    }

}
