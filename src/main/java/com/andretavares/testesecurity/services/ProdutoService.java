package com.andretavares.testesecurity.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.andretavares.testesecurity.dto.ProdutoDto;
import com.andretavares.testesecurity.dto.ProdutoTamanhoVolumesDto;
import com.andretavares.testesecurity.dto.UploadFileResponse;
import com.andretavares.testesecurity.dto.VolumeDto;
import com.andretavares.testesecurity.entities.Arquivo;
import com.andretavares.testesecurity.entities.Categoria;
import com.andretavares.testesecurity.entities.Cor;
import com.andretavares.testesecurity.entities.OrdemItem;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.entities.ProdutoTamanho;
import com.andretavares.testesecurity.entities.ProdutoVolume;
import com.andretavares.testesecurity.entities.Tamanho;
import com.andretavares.testesecurity.entities.Volume;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.ArquivoRepository;
import com.andretavares.testesecurity.repositories.CategoriaRepository;
import com.andretavares.testesecurity.repositories.CorRepository;
import com.andretavares.testesecurity.repositories.OrdemItemRepository;
import com.andretavares.testesecurity.repositories.OrdemLogRepository;
import com.andretavares.testesecurity.repositories.OrdemRepository;
import com.andretavares.testesecurity.repositories.ProdutoRepository;
import com.andretavares.testesecurity.repositories.TamanhoRepository;
import com.andretavares.testesecurity.repositories.VolumeRepository;

