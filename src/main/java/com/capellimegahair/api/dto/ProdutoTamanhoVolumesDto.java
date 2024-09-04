package com.capellimegahair.api.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProdutoTamanhoVolumesDto {
    private Long tamanhoId; // IDs dos tamanhos selecionados
    private List<VolumeDto> volumes; // Lista de volumes com seus preços
}
