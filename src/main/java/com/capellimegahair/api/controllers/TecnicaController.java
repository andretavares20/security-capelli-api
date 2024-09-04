package com.capellimegahair.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capellimegahair.api.dto.TecnicaDto;
import com.capellimegahair.api.services.TecnicaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tecnica")
@RequiredArgsConstructor
public class TecnicaController {
    
    @Autowired
    private TecnicaService tecnicaService;

    @GetMapping("/all")
    public ResponseEntity<List<TecnicaDto>> findAllTecnicas() {
        List<TecnicaDto> tecnicas = tecnicaService.findAll();
        return ResponseEntity.ok(tecnicas);
    }

}
