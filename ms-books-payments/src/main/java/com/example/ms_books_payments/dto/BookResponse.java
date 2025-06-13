package com.example.ms_books_payments.dto;

public class BookResponse {
    private Long id;
    private String titulo;
    private Double precio;
    private Boolean visible;
    private Integer stock;

    public BookResponse() {}

    public BookResponse(Long id, String titulo, Double precio, Boolean visible, Integer stock) {
        this.id = id;
        this.titulo = titulo;
        this.precio = precio;
        this.visible = visible;
        this.stock = stock;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Boolean getVisible() { return visible; }
    public void setVisible(Boolean visible) { this.visible = visible; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
