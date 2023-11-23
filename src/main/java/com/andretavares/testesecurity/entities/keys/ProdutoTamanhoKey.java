package com.andretavares.testesecurity.entities.keys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProdutoTamanhoKey implements Serializable{
    
    @Column(name = "produto_id")
    Long produtoId;

    @Column(name = "tamanho_id")
    Long tamanhoId;

}
