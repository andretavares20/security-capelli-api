package com.andretavares.testesecurity.entities.keys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProdutoVolumeKey implements Serializable{
    
    @Column(name = "produto_id")
    Long produtoId;

    @Column(name = "volume_id")
    Long volumeId;

}
