package com.andretavares.testesecurity.controllers;

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
    public List<Carrinho> findByUserId(@PathVariable Long idUser) {

        return carrinhoService.findByUserId(idUser);

    }

    @Operation(summary = "Adiciona uma quantidade de um produto em um carrinho", description = "Envie para esse endpoint o id do usuario, e o corpo do carrinho contendo o id do produto e a quantidade")
    @PostMapping("/carrinhos/{idUser}")
    public ResponseEntity<Integer> create(@PathVariable Long idUser,
            @RequestBody CarrinhoRequest carrinhoRequest) {
                try {
                    
                    carrinhoService.addCarrinho(idUser, carrinhoRequest.getProdutoId(),
                    carrinhoRequest.getQuantidade(), carrinhoRequest.getTamanhoId(), carrinhoRequest.getTecnicaId(),
                    carrinhoRequest.getVolumeId());
                    return ResponseEntity.ok().body(HttpStatus.CREATED); 
                } catch (Exception e) {
                    // TODO: handle exception
                    throw new BadRequestException("Não foi possivel inserir produto no carro: "+e.toString()+"");
                }

    }

    @Operation(summary = "Atualiza a quantidade de um produto em um carrinho", description = "Envie para esse endpoint o id do usuario, o id do produto e a quantidade que deseja.")
    @PutMapping("/carrinhos/{produtoId}/{idUser}")
    public Carrinho update(@PathVariable Long idUser,
            @PathVariable("produtoId") String produtoId,
            @RequestParam("quantidade") Long quantidade) {

        return carrinhoService.updateQuantidade(idUser, Long.parseLong(produtoId), quantidade);

    }

    @Operation(summary = "Remove produto do carrinho", description = "Envie para esse endpoint o id do usuario, e o id do produto que deseja deletar.")
    @DeleteMapping("/carrinhos/{produtoId}/{idUser}")
    public void delete(@PathVariable Long idUser, @PathVariable("produtoId") String produtoId) {
        carrinhoService.delete(idUser, Long.parseLong(produtoId));
    }

}
