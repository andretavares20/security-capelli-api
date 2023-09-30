package com.andretavares.testesecurity.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.services.CarrinhoService;
import com.andretavares.testesecurity.services.impl.UserDetailsImpl;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping("/adicionar-produto")
    public Produto postProdutoCarrinho(@RequestBody Produto produto, @RequestParam("userId") Long userId) {

        return null;

    }

    @DeleteMapping("/remover-produo")
    public Produto deleteProdutoCarrinho(@RequestParam("produtoId") Long produtoId,
            @RequestParam("userId") Long userId) {

        return null;

    }

    @GetMapping("/calcular-frete")
    public Double calculaFrete(@RequestParam("userId") Long userId, @RequestParam("cep") String cep) {

        return null;

    }

    @PostMapping("/alterar-quantidade")
    public ResponseEntity<?> alteraQuantidadeProdutoCarrinho(@RequestParam("userId") Long userId,
            @RequestParam("produtoId") Long produtoId, @RequestParam("quantidade") Long id) {

        return null;

    }

    @PostMapping("/finalizar-compra")
    public ResponseEntity<?> finalizaCompra(@RequestParam("userId") Long userId) {

        return null;

    }

    @GetMapping("/carrinhos")
    public List<Carrinho> findByUserId(Principal user) {
        System.out.println(user.getName());
        System.out.println(user.getClass());

        return carrinhoService.findByUserId(user.getName());

    }

    @PostMapping("/carrinhos")
    public Carrinho create(@AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody CarrinhoRequest carrinhoRequest) {
        return carrinhoService.addCarrinho(Long.parseLong(user.getUsername()),Long.parseLong(carrinhoRequest.getProdutoId()) ,
                carrinhoRequest.getQuantidade());
    }

    @PutMapping("/carrinhos/{produtoId}")
    public Carrinho update(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @PathVariable("produtoId") String produtoId,
            @RequestParam("quantidade") Double quantidade) {

                return carrinhoService.updateQuantidade(Long.parseLong(userDetailsImpl.getUsername()),Long.parseLong(produtoId) , quantidade);

    }

    @DeleteMapping("/carrinhos/{produtoId}")
    public void delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,@PathVariable("produtoId") String produtoId){
        carrinhoService.delete(Long.parseLong(userDetailsImpl.getUsername()), Long.parseLong(produtoId));
    }

}
