package com.andretavares.testesecurity.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.CarrinhoRequest;
import com.andretavares.testesecurity.entities.Carrinho;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.services.CarrinhoService;
import com.mercadopago.net.HttpStatus;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Operation(summary = "Retorna carrinho de usuário", description = "Envie para esse endpoint o id do usuario")
    @GetMapping("/carrinhos/{idUser}")
    public List<Carrinho> findByUserId(@PathVariable Long idUser, Principal userLogged) {

        return carrinhoService.findByUserId(idUser, userLogged);

    }

    @Operation(summary = "Calcula valor total do carrinho do usuário", description = "Envie para esse endpoint o id do usuario")
    @GetMapping("/carrinhos/calcula-valor-total/{idUser}")
    public ResponseEntity<Double> calculaValorTotal(@PathVariable Long idUser) {

        double valorTotal = carrinhoService.calculaValorTotal(idUser);

        return ResponseEntity.ok().body(valorTotal);

    }

    @Operation(summary = "Adiciona uma quantidade de um produto em um carrinho", description = "Envie para esse endpoint o id do usuario, e o corpo do carrinho contendo o id do produto e a quantidade")
    @PostMapping("/carrinhos/{idUser}")
    public ResponseEntity<Integer> create(@PathVariable Long idUser,
            @RequestBody CarrinhoRequest carrinhoRequest, Principal userLogged) {
        try {

            carrinhoService.addCarrinho(idUser, carrinhoRequest.getProdutoId(),
                    carrinhoRequest.getQuantidade(), carrinhoRequest.getTamanhoId(), carrinhoRequest.getTecnicaId(),
                    carrinhoRequest.getVolumeId(),userLogged);
            return ResponseEntity.ok().body(HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            throw new BadRequestException("Não foi possivel inserir produto no carro: " + e.toString() + "");
        }

    }

    @Operation(summary = "Atualiza a quantidade de um produto em um carrinho", description = "Envie para esse endpoint o id do usuario, o id do produto e a quantidade que deseja.")
    @PutMapping("/carrinhos/{produtoId}/{idUser}")
    public Carrinho update(@PathVariable Long idUser,
            @PathVariable("produtoId") String produtoId,
            @RequestParam("quantidade") Long quantidade,
            Principal userLogged) {

        return carrinhoService.updateQuantidade(idUser, Long.parseLong(produtoId), quantidade, userLogged);

    }

    @Operation(summary = "Remove produto do carrinho", description = "Envie para esse endpoint o id do usuario, e o id do produto que deseja deletar.")
    @DeleteMapping("/carrinhos/{produtoId}/{idUser}")
    public void delete(@PathVariable Long idUser, @PathVariable("produtoId") String produtoId, Principal userLogged) {
        carrinhoService.delete(idUser, Long.parseLong(produtoId), userLogged);
    }

    @Operation(summary = "Remove todos os produtos de um carrinho", description = "Envie para esse endpoint o id do usuario.")
    @DeleteMapping("/carrinhos/{idUser}")
    public void delete(@PathVariable Long idUser,Principal userLogged) {
        carrinhoService.deleteAll(idUser,userLogged);
    }

}
