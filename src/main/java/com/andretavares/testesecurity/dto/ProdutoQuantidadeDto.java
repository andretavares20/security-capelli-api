package com.andretavares.testesecurity.dto;

import com.andretavares.testesecurity.entities.Produto;

import lombok.Data;

@Data
public class ProdutoQuantidadeDto {
    
    private Produto produto;
    private Integer quantidade;

}
