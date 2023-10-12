package com.andretavares.testesecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.andretavares.testesecurity.dto.ProdutoDto;
import com.andretavares.testesecurity.entities.Categoria;
import com.andretavares.testesecurity.entities.Cor;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.CategoriaRepository;
import com.andretavares.testesecurity.repositories.CorRepository;
import com.andretavares.testesecurity.repositories.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CorRepository corRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }

    public Produto create(ProdutoDto produtoDto) {

        if (!StringUtils.hasText(produtoDto.getName())) {
            throw new BadRequestException("Produto informado esta sem nome");
        }

        if (produtoDto.getCorId() == null) {
            throw new BadRequestException("Produto informado esta sem cor");
        }

        if (produtoDto.getCorId() == null) {
            throw new BadRequestException("Cor informada esta sem id");
        }

        Cor cor = corRepository.findById(produtoDto.getCorId())
                .orElseThrow(() -> new BadRequestException(
                        "Cor ID " + produtoDto.getCorId() + " não existe"));

        Categoria categoria = categoriaRepository.findById(cor.getCategoria().getId())
                .orElseThrow(() -> new BadRequestException(
                        "Categoria ID " + cor.getCategoria().getId() + " não existe"));


        Produto produto = new Produto(produtoDto.getName(), produtoDto.getDescription(), produtoDto.getPicture(),
                categoria, cor, produtoDto.getPrice(), produtoDto.getEstoque());

        return produtoRepository.save(produto);
    }

    public Produto edit(Produto produto) {

        Optional<Produto> produtoExistente = produtoRepository.findById(produto.getId());
        if(produtoExistente.isPresent()){
            BeanUtils.copyProperties(produto, produtoExistente.get(),"id");
            produtoRepository.save(produtoExistente.get());
            return produtoExistente.get();
        }else{
            System.out.println("Id não encontrado no banco de dados.");
        }

        return null;
    }

    public Produto mudarImagem(Long id, String imagem) {
        Produto produto = findById(id);
        produto.setPicture(imagem);
        return produtoRepository.save(produto);
    }

    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }

}
