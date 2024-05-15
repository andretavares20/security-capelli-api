package com.andretavares.testesecurity.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.VolumeDto;
import com.andretavares.testesecurity.entities.Volume;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.NotFoundException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.VolumeRepository;

@Service
public class VolumeService {
    @Autowired
    public VolumeRepository volumeRepository;

    public Volume create(VolumeDto volumeDto) {
        Volume volume = new Volume(volumeDto.getGramas());
        return volumeRepository.save(volume);
    }

    public List<VolumeDto> findAll() {
        List<Volume> volumes = volumeRepository.findAll();
        return volumes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public VolumeDto convertToDto(Volume volume) {
        VolumeDto volumeDto = new VolumeDto();
        volumeDto.setId(volume.getId());
        volumeDto.setGramas(volume.getGramas());
        volumeDto.setSituacao(volume.getSituacao());
        return volumeDto;
    }

    public Volume getVolume(Long idVolume) {

        Volume volume = volumeRepository.findById(idVolume).orElseThrow();
        return volume;
    }

    public VolumeDto edit(VolumeDto volumeDto) {
        // Verifica se o ID do volume foi informado
        Long volumeId = volumeDto.getId();
        if (volumeId == null) {
            throw new BadRequestException("O ID do volume não foi informado");
        }

        // Busca o volume existente pelo ID
        Volume volumeExistente = volumeRepository.findById(volumeId)
                .orElseThrow(() -> new NotFoundException("Volume não encontrado com o ID: " + volumeId));

        // Copia apenas as propriedades não nulas do DTO para a entidade Volume
        BeanUtils.copyProperties(volumeDto, volumeExistente, getNullPropertyNames(volumeDto));

        // Salva o volume atualizado
        volumeExistente = volumeRepository.save(volumeExistente);

        // Converte o volume atualizado para VolumeDto
        return convertToDto(volumeExistente);
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

    public void deleteVolume(Long id) {
        Volume volume = volumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Volume não encontrado com o ID: " + id));
        volumeRepository.delete(volume);
    }
}
