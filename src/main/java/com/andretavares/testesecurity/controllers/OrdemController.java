package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.OrdemRequest;
import com.andretavares.testesecurity.dto.OrdemResponse;
import com.andretavares.testesecurity.entities.Ordem;
import com.andretavares.testesecurity.services.OrdemService;

@RestController
@RequestMapping("/api")
public class OrdemController {

    @Autowired
    private OrdemService ordemService;

    @PostMapping("/ordens")
    public ResponseEntity<OrdemResponse> create(Long userId, @RequestBody OrdemRequest request) {

        return ResponseEntity.ok().body(ordemService.create(userId, request));

    }

    @PutMapping("/ordens/{ordemId}/cancel")
    public ResponseEntity<Ordem> cancelOrdemUser(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.cancelOrdem(ordemId, userId));

    }

    @PutMapping("/ordens/{ordemId}/receber")
    public ResponseEntity<Ordem> receber(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.receberPedidos(ordemId, userId));

    }

    @PutMapping("/ordens/{ordemId}/confirmacao")
    public ResponseEntity<Ordem> confirmacao(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.confirmarPagamento(ordemId, userId));

    }

    @PutMapping("/ordens/{ordemId}/embalar")
    public ResponseEntity<Ordem> embalar(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.embalar(ordemId, userId));

    }

    @PutMapping("/ordens/{ordemId}/enviar")
    public ResponseEntity<Ordem> enviar(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.enviar(ordemId, userId));

    }

    @GetMapping("/ordens")
    public ResponseEntity<List<Ordem>> findAllOrdemUser(Long userId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {

        return ResponseEntity.ok().body(ordemService.findAllOrdemUser(userId, page, limit));

    }

    @GetMapping("/ordens/admin")
    public ResponseEntity<List<Ordem>> search(Long userId,
            @RequestParam(name = "filterText", defaultValue = "", required = false) String filterText,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {

        return ResponseEntity.ok().body(ordemService.search(filterText, page, limit));

    }

}
