package com.andretavares.testesecurity.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.andretavares.testesecurity.dto.CategoriaDto;
import com.andretavares.testesecurity.entities.Categoria;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.NotFoundException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoriaDto findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não existe"));
        
        return modelMapper.map(categoria, CategoriaDto.class);
    }

    public CategoriaDto findByNome(String nome) {
        Categoria categoria = categoriaRepository.findByNome(nome);
        if (categoria == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }
        
        return modelMapper.map(categoria, CategoriaDto.class);
    }

    public List<CategoriaDto> findAll() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CategoriaDto> findAllAtivas() {
        List<Categoria> categorias = categoriaRepository.findAllBySituacao(true);
        return categorias.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CategoriaDto convertToDto(Categoria categoria) {
        return modelMapper.map(categoria, CategoriaDto.class);
    }

    public Categoria create(CategoriaDto categoriaDto) {

        Categoria categoria = new Categoria(categoriaDto.getNome(), categoriaDto.getUrlImagem(),
                categoriaDto.getDescricao(), categoriaDto.isSituacao());

        return categoriaRepository.save(categoria);
    }

    public CategoriaDto edit(CategoriaDto categoriaDto) {

        // Verifica se o ID da categoria foi informado
        Long categoriaId = categoriaDto.getId();
        if (categoriaId == null) {
            throw new BadRequestException("O ID da categoria não foi informado");
        }
    
        // Busca a categoria existente pelo ID
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(categoriaId);
        if (optionalCategoria.isEmpty()) {
            throw new NotFoundException("Categoria não encontrada com o ID: " + categoriaId);
        }
        
        // Obtém a categoria existente
        Categoria categoriaExistente = optionalCategoria.get();
    
        // Atualiza os campos da categoria existente com as informações do DTO
        categoriaExistente.setNome(categoriaDto.getNome());
        categoriaExistente.setUrlImagem(categoriaDto.getUrlImagem());
        categoriaExistente.setDescricao(categoriaDto.getDescricao());
        categoriaExistente.setSituacao(categoriaDto.isSituacao());
    
        categoriaExistente = categoriaRepository.save(categoriaExistente);

        // Converte a categoria atualizada para CategoriaDto
        return modelMapper.map(categoriaExistente, CategoriaDto.class);
    }

    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }

}
