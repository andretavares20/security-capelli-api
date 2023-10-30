package com.andretavares.testesecurity.services;

import org.springframework.http.ResponseEntity;

import com.andretavares.testesecurity.dto.PagamentoRequest;
import com.andretavares.testesecurity.dto.PaymentDTO;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

public interface PagamentoService {
    
    public Payment createPayment(PagamentoRequest request) throws MPException, MPApiException;

    public ResponseEntity<PaymentDTO> searchPayments() throws MPException, MPApiException;
}
