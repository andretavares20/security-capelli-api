package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.VolumeDto;
import com.andretavares.testesecurity.services.VolumeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/volume")
@RequiredArgsConstructor
public class VolumeController {
    
    @Autowired
    private VolumeService volumeService;

    @PostMapping()
    public ResponseEntity<List<VolumeDto>> listVolume() {

        return ResponseEntity.ok().body(volumeService.getListVolume());

    }

}
