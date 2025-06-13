package com.example.ms_books_payments.dto;

public class PaymentWithPrice {
    private Long id;
    private String emailUsuario;
    private Long bookId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

    public PaymentWithPrice() {}

    public PaymentWithPrice(Long id, String emailUsuario, Long bookId, Integer cantidad, Double precioUnitario) {
        this.id = id;
        this.emailUsuario = emailUsuario;
        this.bookId = bookId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    private void calcularSubtotal() {
        if (this.cantidad != null && this.precioUnitario != null) {
            this.subtotal = this.cantidad * this.precioUnitario;
        }
    }

    @Override
    public String toString() {
        return "PaymentWithPrice{" +
                "id=" + id +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", bookId=" + bookId +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
}

