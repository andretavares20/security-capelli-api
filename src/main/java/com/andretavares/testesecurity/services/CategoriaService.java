package com.andretavares.testesecurity.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.CategoriaDto;
import com.andretavares.testesecurity.entities.Categoria;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria findById(Long id){
        return categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não existe"));
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria create(CategoriaDto categoriaDto){

        Categoria categoria = new Categoria(categoriaDto.getName());

        return categoriaRepository.save(categoria);
    }

    public Categoria edit(Categoria categoria){

        if(categoria.getId()==null){
            throw new BadRequestException("Id da categoria não foi informado");
        }

        return categoriaRepository.save(categoria);
    }

    public void deleteById(Long id){
        categoriaRepository.deleteById(id);
    }

    

}
