package com.andretavares.testesecurity.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.andretavares.testesecurity.entities.Ordem;
import com.andretavares.testesecurity.entities.OrdemItem;

import lombok.Data;

@Data
public class OrdemResponse {
    
    private Long id;
    private String numeroPedido;
    private LocalDateTime data;
    private String nomeCliente;
    private String enderecoEnvio;
    private LocalDateTime horaPedido;
    private BigDecimal quantia;
    private BigDecimal envio;
    private BigDecimal total;
    private List<OrdemResponse.Item> items;

    public OrdemResponse(Ordem ordem,List<OrdemItem> ordemItems){

        this.id=ordem.getId();
        this.numeroPedido=ordem.getNumber();
        this.data=ordem.getData();
        this.nomeCliente=ordem.getUser().getName();
        this.enderecoEnvio=ordem.getEnderecoEnvio();
        this.horaPedido=ordem.getHoraMensagem();
        this.quantia=ordem.getQuantia();
        this.envio=ordem.getEnvio();
        this.total=ordem.getTotal();
        items= new ArrayList<>();

        for (OrdemItem ordemItem : ordemItems){
            Item item = new Item();
            item.setProdutoId(ordemItem.getProduto().getId());
            item.setNomeProduto(ordemItem.getProduto().getName());
            item.setQuantidade(ordemItem.getQuantidade());
            item.setPreço(ordemItem.getPreço());
            item.setQuantia(ordemItem.getQuantia());
            items.add(item);

        }

    }

    @Data
    public static class Item implements Serializable{

        private Long produtoId;
        private String nomeProduto;
        private Long quantidade;
        private BigDecimal preço;
        private BigDecimal quantia;

    }

}
