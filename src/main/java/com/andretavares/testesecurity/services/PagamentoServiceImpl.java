package com.andretavares.testesecurity.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.dto.PagamentoRequest;
import com.andretavares.testesecurity.services.impl.PagamentoService;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResultsResourcesPage;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.payment.Payment;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Override
    public Payment createPayment(PagamentoRequest request) throws MPException, MPApiException {

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(request.getTransaction_amount())
                .token(request.getToken())
                .description(request.getDescription())
                .installments(request.getInstallments())
                .paymentMethodId(request.getPayment_method_id())
                .payer(PaymentPayerRequest.builder()
                        .email(request.getPayer().getEmail())
                        .firstName(request.getPayer().getIdentification().getNumber())
                        .identification(IdentificationRequest.builder()
                                .type(request.getPayer().getIdentification().getType())
                                .number(request.getPayer().getIdentification().getNumber())
                                .build())
                        .build())
                .build();

        try {
            Payment payment = client.create(paymentCreateRequest);
            System.out.println(payment.toString());
            return payment;
        } catch (MPApiException ex) {
            System.out.printf(
                    "MercadoPago Error. Status: %s, Content: %s%n",
                    ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
            return null;
        } catch (MPException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public MPResultsResourcesPage<Payment> searchPayments() throws MPException, MPApiException {

        PaymentClient client = new PaymentClient();

        Map<String, Object> filters = new HashMap<>();
        filters.put("sort", "date_created");
        filters.put("criteria", "desc");
        filters.put("external_reference", "ID_REF");

        MPSearchRequest searchRequest = MPSearchRequest.builder().offset(0).limit(0).filters(filters).build();

        MPResultsResourcesPage<Payment> listPayments = client.search(searchRequest);
        return listPayments;
    }

}
