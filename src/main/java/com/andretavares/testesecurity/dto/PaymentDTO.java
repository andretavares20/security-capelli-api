package com.andretavares.testesecurity.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private List<Result> results;
    private Paging paging;

    // getters e setters

    public static class Result {
        private Metadata metadata;
        private String corporation_id;
        private String operation_type;
        private PointOfInteraction point_of_interaction;
        private List<FeeDetail> fee_details;
        private String notification_url;
        private String date_approved;
        private Object money_release_schema;
        private Payer payer;
        private TransactionDetails transaction_details;
        private String statement_descriptor;
        private String call_for_authorize_id;
        private int installments;
        private String pos_id;
        private String external_reference;
        private String date_of_expiration;
        private List<ChargesDetail> charges_details;
        private long id;
        private String payment_type_id;
        private PaymentMethod payment_method;
        private Order order;
        private String counter_currency;
        private String money_release_status;
        private String brand_id;
        private String status_detail;
        private String tags;
        private String differential_pricing_id;
        private AdditionalInfo additional_info;
        private boolean live_mode;
        private String marketplace_owner;
        private Card card;
        private String integrator_id;
        private String status;
        private Object accounts_info;
        private double transaction_amount_refunded;
        private double transaction_amount;
        private String description;
        private String financing_group;
        private String money_release_date;
        private String merchant_number;
        private List<Refund> refunds;
        private Expanded expanded;

        // getters e setters

        public static class Metadata {
            // campos do metadata, se existirem
        }

        public static class PointOfInteraction {
            private BusinessInfo business_info;
            private String type;

            // getters e setters

            public static class BusinessInfo {
                private String unit;
                private String sub_unit;

                // getters e setters
            }
        }

        public static class FeeDetail {
            private double amount;
            private String fee_payer;
            private String type;

            // getters e setters
        }

        public static class Payer {
            private String entity_type;
            private Identification identification;
            private Phone phone;
            private String operator_id;
            private String last_name;
            private String id;
            private String type;
            private String first_name;
            private String email;

            // getters e setters

            public static class Identification {
                private String number;
                private String type;

                // getters e setters
            }

            public static class Phone {
                private String number;
                private String extension;
                private String area_code;

                // getters e setters
            }
        }

        public static class TransactionDetails {
            private double total_paid_amount;
            private String acquirer_reference;
            private double installment_amount;
            private String financial_institution;
            private double net_received_amount;
            private double overpaid_amount;
            private String external_resource_url;
            private String payable_deferral_period;
            private String payment_method_reference_id;

            // getters e setters
        }

        public static class ChargesDetail {
            private List<Refund> refund_charges;
            private String last_updated;
            private Metadata metadata;
            private Amounts amounts;
            private String date_created;
            private String name;
            private String reserve_id;
            private Accounts accounts;
            private String id;
            private String type;
            private int client_id;

            // getters e setters

            public static class Amounts {
                private double original;
                private double refunded;

                // getters e setters
            }

            public static class Accounts {
                private String from;
                private String to;

                // getters e setters
            }
        }

        public static class PaymentMethod {
            private String issuer_id;
            private Data data;
            private String id;
            private String type;

            // getters e setters

            public static class Data {
                private RoutingData routing_data;

                // getters e setters

                public static class RoutingData {
                    private String merchant_account_id;

                    // getters e setters
                }
            }
        }

        public static class Order {
            // campos da ordem, se existirem
        }

        public static class AdditionalInfo {
            private String authentication_code;
            private String nsu_processadora;
            private String available_balance;

            // getters e setters
        }

        public static class Card {
            private String first_six_digits;
            private int expiration_year;
            private String bin;
            private String date_created;
            private int expiration_month;
            private String id;
            private Cardholder cardholder;
            private String last_four_digits;
            private String date_last_updated;

            // getters e setters

            public static class Cardholder {
                private Identification identification;
                private String name;

                // getters e setters

                public static class Identification {
                    private String number;
                    private String type;

                    // getters e setters
                }
            }
        }

        public static class Refund {
            // campos do refund, se existirem
        }

        public static class Expanded {
            private Gateway gateway;

            // getters e setters

            public static class Gateway {
                private double buyer_fee;
                private double finance_charge;
                private String date_created;
                private String merchant;
                private String reference;
                private String statement_descriptor;
                private String issuer_id;
                private String usn;
                private int installments;
                private String soft_descriptor;
                private String authorization_code;
                private long payment_id;
                private String profile_id;
                private String options;
                private String connection;
                private String id;
                private String operation;

                // getters e setters
            }
        }
    }

    public static class Paging {
        private int total;
        private int limit;
        private int offset;

        // getters e setters
    }
}
