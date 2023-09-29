package com.andretavares.testesecurity.dto;

import java.math.BigDecimal;

import com.andretavares.testesecurity.entities.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(value = { "categoria.name" })
public class ProdutoDto {
    
    private String name;
    private String description;
    private String picture;
    private Categoria categoria;
    private BigDecimal price;
    private Long estoque;

}
