package com.example.ms_books_catalogue.exception;

public class BookNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Long bookId;

    public BookNotFoundException(Long bookId) {
        super("Libro no encontrado con id: " + bookId);
        this.bookId = bookId;
    }

    public BookNotFoundException(String message) {
        super(message);
        this.bookId = null;
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.bookId = null;
    }

    public Long getBookId() {
        return bookId;
    }
}
