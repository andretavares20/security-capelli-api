package com.andretavares.testesecurity.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class CarrinhoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    private LocalDateTime dataCriacao;

    @ManyToMany
    @JoinTable(
        name = "carrinho_produto",
        joinColumns = @JoinColumn(name = "carrinho_id"),
        inverseJoinColumns = @JoinColumn(name = "produtoId")
    )
    private List<Produto> produtos;

    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private PagamentoModel pagamento;

}
