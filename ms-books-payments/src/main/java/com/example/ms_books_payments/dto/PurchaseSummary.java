package com.example.ms_books_payments.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseSummary {

    private String purchaseId;
    private String emailUsuario;
    private LocalDateTime fechaCompra;
    private List<PaymentWithPrice> pagos;
    private Double totalCompra; 

    public PurchaseSummary() {}

    public PurchaseSummary(String purchaseId, String emailUsuario, LocalDateTime fechaCompra, List<PaymentWithPrice> pagos) {
        this.purchaseId = purchaseId;
        this.emailUsuario = emailUsuario;
        this.fechaCompra = fechaCompra;
        this.pagos = pagos;
        calcularTotalCompra(); // calcula el total
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public List<PaymentWithPrice> getPagos() {
        return pagos;
    }

    public void setPagos(List<PaymentWithPrice> pagos) {
        this.pagos = pagos;
        calcularTotalCompra();
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

    // Lógica para calcular total
    private void calcularTotalCompra() {
        if (pagos != null) {
            this.totalCompra = pagos.stream()
                .mapToDouble(PaymentWithPrice::getSubtotal)
                .sum();
        } else {
            this.totalCompra = 0.0;
        }
    }
}
