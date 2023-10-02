package com.andretavares.testesecurity.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.andretavares.testesecurity.dto.CarrinhoRequest;
import com.andretavares.testesecurity.dto.OrdemRequest;
import com.andretavares.testesecurity.dto.OrdemResponse;
import com.andretavares.testesecurity.entities.Ordem;
import com.andretavares.testesecurity.entities.OrdemItem;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.enums.StatusOrdem;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.OrdemItemRepository;
import com.andretavares.testesecurity.repositories.OrdemRepository;
import com.andretavares.testesecurity.repositories.ProdutoRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
public class OrdemService {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private OrdemRepository ordemRepository;

    @Autowired
    private OrdemItemRepository ordemItemRepository;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private OrdemLogService ordemLogService;

    @Transactional
    public OrdemResponse create(Long idUser,OrdemRequest request){
        Ordem ordem = new Ordem();
        ordem.setData(LocalDateTime.now());
        ordem.setNumber(generateNumeroOrdem());
        ordem.setUser(new User(idUser));
        ordem.setEnderecoEnvio(request.getEnderecoEnvio());
        ordem.setStatusOrdem(StatusOrdem.DRAFT);
        ordem.setHoraMensagem(LocalDateTime.now());

        List<OrdemItem> items= new ArrayList<>();
        for(CarrinhoRequest k : request.getItems()){
            Produto produto = produtoRepository.findById(k.getProdutoId())
                .orElseThrow(() -> new BadRequestException("Produto Id "+k.getProdutoId()+ "não foi encontrado"));

            if(produto.getEstoque()<k.getQuantidade()){
                throw new BadRequestException("Estoque insuficiente");
            }

            OrdemItem ordemItem = new OrdemItem();
            ordemItem.setProduto(produto);
            ordemItem.setDescription(produto.getName());
            ordemItem.setQuantidade(k.getQuantidade());
            ordemItem.setPreço(produto.getPrice());
            ordemItem.setQuantia(new BigDecimal(ordemItem.getPreço().doubleValue()*ordemItem.getQuantidade()));
            ordemItem.setOrdem(ordem);
            items.add(ordemItem);


        }

        BigDecimal quantia = BigDecimal.ZERO;
        for (OrdemItem ordemItem:items){
            quantia = quantia.add(ordemItem.getQuantia());
        }

        ordem.setQuantia(quantia);
        ordem.setEnvio(request.getEnvio());
        ordem.setTotal(ordem.getQuantia().add(ordem.getEnvio()));

        Ordem saved = ordemRepository.save(ordem);
        for (OrdemItem ordemItem:items){
            ordemItemRepository.save(ordemItem);
            Produto produto = ordemItem.getProduto();
            produto.setEstoque(produto.getEstoque() - ordemItem.getQuantidade());
            produtoRepository.save(produto);
            carrinhoService.delete(idUser, produto.getId());
        }

        ordemLogService.createLog(idUser, ordem, 0, "Pedido feito com sucesso");

        OrdemResponse ordemResponse = new OrdemResponse(saved, items);
        return ordemResponse;
        
    }

    @Transactional
    public Ordem calcelOrdem(Long ordemId,Long userId){
        Ordem ordem = ordemRepository.findById(ordemId)
            .orElseThrow(() -> new ResourceNotFoundException("Ordem Id: "+ordemId+ "não encontrado"));
        if(!userId.equals(ordem.getUser().getId())){
            throw new BadRequestException("Este pedido só pode ser cancelado pela própria pessoa");
        }
        if(!StatusOrdem.DRAFT.equals(ordem.getStatusOrdem())){
            throw new BadRequestException("Este pedido nao pode ser cancelado pois ja foi processado");
        }

        ordem.setStatusOrdem(StatusOrdem.CANCELED);

        return null;
        
    }

    private String generateNumeroOrdem(){
        return String.format("%016d", System.nanoTime());
    }



}
