package com.example.ms_books_payments.dto;

public class BookResponseWrapper {
    private String mensaje;
    private BookResponse libro;

    public BookResponseWrapper() {}

    public BookResponseWrapper(String mensaje, BookResponse libro) {
        this.mensaje = mensaje;
        this.libro = libro;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public BookResponse getLibro() { return libro; }
    public void setLibro(BookResponse libro) { this.libro = libro; }
}
