package com.example.ms_books_payments.controller;

import com.example.ms_books_payments.dto.PaymentRequest;
import com.example.ms_books_payments.dto.PurchaseSummary;
import com.example.ms_books_payments.entity.Payment;
import com.example.ms_books_payments.service.PaymentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<List<Payment>> makePayment(@RequestBody PaymentRequest paymentRequest) {
        List<Payment> pagos = paymentService.registerPayments(paymentRequest);
        return ResponseEntity.ok(pagos);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseSummary>> getPayments(@RequestParam(required = false) String email) {
        List<PurchaseSummary> resumen;
        if (email != null && !email.isEmpty()) {
            resumen = paymentService.getPurchaseSummariesByUser(email);
        } else {
            resumen = paymentService.getAllPurchaseSummaries();
        }
        return ResponseEntity.ok(resumen);
}



}
