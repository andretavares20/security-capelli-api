package com.andretavares.testesecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolumeDto {

    private String gramas;

    private boolean situacao;
}
