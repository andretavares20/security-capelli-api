package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.OrdemRequest;
import com.andretavares.testesecurity.dto.OrdemResponse;
import com.andretavares.testesecurity.entities.Ordem;
import com.andretavares.testesecurity.services.OrdemService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class OrdemController {

    @Autowired
    private OrdemService ordemService;

    @Operation(summary  = "Finaliza compra no carrinho e gera um ordem de compra em RASCUNHO", description  = "Envie para esse endpoint o id do usuário que ta realizando a compra, o json do objeto Ordem e os itens contendo o id do produto.")
    @PostMapping("/ordens")
    public ResponseEntity<OrdemResponse> create(Long userId, @RequestBody OrdemRequest request) {

        return ResponseEntity.ok().body(ordemService.create(userId, request));

    }

    @Operation(summary  = "Cancela ordem de serviço e atualiza o status para CANCELADO", description  = "Envie para esse endpoint o id do usuário que ta cancelando a ordem e o id da ordem")
    @PutMapping("/ordens/{ordemId}/cancel")
    public ResponseEntity<Ordem> cancelOrdemUser(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.cancelOrdem(ordemId, userId));

    }

    @Operation(summary  = "Finaliza a ordem de serviço após o pedido ser entregue e atualiza o status para FINALIZADO", description  = "Envie para esse endpoint o id do usuário que ta finalizando a ordem e o id da ordem")
    @PutMapping("/ordens/{ordemId}/finalizar-pedido")
    public ResponseEntity<Ordem> receber(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.finalizarPedidos(ordemId, userId));

    }

    @Operation(summary  = "Realiza pagamento da ordem de serviço e atualiza o status para PAGO", description  = "Envie para esse endpoint o id do usuário que pagou a ordem de serviço e o id da ordem de serviço.")
    @PutMapping("/ordens/{ordemId}/confirmar-pagamento")
    public ResponseEntity<Ordem> confirmacao(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.confirmarPagamento(ordemId, userId));

    }

    @Operation(summary  = "Após o pagamento, atualiza o status da ordem de serviço para EMBALAGEM", description  = "Envie para esse endpoint o id do usuário que ta enviando o pedido para embalar e o id da ordem de serviço.")
    @PutMapping("/ordens/{ordemId}/embalar")
    public ResponseEntity<Ordem> embalar(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.embalar(ordemId, userId));

    }

    @Operation(summary  = "Após embalar e despachar para a entrega, atualiza o status da ordem de serviço para ENTREGA", description  = "Envie para esse endpoint o id do usuário que ta enviando o pedido e o id da ordem")
    @PutMapping("/ordens/{ordemId}/enviar")
    public ResponseEntity<Ordem> enviar(Long userId, @PathVariable("ordemId") Long ordemId) {

        return ResponseEntity.ok().body(ordemService.enviar(ordemId, userId));

    }
    
    @Operation(summary  = "Retorna todas as ordens existentes no banco de dados.")
    @GetMapping("/ordens")
    public ResponseEntity<List<Ordem>> findAllOrdemUser(Long userId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {

        return ResponseEntity.ok().body(ordemService.findAllOrdemUser(userId, page, limit));

    }

    @GetMapping("/ordens/admin")
    public ResponseEntity<List<Ordem>> search(Long userId,
            @RequestParam(name = "filterText", defaultValue = "", required = false) String filterText,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "25", required = false) int limit) {

        return ResponseEntity.ok().body(ordemService.search(filterText, page, limit));

    }

}
