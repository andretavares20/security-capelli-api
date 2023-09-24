package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.entities.PedidoModel;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    
    @PostMapping
    public PedidoModel postPedido(@RequestBody PedidoModel pedido){

        return null;

    }

    @GetMapping
    public PedidoModel getPedido(@RequestParam("pedidoId") Long pedidoId){

        return null;

    }

    @PostMapping("/cancelar-pedido")
    public ResponseEntity<?> cancelaPedido(@RequestParam("pedidoId") Long pedidoId,
        @RequestParam("usuarioId") Long usuarioId){

            return null;

    }

    @GetMapping("/buscar-pedidos")
    public List<PedidoModel> buscaListPedido(@RequestParam("usuarioId") Long usuarioId){

        return null;

    }

}
