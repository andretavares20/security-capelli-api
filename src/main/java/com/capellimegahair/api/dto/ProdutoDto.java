package com.capellimegahair.api.dto;

import java.util.List;

import com.capellimegahair.api.entities.Categoria;

import lombok.Data;

@Data
public class ProdutoDto {

    private String name;
    private String description;
    private Categoria categoria;
    private List<ProdutoTamanhoVolumesDto> produtoTamanhoVolumesDto;
    private String sugestaoVolume;
    private String tecnicas;
    private String envioParaTodoBrasil;
}
