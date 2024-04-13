package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.AvaliacaoDTO;
import com.andretavares.testesecurity.entities.Avaliacao;
import com.andretavares.testesecurity.services.AvaliacaoService;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<Avaliacao> createAvaliacao(@RequestBody AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = avaliacaoService.createAvaliacao(avaliacaoDTO);
        if (avaliacao != null) {
            return new ResponseEntity<>(avaliacao, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // ou outro status adequado
        }
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoDTO>> getAllAvaliacoes() {
        List<AvaliacaoDTO> avaliacoes = avaliacaoService.getAllAvaliacoes();
        return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
    }

    // Endpoint para obter uma avaliação por ID
    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> getAvaliacaoById(@PathVariable("id") Long id) {
        Avaliacao avaliacao = avaliacaoService.getAvaliacaoById(id);
        if (avaliacao != null) {
            return new ResponseEntity<>(avaliacao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> updateAvaliacao(@PathVariable("id") Long id, @RequestBody AvaliacaoDTO avaliacaoDTO) {
        Avaliacao updatedAvaliacao = avaliacaoService.updateAvaliacao(id, avaliacaoDTO);
        if (updatedAvaliacao != null) {
            return new ResponseEntity<>(updatedAvaliacao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para excluir uma avaliação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable("id") Long id) {
        boolean deleted = avaliacaoService.deleteAvaliacao(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
