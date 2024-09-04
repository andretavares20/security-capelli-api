package com.capellimegahair.api.dto;

import com.capellimegahair.api.entities.Produto;

import lombok.Data;

@Data
public class ProdutoQuantidadeDto {
    
    private Produto produto;
    private Integer quantidade;

}
