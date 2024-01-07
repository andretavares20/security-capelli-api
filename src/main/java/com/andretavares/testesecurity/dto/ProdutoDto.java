package com.andretavares.testesecurity.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(value = { "categoria.name" })
public class ProdutoDto {
    
    private String name;
    private String description;
    private Long categoriaId;
    private BigDecimal price;
    private Long estoque;

}
