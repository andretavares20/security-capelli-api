package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.VolumeDto;
import com.andretavares.testesecurity.services.VolumeService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/volume")
@RequiredArgsConstructor
public class VolumeController {
    
    @Autowired
    private VolumeService volumeService;

    @Operation(summary = "Retorna todos os volumes existentes no banco de dados")
    @GetMapping()
    public ResponseEntity<List<VolumeDto>> findAllVolumes() {
        return ResponseEntity.ok().body(volumeService.findAll());
    }

}
