package com.andretavares.testesecurity.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class OrdemRequest implements Serializable {
    
    private BigDecimal envio;
    private String enderecoEnvio;
    private List<CarrinhoRequest> items;

}
