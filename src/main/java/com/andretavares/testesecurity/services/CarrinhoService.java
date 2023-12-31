package com.andretavares.testesecurity.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.entities.Carrinho;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.entities.Tamanho;
import com.andretavares.testesecurity.entities.Tecnica;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.entities.Volume;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.repositories.CarrinhoRepository;
import com.andretavares.testesecurity.repositories.ProdutoRepository;
import com.andretavares.testesecurity.repositories.TamanhoRepository;
import com.andretavares.testesecurity.repositories.TecnicaRepository;
import com.andretavares.testesecurity.repositories.UserRepository;
import com.andretavares.testesecurity.repositories.VolumeRepository;

import jakarta.transaction.Transactional;

@Service
public class CarrinhoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TamanhoRepository tamanhoRepository;

    @Autowired
    private TecnicaRepository tecnicaRepository;

    @Autowired
    private VolumeRepository volumeRepository;

    @Transactional
    public Carrinho addCarrinho(Long idUser, Long produtoId, Long quantidade, Long tamanhoId, Long tecnicaId,
            Long volumeId) {

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new BadRequestException("Produto de id " + produtoId + " não encontrado"));
        // produto.setTamanho(tamanho);
        // produto.setTecnica(tecnica);
        // produto.setVolume(volume);

        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findByUserIdAndProdutoId(idUser, produtoId);
        Carrinho carrinho;
        // if(optionalCarrinho.isPresent()){
        // carrinho = optionalCarrinho.get();
        // carrinho.setQuantidade(carrinho.getQuantidade()+quantidade);
        // carrinho.setQuantia(new
        // BigDecimal(carrinho.getPreco().doubleValue()*carrinho.getQuantidade()));

        // Optional<Tecnica> optionalTecnica = tecnicaRepository.findById(tecnicaId);
        // if(optionalTecnica.isPresent()){
        // carrinho.setTecnica(optionalTecnica.get());
        // }

        // Optional<Tamanho> optionalTamanho = tamanhoRepository.findById(tamanhoId);
        // if(optionalTamanho.isPresent()){
        // carrinho.setTamanho(optionalTamanho.get());
        // }

        // Optional<Volume> optionalVolume = volumeRepository.findById(volumeId);
        // if(optionalVolume.isPresent()){
        // carrinho.setVolume(optionalVolume.get());
        // }

        // carrinhoRepository.save(carrinho);
        // }else{
        carrinho = new Carrinho();
        carrinho.setProduto(produto);
        carrinho.setQuantidade(quantidade);
        carrinho.setPreco(produto.getPrice());
        carrinho.setQuantia(new BigDecimal(carrinho.getPreco().doubleValue() * carrinho.getQuantidade()));
        carrinho.setUser(new User(idUser));

        Optional<Tecnica> optionalTecnica = tecnicaRepository.findById(tecnicaId);
        if (optionalTecnica.isPresent()) {
            carrinho.setTecnica(optionalTecnica.get());
        }

        Optional<Tamanho> optionalTamanho = tamanhoRepository.findById(tamanhoId);
        if (optionalTamanho.isPresent()) {
            carrinho.setTamanho(optionalTamanho.get());
        }

        Optional<Volume> optionalVolume = volumeRepository.findById(volumeId);
        if (optionalVolume.isPresent()) {
            carrinho.setVolume(optionalVolume.get());
        }

        carrinho.setDataCriacao(LocalDateTime.now());

        carrinhoRepository.save(carrinho);
        // }

        return carrinho;

    }

    public Carrinho updateQuantidade(Long idUser, Long produtoId, Long quantidade) {
        Carrinho carrinho = carrinhoRepository.findByUserIdAndProdutoId(idUser, produtoId)
                .orElseThrow(() -> new BadRequestException(
                        "Produto Id " + produtoId + " não foi encontrado no seu carrinho"));

        carrinho.setQuantidade(quantidade);
        carrinho.setQuantia(new BigDecimal(carrinho.getPreco().doubleValue() * carrinho.getQuantidade()));
        carrinhoRepository.save(carrinho);
        return carrinho;

    }

    public void delete(Long idUser, Long produtoId) {

        Carrinho carrinho = carrinhoRepository.findByUserIdAndProdutoId(idUser, produtoId)
                .orElseThrow(() -> new BadRequestException(
                        "Produto Id " + produtoId + " não foi encontrado no seu carrinho"));

        carrinhoRepository.delete(carrinho);

    }

    public void deleteAll(Long idUser) {

        List<Carrinho> listCarrinho = carrinhoRepository.findAllByUserId(idUser);

        carrinhoRepository.deleteAll(listCarrinho);

    }

    public List<Carrinho> findByUserId(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            List<Carrinho> listCarrinho =  carrinhoRepository.findByUserId(optionalUser.get().getId());
            return listCarrinho;
        }

        return null;
    }

    public double calculaValorTotal(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            List<Carrinho> listCarrinho =  carrinhoRepository.findByUserId(optionalUser.get().getId());
            double valorTotal = 0.00;
            for(Carrinho carrinho:listCarrinho){

                valorTotal = valorTotal+carrinho.getQuantia().doubleValue();
            }
            return valorTotal;
        }

        return 0.00;
    }

}
