package com.example.ms_books_payments.dto;

import java.util.List;

public class PaymentRequest {
    private String emailUsuario;
    private List<PaymentItem> items;

    public PaymentRequest() {}

    public PaymentRequest(String emailUsuario, List<PaymentItem> items) {
        this.emailUsuario = emailUsuario;
        this.items = items;
    }

    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }
    public List<PaymentItem> getItems() { return items; }
    public void setItems(List<PaymentItem> items) { this.items = items; }
}
