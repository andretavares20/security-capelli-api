package com.andretavares.testesecurity.dto;

import lombok.Data;

@Data
public class CarrinhoRequest {
    
    private String produtoId;
    private Double quantidade;

}
