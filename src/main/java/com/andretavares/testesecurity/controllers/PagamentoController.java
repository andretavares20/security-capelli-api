package com.andretavares.testesecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.PagamentoRequest;
import com.andretavares.testesecurity.services.impl.PagamentoService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.resources.payment.Payment;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,path = "/process_payment")
    public ResponseEntity<Payment> processPayment(@RequestBody PagamentoRequest pagamentoRequest) throws MPException, MPApiException {
        
        Payment payment = pagamentoService.createPayment(pagamentoRequest);

        return ResponseEntity.ok().body(payment);
    }

    @GetMapping(path = "/search-payments")
    public ResponseEntity<MPResultsResourcesPage<Payment>> searchPayments() throws MPException, MPApiException {

        MPResultsResourcesPage<Payment> listPayments =  pagamentoService.searchPayments();

        return ResponseEntity.ok().body(listPayments);

    }
}
