package com.andretavares.testesecurity.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.TamanhoDto;
import com.andretavares.testesecurity.entities.Tamanho;
import com.andretavares.testesecurity.repositories.TamanhoRepository;

@Service
public class TamanhoService {

    @Autowired
    public TamanhoRepository tamanhoRepository;
    
    public Tamanho postTamanho(String cm){
        Tamanho tamanho =  new Tamanho(cm);
        return tamanhoRepository.save(tamanho);

    }

    public List<TamanhoDto> getListTamanho(){
        List<Tamanho> listTamanho = tamanhoRepository.findAll();
        List<TamanhoDto> listTamanhoDto = new ArrayList<>();
        for(Tamanho tamanho:listTamanho){
            TamanhoDto tamanhoDto = new TamanhoDto();
            tamanhoDto.setId(tamanho.getId());
            tamanhoDto.setCm(tamanho.getCm());
            listTamanhoDto.add(tamanhoDto);
        }
        return listTamanhoDto;
    }

}
