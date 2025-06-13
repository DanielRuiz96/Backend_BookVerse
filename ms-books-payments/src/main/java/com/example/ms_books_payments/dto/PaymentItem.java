package com.example.ms_books_payments.dto;

public class PaymentItem {
    private Long bookId;
    private Integer cantidad;

    public PaymentItem() {}

    public PaymentItem(Long bookId, Integer cantidad) {
        this.bookId = bookId;
        this.cantidad = cantidad;
    }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
