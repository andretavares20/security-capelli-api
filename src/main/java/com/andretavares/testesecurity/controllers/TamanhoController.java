package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.TamanhoDto;
import com.andretavares.testesecurity.entities.Tamanho;
import com.andretavares.testesecurity.services.TamanhoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tamanho")
@RequiredArgsConstructor
public class TamanhoController {

    @Autowired
    private TamanhoService tamanhoService;

    @PostMapping()
    public ResponseEntity<List<TamanhoDto>> listTamanho() {

        return ResponseEntity.ok().body(tamanhoService.getListTamanho());

    }
    
}
