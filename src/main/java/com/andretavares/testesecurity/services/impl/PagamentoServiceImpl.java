package com.andretavares.testesecurity.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.andretavares.testesecurity.dto.PagamentoRequest;
import com.andretavares.testesecurity.dto.PaymentDTO;
import com.andretavares.testesecurity.services.PagamentoService;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    private RestTemplate restTemplate;

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
    public ResponseEntity<PaymentDTO> searchPayments() throws MPException, MPApiException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "APP_USR-4044482508044042-091415-4c050743d2e1388029b384704b4ec264-170994291");
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<PaymentDTO> responseEntity = restTemplate.exchange(
            "https://api.mercadopago.com/v1/payments/search?sort=date_created&criteria=desc",
            HttpMethod.GET,
            httpEntity,
            PaymentDTO.class
        );

        return responseEntity;
    }

}
