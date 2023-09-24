package com.andretavares.testesecurity.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class PedidoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @OneToOne
    @JoinColumn(name = "carrinho_id")
    private CarrinhoModel carrinho;

    @ManyToOne
    @JoinColumn(name = "pagamento_id")
    private PagamentoModel pagamento;

    private LocalDateTime dataPedido;

}
