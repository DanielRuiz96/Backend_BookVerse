package com.example.ms_books_payments.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;
    private String userEmail;
    private Double amount;
    private LocalDateTime timestamp;

    public Payment() {}

    public Payment(Long id, Long bookId, String userEmail, Double amount, LocalDateTime timestamp) {
        this.id = id;
        this.bookId = bookId;
        this.userEmail = userEmail;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Constructor sin id para crear antes de persistir
    public Payment(Long bookId, String userEmail, Double amount, LocalDateTime timestamp) {
        this.bookId = bookId;
        this.userEmail = userEmail;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
