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
import com.capellimegahair.api.dto.TecnicaDto;
import com.capellimegahair.api.entities.Categoria;
import com.capellimegahair.api.entities.Tecnica;
import com.capellimegahair.api.exceptions.BadRequestException;
import com.capellimegahair.api.exceptions.NotFoundException;
import com.capellimegahair.api.repositories.TecnicaRepository;

@Service
public class TecnicaService {
    @Autowired
    public TecnicaRepository tecnicaRepository;
    @Autowired
    public ModelMapper modelMapper;
    
    public Tecnica create(TecnicaDto tecnicaDto) {
        // Verifica se o nome da técnica já existe
        if (tecnicaRepository.findByNome(tecnicaDto.getNome()) != null) {
            throw new BadRequestException("Já existe uma técnica com esse nome.");
        }

        // Mapeia o objeto TecnicaDto para Tecnica
        Tecnica tecnica = modelMapper.map(tecnicaDto, Tecnica.class);

        // Salva a técnica e a retorna
        return tecnicaRepository.save(tecnica);
    }

    public List<TecnicaDto> findAll() {
        List<Tecnica> tecnicas = tecnicaRepository.findAll();
        return tecnicas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TecnicaDto convertToDto(Tecnica tecnica) {
        return modelMapper.map(tecnica, TecnicaDto.class);
    }

    public TecnicaDto edit(TecnicaDto tecnicaDto) {
        // Verifica se o ID da categoria foi informado
        Long tecnicaId = tecnicaDto.getId();
        if (tecnicaId == null) {
            throw new BadRequestException("O ID da tecnica não foi informado");
        }
    
        // Busca a categoria existente pelo ID
        Tecnica tecnicaExistente = tecnicaRepository.findById(tecnicaId)
                .orElseThrow(() -> new NotFoundException("Tecnica não encontrada com o ID: " + tecnicaId));
    
        // Copia apenas as propriedades não nulas do DTO para a entidade Categoria
        BeanUtils.copyProperties(tecnicaDto, tecnicaExistente, getNullPropertyNames(tecnicaDto));
    
        // Salva a categoria atualizada
        tecnicaExistente = tecnicaRepository.save(tecnicaExistente);
    
        // Converte a categoria atualizada para CategoriaDto
        return modelMapper.map(tecnicaExistente, TecnicaDto.class);
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

    public void deleteById(Long id) {
        tecnicaRepository.deleteById(id);
    }

    public TecnicaDto findById(Long id) {
        Tecnica tecnica = tecnicaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Técnica não encontrada com o ID: " + id));
        return modelMapper.map(tecnica, TecnicaDto.class);
    }
}
