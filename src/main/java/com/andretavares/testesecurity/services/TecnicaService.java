package com.andretavares.testesecurity.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.TecnicaDto;
import com.andretavares.testesecurity.entities.Tecnica;
import com.andretavares.testesecurity.repositories.TecnicaRepository;

@Service
public class TecnicaService {
    @Autowired
    public TecnicaRepository tecnicaRepository;
    @Autowired
    public ModelMapper modelMapper;
    
    public Tecnica postTecnica(String nome){
        Tecnica tecnica =  new Tecnica(nome);
        return tecnicaRepository.save(tecnica);

    }

    public List<TecnicaDto> getListTecnica(){

        List<Tecnica> listTecnica = tecnicaRepository.findAll();
        List<TecnicaDto> listTecnicaDto = new ArrayList<>();
        for(Tecnica tecnica:listTecnica){
            TecnicaDto tecnicaDto = new TecnicaDto();
            tecnicaDto.setId(tecnica.getId());
            tecnicaDto.setNome(tecnica.getNome());
            listTecnicaDto.add(tecnicaDto);
        }
        return listTecnicaDto;
    }

    public Tecnica updateTecnica(Long id, TecnicaDto tecnicaDto) {
        Optional<Tecnica> optionalTecnica = tecnicaRepository.findById(id);
        if (optionalTecnica.isEmpty()) {
            // Lançar exceção de recurso não encontrado se a técnica não existir
        }

        Tecnica tecnica = optionalTecnica.get();
        
        // Mapear os campos não nulos do DTO para a entidade Tecnica
        modelMapper.map(tecnicaDto, tecnica);

        // Salvar e retornar a técnica atualizada
        return tecnicaRepository.save(tecnica);
    }

    public void deleteTecnica(Long id) {
        tecnicaRepository.deleteById(id);
    }
}
