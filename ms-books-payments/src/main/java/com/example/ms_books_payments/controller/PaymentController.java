package com.example.ms_books_payments.controller;

import com.example.ms_books_payments.entity.Payment;
import com.example.ms_books_payments.service.PaymentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Tu endpoint actual para registrar pagos
    @PostMapping
    public ResponseEntity<Payment> makePayment(@RequestParam Long bookId,
                                               @RequestParam String buyer,
                                               @RequestParam Integer cantidad) {
        Payment payment = paymentService.registerPayment(bookId, buyer, cantidad);
        return ResponseEntity.ok(payment);
    }

    // endpoint para obtener todos los pagos
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    // Nuevo endpoint para obtener pagos por email
    @GetMapping("/user")
    public ResponseEntity<List<Payment>> getPaymentsByUser(@RequestParam String email) {
        List<Payment> payments = paymentService.getPaymentsByUserEmail(email);
        return ResponseEntity.ok(payments);
    }
}
