package com.andretavares.testesecurity.dto;

import lombok.Data;

@Data
public class AvaliacaoDTO {
    private Long id;
    private Long idProduto;
    private Long idUsuario;
    private int rating;
    private String descricao;
    private String titulo;

}