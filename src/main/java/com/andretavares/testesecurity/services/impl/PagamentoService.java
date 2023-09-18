package com.andretavares.testesecurity.services.impl;

import com.andretavares.testesecurity.dto.PagamentoRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.resources.payment.Payment;

public interface PagamentoService {
    
    public Payment createPayment(PagamentoRequest request) throws MPException, MPApiException;

    public MPResultsResourcesPage<Payment> searchPayments() throws MPException, MPApiException;
}
