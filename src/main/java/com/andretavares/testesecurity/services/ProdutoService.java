package com.andretavares.testesecurity.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.ProdutoQuantidadeDto;
import com.andretavares.testesecurity.entities.Estoque;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.repositories.EstoqueRepository;
import com.andretavares.testesecurity.repositories.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    EstoqueRepository estoqueRepository;
    
    public Estoque postProduto(Produto produto){
        
        Produto produtoDb = produtoRepository.save(produto);

        Estoque estoque = estoqueRepository.findById(produtoDb.getId()).get();

        if(estoque!=null){
            Integer quantidade = estoque.getQuantidade();
            quantidade += 1;
            
        }

        Estoque estoqueDb = estoqueRepository.save(estoque);

        return estoqueDb;

    }

    public List<Estoque> postListProduto(List<ProdutoQuantidadeDto> listProdutoQuantidadeDtos){

        List<Estoque> listEstoqueResponse = new ArrayList<Estoque>();
        
        for(ProdutoQuantidadeDto produtoQuantidadeDto:listProdutoQuantidadeDtos){
            
            Produto produtoDb = produtoRepository.save(produtoQuantidadeDto.getProduto());

            Estoque estoque = new Estoque(produtoDb,produtoQuantidadeDto.getQuantidade());

            Estoque estoqueDb = estoqueRepository.save(estoque);

            listEstoqueResponse.add(estoqueDb);

        }

        return listEstoqueResponse;

    }

}
