package com.andretavares.testesecurity.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.TecnicaDto;
import com.andretavares.testesecurity.entities.Tecnica;
import com.andretavares.testesecurity.repositories.TecnicaRepository;

@Service
public class TecnicaService {
    @Autowired
    public TecnicaRepository tecnicaRepository;
    
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
}
