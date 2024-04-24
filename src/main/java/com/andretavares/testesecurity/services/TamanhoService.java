package com.andretavares.testesecurity.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.TamanhoDto;
import com.andretavares.testesecurity.entities.Tamanho;
import com.andretavares.testesecurity.repositories.TamanhoRepository;

@Service
public class TamanhoService {

    @Autowired
    public TamanhoRepository tamanhoRepository;
    @Autowired
    public ModelMapper modelMapper;
    
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

    public Tamanho updateTamanho(Long id, TamanhoDto tamanhoDto) {
        Optional<Tamanho> optionalTamanho = tamanhoRepository.findById(id);
        if (optionalTamanho.isEmpty()) {
            // Lançar exceção de recurso não encontrado se o tamanho não existir
        }

        Tamanho tamanho = optionalTamanho.get();

        // Mapear os campos não nulos do DTO para a entidade Tamanho
        modelMapper.map(tamanhoDto, tamanho);

        // Salvar e retornar o tamanho atualizado
        return tamanhoRepository.save(tamanho);
    }

    public void deleteTamanho(Long id) {
        tamanhoRepository.deleteById(id);
    }

}
