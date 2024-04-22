package com.andretavares.testesecurity.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.VolumeDto;
import com.andretavares.testesecurity.entities.Volume;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.VolumeRepository;

@Service
public class VolumeService {
    @Autowired
    public VolumeRepository volumeRepository;
    
    public Volume postVolume(String gramas){
        Volume volume =  new Volume(gramas);
        return volumeRepository.save(volume);

    }

    public List<VolumeDto> getListVolume(){

        List<Volume> listVolume = volumeRepository.findAll();
        List<VolumeDto> listVolumeDto = new ArrayList<>();
        for(Volume volume:listVolume){
            VolumeDto volumeDto = new VolumeDto();
            volumeDto.setId(volume.getId());
            volumeDto.setGramas(volume.getGramas());
            listVolumeDto.add(volumeDto);
        }
        return listVolumeDto;
    }

    public Volume updateVolume(Long id, String gramas) {
        Volume volume = volumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volume não encontrado com o ID: " + id));
        volume.setGramas(gramas);
        return volumeRepository.save(volume);
    }

    public void deleteVolume(Long id) {
        Volume volume = volumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volume não encontrado com o ID: " + id));
        volumeRepository.delete(volume);
    }
}
