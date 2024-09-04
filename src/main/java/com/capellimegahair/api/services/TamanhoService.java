package com.capellimegahair.api.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capellimegahair.api.dto.CategoriaDto;
import com.capellimegahair.api.dto.TamanhoDto;
import com.capellimegahair.api.dto.TecnicaDto;
import com.capellimegahair.api.entities.Categoria;
import com.capellimegahair.api.entities.Tamanho;
import com.capellimegahair.api.entities.Tecnica;
import com.capellimegahair.api.exceptions.BadRequestException;
import com.capellimegahair.api.exceptions.NotFoundException;
import com.capellimegahair.api.repositories.TamanhoRepository;

@Service
public class TamanhoService {

    @Autowired
    public TamanhoRepository tamanhoRepository;
    @Autowired
    public ModelMapper modelMapper;
    
    public Tamanho create(TamanhoDto tamanhoDto) {
        // Verifica se o nome da técnica já existe
        if (tamanhoRepository.findByCm(tamanhoDto.getCm()) != null) {
            throw new BadRequestException("Já existe um Tamanho com esse nome.");
        }

        // Mapeia o objeto TecnicaDto para Tecnica
        Tamanho tamanho = modelMapper.map(tamanhoDto, Tamanho.class);

        // Salva a técnica e a retorna
        return tamanhoRepository.save(tamanho);
    }

    public List<TamanhoDto> findAll() {
        List<Tamanho> tamanhos = tamanhoRepository.findAll();
        return tamanhos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TamanhoDto convertToDto(Tamanho tamanho) {
        return modelMapper.map(tamanho, TamanhoDto.class);
    }

    public TamanhoDto edit(TamanhoDto tamanhoDto) {
        // Verifica se o ID da categoria foi informado
        Long tamanhoId = tamanhoDto.getId();
        if (tamanhoId == null) {
            throw new BadRequestException("O ID da tecnica não foi informado");
        }
    
        // Busca a categoria existente pelo ID
        Tamanho tamanhoExistente = tamanhoRepository.findById(tamanhoId)
                .orElseThrow(() -> new NotFoundException("Tamanho não encontrada com o ID: " + tamanhoId));
    
        // Copia apenas as propriedades não nulas do DTO para a entidade Categoria
        BeanUtils.copyProperties(tamanhoDto, tamanhoExistente, getNullPropertyNames(tamanhoDto));
    
        // Salva a categoria atualizada
        tamanhoExistente = tamanhoRepository.save(tamanhoExistente);
    
        // Converte a categoria atualizada para CategoriaDto
        return modelMapper.map(tamanhoExistente, TamanhoDto.class);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
    
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public void deleteTamanho(Long id) {
        tamanhoRepository.deleteById(id);
    }

}
