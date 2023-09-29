package com.andretavares.testesecurity.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.andretavares.testesecurity.dto.ProdutoDto;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.CategoriaRepository;
import com.andretavares.testesecurity.repositories.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    public Produto findById(Long id){
        return produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }

    public Produto create(ProdutoDto produtoDto){

        if(!StringUtils.hasText(produtoDto.getName())){
            throw new BadRequestException("Produto informado esta sem nome");
        }

        if(produtoDto.getCategoria()==null){
            throw new BadRequestException("Produto informado esta sem categoria");
        }

        if(produtoDto.getCategoria().getId()==null){
            throw new BadRequestException("Categoria informada esta sem id");
        }

        categoriaRepository.findById(produtoDto.getCategoria().getId())
            .orElseThrow(() -> new BadRequestException("Categoria ID "+produtoDto.getCategoria().getId()+" não existe"));

        Produto produto = new Produto(produtoDto.getName(),produtoDto.getDescription(),produtoDto.getPicture(),produtoDto.getCategoria()
            ,produtoDto.getPrice(),produtoDto.getEstoque());

        return produtoRepository.save(produto);
    }

    public Produto edit(Produto produto){

        if(produto.getId()==null){
            throw new BadRequestException("Id do produto não foi informado");
        }

        if(!StringUtils.hasText(produto.getName())){
            throw new BadRequestException("Produto informado esta sem nome");
        }

        if(produto.getCategoria()==null){
            throw new BadRequestException("Produto informado esta sem categoria");
        }

        if(produto.getCategoria().getId()==null){
            throw new BadRequestException("Categoria informada esta sem id");
        }

        categoriaRepository.findById(produto.getCategoria().getId())
            .orElseThrow(() -> new BadRequestException("Categoria ID "+produto.getCategoria().getId()+" não existe"));

        return produtoRepository.save(produto);
    }

    public Produto mudarImagem(Long id,String imagem){
        Produto produto = findById(id);
        produto.setPicture(imagem);
        return produtoRepository.save(produto);
    }

    public void deleteById(Long id){
        produtoRepository.deleteById(id);
    }

}
