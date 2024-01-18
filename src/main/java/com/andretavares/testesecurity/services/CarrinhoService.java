package com.andretavares.testesecurity.services;

import java.math.BigDecimal;
import java.security.Principal;
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
            Long volumeId,Principal userLogged) {

        Optional<User> optionalUser = userRepository.findById(idUser);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if(!user.getEmail().equals(userLogged.getName())){

                System.out.println("Usuário que solicitou não é o mesmo que esta logado.");
                throw new BadRequestException("Usuário que solicitou não é o mesmo que esta logado.");
            }
            
        }

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new BadRequestException("Produto de id " + produtoId + " não encontrado"));

        Optional<Carrinho> optionalCarrinho = carrinhoRepository.findByUserIdAndProdutoId(idUser, produtoId);
        Carrinho carrinho;

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

        return carrinho;

    }

    public Carrinho updateQuantidade(Long idUser, Long produtoId, Long quantidade, Principal userLogged) {

        Optional<User> optionalUser = userRepository.findById(idUser);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if(!user.getEmail().equals(userLogged.getName())){

                System.out.println("Usuário que solicitou não é o mesmo que esta logado.");
                throw new BadRequestException("Usuário que solicitou não é o mesmo que esta logado.");
            }
            
        }

        Carrinho carrinho = carrinhoRepository.findByUserIdAndProdutoId(idUser, produtoId)
                .orElseThrow(() -> new BadRequestException(
                        "Produto Id " + produtoId + " não foi encontrado no seu carrinho"));

        carrinho.setQuantidade(quantidade);
        carrinho.setQuantia(new BigDecimal(carrinho.getPreco().doubleValue() * carrinho.getQuantidade()));
        carrinhoRepository.save(carrinho);
        return carrinho;


    }

    public void delete(Long idUser, Long idCarrinho, Principal userLogged) {

        Optional<User> optionalUser = userRepository.findById(idUser);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if(!user.getEmail().equals(userLogged.getName())){

                System.out.println("Usuário que solicitou não é o mesmo que esta logado.");
                throw new BadRequestException("Usuário que solicitou não é o mesmo que esta logado.");
            }
            
        }

        Carrinho carrinho = carrinhoRepository.findById(idCarrinho)
                .orElseThrow(() -> new BadRequestException(
                        "Carrinho Id " + idCarrinho + " não foi encontrado"));

        carrinhoRepository.delete(carrinho);

    }

    public void deleteAll(Long idUser,Principal userLogged) {

        Optional<User> optionalUser = userRepository.findById(idUser);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if(!user.getEmail().equals(userLogged.getName())){

                System.out.println("Usuário que solicitou não é o mesmo que esta logado.");
                throw new BadRequestException("Usuário que solicitou não é o mesmo que esta logado.");
            }
            
        }

        List<Carrinho> listCarrinho = carrinhoRepository.findAllByUserId(idUser);

        carrinhoRepository.deleteAll(listCarrinho);

    }

    public List<Carrinho> findByUserId(Long id,Principal userLogged) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if(user.getEmail().equals(userLogged.getName())){

                List<Carrinho> listCarrinho =  carrinhoRepository.findByUserId(optionalUser.get().getId());
                return listCarrinho;
            }else{
                System.out.println("Usuário que solicitou não é o mesmo que esta logado.");
                throw new BadRequestException("Usuário que solicitou não é o mesmo que esta logado.");
            }

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
