package com.capellimegahair.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdemItemResponse {
    private Long id;
    private Long amount;
    private Long quantity;
    private ProdutoResponse perfume;
}