import lombok.RequiredArgsConstructor;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CorRepository corRepository;
    @Autowired
    private OrdemItemRepository ordemItemRepository;
    @Autowired
    private OrdemRepository ordemRepository;
    @Autowired
    private OrdemLogRepository ordemLogRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private ArquivoRepository arquivoRepository;
    @Value("${s3.bucket-name.arquivos}")
    @Autowired
    private String S3_BUCKET_NAME_ARQUIVOS;
    @Autowired
    private TamanhoRepository tamanhoRepository;
    @Autowired
    private VolumeRepository volumeRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }

    public Produto create(ProdutoDto produtoDto) {

        if (!StringUtils.hasText(produtoDto.getName())) {
            throw new BadRequestException("Produto informado esta sem nome");
        }

        if (produtoDto.getCategoriaId() == null) {
            throw new BadRequestException("Produto informado esta sem categoria");
        }

        if (produtoDto.getCategoriaId() == null) {
            throw new BadRequestException("Cor informada esta sem id");
        }

        Categoria categoria = categoriaRepository.findById(produtoDto.getCategoriaId())
                .orElseThrow(() -> new BadRequestException(
                        "Categoria ID " + produtoDto.getCategoriaId() + " não existe"));

        Produto produto = new Produto(produtoDto.getName(), produtoDto.getDescription(),
                categoria);

        try {

            // Criar e salvar os ProdutoTamanho e ProdutoVolume para o produto
            List<ProdutoTamanho> produtoTamanhos = new ArrayList<>();

            produto.setProdutoTamanhos(produtoTamanhos);

            return produtoRepository.save(produto);
        } catch (Exception e) {
            // TODO: handle exception
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Já existe um produto com esta cor", e);
        }

    }

    public Produto createProdutoComTamanhosEVolumes(ProdutoDto produtoDto) {
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDto, produto);

        List<ProdutoTamanho> produtoTamanhos = new ArrayList<>();

        for (ProdutoTamanhoVolumesDto tamanhoDto : produtoDto.getProdutoTamanhoVolumesDto()) {
            Tamanho tamanho = tamanhoRepository.findById(tamanhoDto.getTamanhoId())
                    .orElseThrow(() -> new RuntimeException("Tamanho não encontrado com ID: " + tamanhoDto.getTamanhoId()));

            ProdutoTamanho produtoTamanho = new ProdutoTamanho();
            produtoTamanho.setProduto(produto);
            produtoTamanho.setTamanho(tamanho);

            List<ProdutoVolume> produtoVolumes = new ArrayList<>();

            for (VolumeDto volumeDto : tamanhoDto.getVolumes()) {
                Volume volume = volumeRepository.findById(volumeDto.getId())
                        .orElseThrow(() -> new RuntimeException("Volume não encontrado com ID: " + volumeDto.getId()));

                ProdutoVolume produtoVolume = new ProdutoVolume();
                produtoVolume.setProdutoTamanho(produtoTamanho);
                produtoVolume.setVolume(volume);
                produtoVolume.setPrice(volumeDto.getPrice()); // Definindo o preço do volume
                produtoVolumes.add(produtoVolume);
            }

            produtoTamanho.setProdutoVolumes(produtoVolumes);
            produtoTamanhos.add(produtoTamanho);
        }

        produto.setProdutoTamanhos(produtoTamanhos);

        return produtoRepository.save(produto);
    }

    public Produto addImagens(Long idProduto, List<MultipartFile> files) throws IOException {

        Optional<Produto> optionalProduto = produtoRepository.findById(idProduto);
        Produto produto;

        if (optionalProduto.isPresent()) {

            produto = optionalProduto.get();

            if (!StringUtils.hasText(produto.getName())) {
                throw new BadRequestException("Produto informado esta sem nome");
            }

            if (produto.getCategoria() == null) {
                throw new BadRequestException("Produto informado não pertence a nenhuma categoria");
            }

            if (produto.getCategoria().getId() == null) {
                throw new BadRequestException("Categorua informada esta sem id");
            }

            Cor cor = corRepository.findById(produto.getCategoria().getId())
                    .orElseThrow(() -> new BadRequestException(
                            "Cor ID " + produto.getCategoria().getId() + " não existe"));

            Categoria categoria = categoriaRepository.findById(cor.getCategoria().getId())
                    .orElseThrow(() -> new BadRequestException(
                            "Categoria ID " + cor.getCategoria().getId() + " não existe"));

            List<Arquivo> arquivos = new ArrayList<>();

            for (MultipartFile file : files) {
                UploadFileResponse response = fileService.uploadFile(file, S3_BUCKET_NAME_ARQUIVOS);
                if (response.getFileName() != "") {
                    arquivos.add(new Arquivo(response.getFileName(),
                            response.getFileDownloadUri() + "/" + response.getFileName(), file.getOriginalFilename(),
                            produto));
                }
            }
            produto.setArquivos(arquivos);

            return produtoRepository.save(produto);
        }
        return null;

    }

    public Produto edit(Produto produto) {

        Optional<Produto> produtoExistente = produtoRepository.findById(produto.getId());
        if (produtoExistente.isPresent()) {
            BeanUtils.copyProperties(produto, produtoExistente.get(), "id");
            produtoRepository.save(produtoExistente.get());
            return produtoExistente.get();
        } else {
            System.out.println("Id não encontrado no banco de dados.");
        }

        return null;
    }

    public void deleteById(Long id) {

        List<OrdemItem> listOrdem = ordemItemRepository.findAllByProdutoId(id);

        for (OrdemItem ordemItem : listOrdem) {
            ordemItemRepository.deleteById(ordemItem.getId());
            ordemRepository.deleteById(ordemItem.getOrdem().getId());
            ordemLogRepository.deleteByOrdemId(ordemItem.getOrdem().getId());
        }

        produtoRepository.deleteById(id);
    }

    public List<Produto> listaProdutosPorCategoriaId(Long categoriaId) {

        Optional<Categoria> optionalCategoria = categoriaRepository.findById(categoriaId);

        if (optionalCategoria.isPresent()) {

            List<Produto> listProduto = produtoRepository.findAllByCategoriaId(categoriaId);

            if (!listProduto.isEmpty()) {

                return listProduto;
            }

            throw new BadRequestException("Não existem produtos para esta categoria Id");
        }

        throw new BadRequestException("Categoria não encontrada.");

    }

    public List<Produto> listaProdutosPorCategoriaNome(String nomeCategoria) {

        Categoria categoria = categoriaRepository.findByNome(nomeCategoria);

        if (categoria != null) {

            List<Produto> listProdutos = produtoRepository.findAllByCategoriaId(categoria.getId());

            if (!listProdutos.isEmpty()) {

                return listProdutos;
            }

            throw new BadRequestException("Não existe produto nesta categoria");

        }

        throw new BadRequestException("Categoria não encontrada");

    }

    public List<MultipartFile> listaImagensProduto(Long produtoId) throws IOException {
        List<Arquivo> listArquivo = arquivoRepository.findAllByProdutoId(produtoId);

        for (Arquivo arquivo : listArquivo) {
            FileOutputStream uploadFileResponse = fileService.downloadObject(S3_BUCKET_NAME_ARQUIVOS,
                    arquivo.getNome());
        }

        // Categoria categoria = categoriaRepository.findByNome(nomeCategoria);

        // List<Cor> listCor = corRepository.findAllByCategoriaId(categoria.getId());

        // List<Produto> listProduto = new ArrayList();

        // for (Cor cor : listCor) {
        // Produto produto = produtoRepository.findByCorId(cor.getId());
        // listProduto.add(produto);
        // }
        return null;

    }

}
