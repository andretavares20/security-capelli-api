package com.andretavares.testesecurity.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoRequest {
    
    private String token;
    private String issuer_id;
    private String payment_method_id;
    private BigDecimal transaction_amount;
    private int installments;
    private String description;
    private Payer payer;

    // getters e setters

    @Data
    public static class Payer {
        private String email;
        private Identification identification;

        // getters e setters
    }

    @Data
    public static class Identification {
        private String type;
        private String number;

        // getters e setters
    }

}
