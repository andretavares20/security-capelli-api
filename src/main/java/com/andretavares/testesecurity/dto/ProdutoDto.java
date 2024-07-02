package com.andretavares.testesecurity.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProdutoDto {

    private String name;
    private String description;
    private Long categoriaId;
    private List<ProdutoTamanhoVolumesDto> produtoTamanhoVolumesDto;
}
