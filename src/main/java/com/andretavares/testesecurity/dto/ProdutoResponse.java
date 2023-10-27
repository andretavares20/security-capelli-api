package com.andretavares.testesecurity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoResponse {
    private Long id;
    private String perfumeTitle;
    private String perfumer;
    private Integer price;
    private Double perfumeRating;
    private String filename;
    private Integer reviewsCount;
    private String volume;
}
