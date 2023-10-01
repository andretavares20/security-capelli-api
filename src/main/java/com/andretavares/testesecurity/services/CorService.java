package com.andretavares.testesecurity.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.CorDto;
import com.andretavares.testesecurity.entities.Categoria;
import com.andretavares.testesecurity.entities.Cor;
import com.andretavares.testesecurity.repositories.CategoriaRepository;
import com.andretavares.testesecurity.repositories.CorRepository;

@Service
public class CorService {

    @Autowired
    private CorRepository corRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Cor postCor(CorDto corDto){

        Optional<Categoria> optionalCategoria = categoriaRepository.findById(corDto.getCategoriaId());

        if(optionalCategoria.isPresent()){

            Cor cor = new Cor(corDto.getNome(),optionalCategoria.get());

            corRepository.save(cor);

            return cor;

        }

        return null;

       
    }
    
}
