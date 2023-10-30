package com.andretavares.testesecurity.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.CarrinhoRequest;
import com.andretavares.testesecurity.dto.OrdemRequest;
import com.andretavares.testesecurity.dto.OrdemResponse;
import com.andretavares.testesecurity.entities.Ordem;
import com.andretavares.testesecurity.entities.OrdemItem;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.enums.StatusOrdem;
import com.andretavares.testesecurity.exceptions.ApiRequestException;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.OrdemItemRepository;
import com.andretavares.testesecurity.repositories.OrdemRepository;
import com.andretavares.testesecurity.repositories.ProdutoRepository;
import com.andretavares.testesecurity.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
public class OrdemService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private OrdemRepository ordemRepository;

    @Autowired
    private OrdemItemRepository ordemItemRepository;

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private OrdemLogService ordemLogService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public OrdemResponse create(Long idUser, OrdemRequest request) {
        Ordem ordem = new Ordem();
        ordem.setData(LocalDateTime.now());
        ordem.setNumber(generateNumeroOrdem());
        ordem.setUser(new User(idUser));
        ordem.setEnderecoEnvio(request.getEnderecoEnvio());
        ordem.setStatusOrdem(StatusOrdem.RASCUNHO);
        ordem.setHoraMensagem(LocalDateTime.now());

        List<OrdemItem> items = new ArrayList<>();
        for (CarrinhoRequest k : request.getItems()) {
            Produto produto = produtoRepository.findById(k.getProdutoId())
                    .orElseThrow(
                            () -> new BadRequestException("Produto Id " + k.getProdutoId() + "não foi encontrado"));

            if (produto.getEstoque() < k.getQuantidade()) {
                throw new BadRequestException("Estoque insuficiente");
            }

            OrdemItem ordemItem = new OrdemItem();
            ordemItem.setProduto(produto);
            ordemItem.setDescription(produto.getName());
            ordemItem.setQuantidade(k.getQuantidade());
            ordemItem.setPreço(produto.getPrice());
            ordemItem.setQuantia(new BigDecimal(ordemItem.getPreço().doubleValue() * ordemItem.getQuantidade()));
            ordemItem.setOrdem(ordem);
            items.add(ordemItem);

        }

        BigDecimal quantia = BigDecimal.ZERO;
        for (OrdemItem ordemItem : items) {
            quantia = quantia.add(ordemItem.getQuantia());
        }

        ordem.setQuantia(quantia);
        ordem.setEnvio(request.getEnvio());
        ordem.setTotal(ordem.getQuantia().add(ordem.getEnvio()));

        Ordem saved = ordemRepository.save(ordem);
        for (OrdemItem ordemItem : items) {
            ordemItemRepository.save(ordemItem);
            Produto produto = ordemItem.getProduto();
            produto.setEstoque(produto.getEstoque() - ordemItem.getQuantidade());
            produtoRepository.save(produto);
            carrinhoService.delete(idUser, produto.getId());
        }

        ordemLogService.createLog(idUser, ordem, 0, "Pedido feito com sucesso");

        Optional<User> optionalUser = userRepository.findById(saved.getUser().getId());
        if (!optionalUser.isPresent()){
            throw new BadRequestException("Usuario não encontrado");
        }

        saved.getUser().setName(optionalUser.get().getName());

        OrdemResponse ordemResponse = new OrdemResponse(saved, items);
        return ordemResponse;

    }

    @Transactional
    public Ordem cancelOrdem(Long ordemId, Long userId) {
        Ordem ordem = ordemRepository.findById(ordemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem Id: " + ordemId + "não encontrado"));
        if (!userId.equals(ordem.getUser().getId())) {
            throw new BadRequestException("Este pedido só pode ser cancelado pela própria pessoa");
        }
        if (!StatusOrdem.RASCUNHO.equals(ordem.getStatusOrdem())) {
            throw new BadRequestException("Este pedido nao pode ser cancelado pois ja foi processado");
        }

        ordem.setStatusOrdem(StatusOrdem.CANCELADO);
        Ordem saved = ordemRepository.save(ordem);
        ordemLogService.createLog(userId, saved, OrdemLogService.CANCELADO, "Pedido cancelado com sucesso");

        return saved;

    }

    @Transactional
    public Ordem finalizarPedidos(Long ordemId, Long userId) {
        Ordem ordem = ordemRepository.findById(ordemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem Id: " + ordemId + "não encontrado"));
        if (!userId.equals(ordem.getUser().getId())) {
            throw new BadRequestException("Este pedido só pode ser finalizado pela própria pessoa");
        }
        if (!StatusOrdem.ENTREGA.equals(ordem.getStatusOrdem())) {
            throw new BadRequestException(
                    "Falha na recepção, o status atual do pedido é " + ordem.getStatusOrdem().name());
        }

        ordem.setStatusOrdem(StatusOrdem.FINALIZADO);
        Ordem saved = ordemRepository.save(ordem);
        ordemLogService.createLog(userId, saved, OrdemLogService.FINALIZADO,
                "Pedido cancelado com sucesso " + ordem.getStatusOrdem().name());

        return saved;

    }

    public List<Ordem> findAllOrdemUser(Long userId, int page, int limit) {
        return ordemRepository.findByUserId(userId, PageRequest.of(page, limit, Sort.by("horaMensagem").descending()));
    }

    public List<Ordem> search(String filterText, int page, int limit) {
        return ordemRepository.search(filterText.toLowerCase(),
                PageRequest.of(page, limit, Sort.by("horaMensagem").descending()));
    }

    private String generateNumeroOrdem() {
        return String.format("%016d", System.nanoTime());
    }

    @Transactional
    public Ordem confirmarPagamento(Long ordemId, Long userId) {
        Ordem ordem = ordemRepository.findById(ordemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem Id: " + ordemId + "não encontrado"));

        if (!StatusOrdem.RASCUNHO.equals(ordem.getStatusOrdem())) {
            throw new BadRequestException(
                    "A confirmação do pedido falhou, o status atual do pedido é " + ordem.getStatusOrdem().name());
        }

        ordem.setStatusOrdem(StatusOrdem.PAGO);
        Ordem saved = ordemRepository.save(ordem);
        ordemLogService.createLog(userId, saved, OrdemLogService.PAGAMENTO, "Pagamento bem sucedido e confirmado");

        return saved;

    }

    @Transactional
    public Ordem embalar(Long ordemId, Long userId) {
        Ordem ordem = ordemRepository.findById(ordemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem Id: " + ordemId + "não encontrado"));

        if (!StatusOrdem.PAGO.equals(ordem.getStatusOrdem())) {
            throw new BadRequestException(
                    "Falha na embalagem do pedido, o status atual do pedido é " + ordem.getStatusOrdem().name());
        }

        ordem.setStatusOrdem(StatusOrdem.EMBALAGEM);
        Ordem saved = ordemRepository.save(ordem);
        ordemLogService.createLog(userId, saved, OrdemLogService.EMBALAGEM, "Pedidos estão sendo preparados");

        return saved;

    }

    @Transactional
    public Ordem enviar(Long ordemId, Long userId) {
        Ordem ordem = ordemRepository.findById(ordemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem Id: " + ordemId + "não encontrado"));

        if (!StatusOrdem.EMBALAGEM.equals(ordem.getStatusOrdem())) {
            throw new BadRequestException(
                    "Falha na entrega do pedido, o status atual do pedido é " + ordem.getStatusOrdem().name());
        }

        ordem.setStatusOrdem(StatusOrdem.ENTREGA);
        Ordem saved = ordemRepository.save(ordem);
        ordemLogService.createLog(userId, saved, OrdemLogService.ENTREGA, "Pedido está sendo enviado");

        return saved;

    }

    public Ordem getOrdemById(Long ordemId) {
        return ordemRepository.findById(ordemId)
                .orElseThrow(() -> new ApiRequestException("Ordem não foi encontrada", HttpStatus.NOT_FOUND));
    }

    public List<OrdemItem> getOrdemItemsByOrdemId(Long ordemId) {
        Ordem ordem = getOrdemById(ordemId);
        return ordem.getOrdemItems();
    }

    @Transactional
    public String deleteOrdem(Long ordemId) {
        Ordem ordem = ordemRepository.findById(ordemId)
                .orElseThrow(() -> new ApiRequestException("Ordem não encontrada", HttpStatus.NOT_FOUND));
        ordemRepository.delete(ordem);
        return "Order deleted successfully";
    }

}
