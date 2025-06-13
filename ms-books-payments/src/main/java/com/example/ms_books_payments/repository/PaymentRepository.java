package com.example.ms_books_payments.repository;

import com.example.ms_books_payments.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByEmailUsuario(String emailUsuario);
}
