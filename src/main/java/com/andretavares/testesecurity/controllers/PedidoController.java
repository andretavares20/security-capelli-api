package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.entities.Pedido;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    
    @PostMapping
    public Pedido postPedido(@RequestBody Pedido pedido){

        return null;

    }

    @GetMapping
    public Pedido getPedido(@RequestParam("pedidoId") Long pedidoId){

        return null;

    }

    @PostMapping("/cancelar-pedido")
    public ResponseEntity<?> cancelaPedido(@RequestParam("pedidoId") Long pedidoId,
        @RequestParam("usuarioId") Long usuarioId){

            return null;

    }

    @GetMapping("/buscar-pedidos")
    public List<Pedido> buscaListPedido(@RequestParam("usuarioId") Long usuarioId){

        return null;

    }

}
