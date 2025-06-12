package com.example.ms_books_payments.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.ms_books_payments.client.CatalogueClient;
import com.example.ms_books_payments.dto.BookResponse;
import com.example.ms_books_payments.entity.Payment;
import com.example.ms_books_payments.repository.PaymentRepository;

import java.util.List;


@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CatalogueClient catalogueClient;

    public PaymentService(PaymentRepository paymentRepository, CatalogueClient catalogueClient) {
        this.paymentRepository = paymentRepository;
        this.catalogueClient = catalogueClient;
    }

    public Payment registerPayment(Long bookId, String buyer, Integer cantidad) {
        BookResponse book = catalogueClient.getBookById(bookId);
        
        if (book == null || !book.getVisible()) {
            throw new RuntimeException("El libro no está disponible para la venta.");
        }

        if (book.getStock() < cantidad) {
            throw new RuntimeException("No hay suficiente stock.");
        }

        // Descontar stock antes de guardar el pago
        BookResponse updatedBook = catalogueClient.discountStock(bookId, cantidad);
        if (updatedBook.getStock() < 0) {
            throw new RuntimeException("Error al descontar stock.");
        }

        Double total = book.getPrecio() * cantidad;

        Payment payment = new Payment();
        payment.setBookId(bookId);
        payment.setUserEmail(buyer);
        payment.setAmount(total);
        payment.setTimestamp(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    // método para obtener todos los pagos
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // método para obtener pagos por email de usuario
    public List<Payment> getPaymentsByUserEmail(String email) {
        return paymentRepository.findByUserEmail(email);
    }
}
