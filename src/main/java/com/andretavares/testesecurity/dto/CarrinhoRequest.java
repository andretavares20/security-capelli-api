package com.andretavares.testesecurity.dto;

import lombok.Data;

@Data
public class CarrinhoRequest {
    
    private Long produtoId;
    private Long quantidade;
    private Long tamanhoId;
    private Long volumeId;
    private Long tecnicaId;

}
