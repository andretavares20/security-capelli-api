package com.andretavares.testesecurity.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.andretavares.testesecurity.dto.VolumeDto;
import com.andretavares.testesecurity.entities.Volume;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.VolumeRepository;

import jakarta.transaction.Transactional;

@Service
public class VolumeService {
    @Autowired
    public VolumeRepository volumeRepository;

    public Volume postVolume(String gramas) {
        Volume volume = new Volume(gramas);
        return volumeRepository.save(volume);

    }

    public List<Volume> getListVolume() {

        List<Volume> listVolume = volumeRepository.findAll();
        return listVolume;
    }

    public Volume getVolume(Long idVolume){

        Volume volume = volumeRepository.findById(idVolume).orElseThrow();
        return volume;
    }

    @Transactional
    public Volume updateVolume(Long id, VolumeDto volumeDto) {
        Volume volume = volumeRepository.findById(id).orElse(null);
        if (volume == null) {
            // Aqui você pode lidar com o caso em que o volume não é encontrado
            return null;
        }
        BeanUtils.copyProperties(volumeDto, volume);
        volume.setId(id);
        return volumeRepository.save(volume);
    }

    public void deleteVolume(Long id) {
        Volume volume = volumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volume não encontrado com o ID: " + id));
        volumeRepository.delete(volume);
    }
}
