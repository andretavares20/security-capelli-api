package com.andretavares.testesecurity.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.andretavares.testesecurity.dto.CategoriaDto;
import com.andretavares.testesecurity.dto.UploadFileResponse;
import com.andretavares.testesecurity.entities.Arquivo;
import com.andretavares.testesecurity.entities.Categoria;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.NotFoundException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired 
    private FileService fileService;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${s3.bucket-name.arquivos}")
    private String S3_BUCKET_NAME_ARQUIVOS;

    public CategoriaDto findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não existe"));
        
        return modelMapper.map(categoria, CategoriaDto.class);
    }

    public CategoriaDto findByNome(String nome) {
        Categoria categoria = categoriaRepository.findByNome(nome);
        if (categoria == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }
        
        return modelMapper.map(categoria, CategoriaDto.class);
    }

    public List<CategoriaDto> findAll() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CategoriaDto> findAllAtivas() {
        List<Categoria> categorias = categoriaRepository.findAllBySituacao(true);
        return categorias.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CategoriaDto convertToDto(Categoria categoria) {
        return modelMapper.map(categoria, CategoriaDto.class);
    }

    public Categoria create(CategoriaDto categoriaDto) {

        Categoria categoria = new Categoria(categoriaDto.getNome(), categoriaDto.getUrlImagem(),
                categoriaDto.getDescricao(), categoriaDto.isSituacao());

        return categoriaRepository.save(categoria);
    }

    public CategoriaDto edit(CategoriaDto categoriaDto) {
        // Verifica se o ID da categoria foi informado
        Long categoriaId = categoriaDto.getId();
        if (categoriaId == null) {
            throw new BadRequestException("O ID da categoria não foi informado");
        }
    
        // Busca a categoria existente pelo ID
        Categoria categoriaExistente = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada com o ID: " + categoriaId));
    
        // Copia apenas as propriedades não nulas do DTO para a entidade Categoria
        BeanUtils.copyProperties(categoriaDto, categoriaExistente, getNullPropertyNames(categoriaDto));
    
        // Salva a categoria atualizada
        categoriaExistente = categoriaRepository.save(categoriaExistente);
    
        // Converte a categoria atualizada para CategoriaDto
        return modelMapper.map(categoriaExistente, CategoriaDto.class);
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

    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }

    public Categoria addImagens(Long idCategoria, List<MultipartFile> files) throws IOException {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(idCategoria);
        if (optionalCategoria.isPresent()) {
            Categoria categoria = optionalCategoria.get();
            List<Arquivo> arquivos = new ArrayList<>();
            for (MultipartFile file : files) {
                UploadFileResponse response = fileService.uploadFile(file, S3_BUCKET_NAME_ARQUIVOS);
                if (!response.getFileName().isEmpty()) {
                    arquivos.add(new Arquivo(response.getFileName(),
                            response.getFileDownloadUri() + "/" + response.getFileName(), file.getOriginalFilename(),
                            categoria));
                }
            }
            categoria.setArquivos(arquivos);
            return categoriaRepository.save(categoria);
        } else {
            throw new ResourceNotFoundException("Categoria não encontrada com o ID: " + idCategoria);
        }
    }

}
