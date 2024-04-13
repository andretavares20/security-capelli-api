package com.andretavares.testesecurity.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.AvaliacaoDTO;
import com.andretavares.testesecurity.entities.Avaliacao;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.repositories.AvaliacaoRepository;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private UserService userService;

    public Avaliacao createAvaliacao(AvaliacaoDTO avaliacaoDTO) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setRating(avaliacaoDTO.getRating());
        avaliacao.setTitulo(avaliacaoDTO.getTitulo());
        avaliacao.setDescricao(avaliacaoDTO.getDescricao());

        // Busca o produto pelo ID e associa à avaliação
        Produto produto = produtoService.findById(avaliacaoDTO.getIdProduto());
        if (produto != null) {
            avaliacao.setProduto(produto);
        } else {
            // Lida com o caso em que o produto não foi encontrado
            // Aqui você pode lançar uma exceção, retornar null ou lidar de outra forma conforme necessário
            return null;
        }

        // Busca o usuário pelo ID e associa à avaliação
        User user = userService.findById(avaliacaoDTO.getIdUsuario());
        if (user != null) {
            avaliacao.setUser(user);
        } else {
            // Lida com o caso em que o usuário não foi encontrado
            // Aqui você pode lançar uma exceção, retornar null ou lidar de outra forma conforme necessário
            return null;
        }

        return avaliacaoRepository.save(avaliacao);
    }

    public List<AvaliacaoDTO> getAllAvaliacoes() {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll();
        List<AvaliacaoDTO> avaliacoesDTO = new ArrayList<>();
        for (Avaliacao avaliacao : avaliacoes) {
            AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
            avaliacaoDTO.setId(avaliacao.getId());
            avaliacaoDTO.setIdProduto(avaliacao.getProduto().getId());
            avaliacaoDTO.setIdUsuario(avaliacao.getUser().getId());
            avaliacaoDTO.setRating(avaliacao.getRating());
            avaliacaoDTO.setDescricao(avaliacao.getDescricao());
            avaliacaoDTO.setTitulo(avaliacao.getTitulo());
            avaliacoesDTO.add(avaliacaoDTO);
        }
        return avaliacoesDTO;
    }

    // Método para obter uma avaliação por ID
    public Avaliacao getAvaliacaoById(Long id) {
        Optional<Avaliacao> optionalAvaliacao = avaliacaoRepository.findById(id);
        return optionalAvaliacao.orElse(null);
    }

    public Avaliacao updateAvaliacao(Long id, AvaliacaoDTO avaliacaoDTO) {
        Optional<Avaliacao> optionalAvaliacao = avaliacaoRepository.findById(id);
        if (optionalAvaliacao.isPresent()) {
            Avaliacao avaliacao = optionalAvaliacao.get();
            avaliacao.setRating(avaliacaoDTO.getRating());
            avaliacao.setTitulo(avaliacaoDTO.getTitulo());
            avaliacao.setDescricao(avaliacaoDTO.getDescricao());
            return avaliacaoRepository.save(avaliacao);
        } else {
            return null;
        }
    }

    // Método para excluir uma avaliação
    public boolean deleteAvaliacao(Long id) {
        Optional<Avaliacao> optionalAvaliacao = avaliacaoRepository.findById(id);
        if (optionalAvaliacao.isPresent()) {
            avaliacaoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
