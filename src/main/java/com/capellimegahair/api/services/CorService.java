package com.capellimegahair.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capellimegahair.api.dto.CorDto;
import com.capellimegahair.api.entities.Categoria;
import com.capellimegahair.api.entities.Cor;
import com.capellimegahair.api.repositories.CategoriaRepository;
import com.capellimegahair.api.repositories.CorRepository;

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
