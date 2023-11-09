package com.andretavares.testesecurity.dto;

import lombok.Data;

@Data
public class CarrinhoRequest {
    
    private Long produtoId;
    private Long quantidade;
    private String tamanho;
    private String volume;
    private String tecnica;

}
