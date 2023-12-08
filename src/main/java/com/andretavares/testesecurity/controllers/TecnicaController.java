package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.TecnicaDto;
import com.andretavares.testesecurity.services.TecnicaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tecnica")
@RequiredArgsConstructor
public class TecnicaController {
    
    @Autowired
    private TecnicaService tecnicaService;

    @GetMapping()
    public ResponseEntity<List<TecnicaDto>> listTamanho() {

        return ResponseEntity.ok().body(tecnicaService.getListTecnica());

    }

}
