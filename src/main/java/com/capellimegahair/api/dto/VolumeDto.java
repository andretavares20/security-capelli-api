package com.capellimegahair.api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolumeDto {

    private Long id;
    private String gramas;
    private Boolean situacao;
    private BigDecimal price; // Adicionando o preço do volume
}
