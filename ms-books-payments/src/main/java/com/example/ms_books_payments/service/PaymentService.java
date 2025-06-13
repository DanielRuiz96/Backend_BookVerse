package com.example.ms_books_payments.service;

import com.example.ms_books_payments.client.CatalogueClient;
import com.example.ms_books_payments.dto.BookResponseWrapper;
import com.example.ms_books_payments.dto.PaymentItem;
import com.example.ms_books_payments.dto.PaymentRequest;
import com.example.ms_books_payments.dto.PaymentWithPrice;
import com.example.ms_books_payments.dto.PurchaseSummary;
import com.example.ms_books_payments.entity.Payment;
import com.example.ms_books_payments.repository.PaymentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final CatalogueClient catalogueClient;

    public PaymentService(PaymentRepository paymentRepository, CatalogueClient catalogueClient) {
        this.paymentRepository = paymentRepository;
        this.catalogueClient = catalogueClient;
    }

    @Transactional
    public List<Payment> registerPayments(PaymentRequest paymentRequest) {
        List<Payment> pagosRealizados = new ArrayList<>();

        String purchaseId = UUID.randomUUID().toString();
        LocalDateTime fechaCompra = LocalDateTime.now();

        for (PaymentItem item : paymentRequest.getItems()) {
            BookResponseWrapper respuesta = catalogueClient.getBookById(item.getBookId());
            if (respuesta == null || respuesta.getLibro() == null) {
                throw new RuntimeException("Libro no encontrado con id: " + item.getBookId());
            }

            try {
                BookResponseWrapper stockActualizado = catalogueClient.discountStock(item.getBookId(), item.getCantidad());
                if (stockActualizado == null) {
                    logger.warn("Respuesta nula del servicio al actualizar stock para libro ID {}", item.getBookId());
                } else if (stockActualizado.getLibro() == null) {
                    logger.warn("Stock actualizado, pero el campo 'libro' es nulo para libro ID {}", item.getBookId());
                } else {
                    logger.info("Stock actualizado para libro ID {}: {}", item.getBookId(), stockActualizado.getLibro().getStock());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error al descontar stock para el libro con ID " + item.getBookId() + ": " + e.getMessage());
            }

            Payment pago = new Payment();
            pago.setEmailUsuario(paymentRequest.getEmailUsuario());
            pago.setBookId(item.getBookId());
            pago.setCantidad(item.getCantidad());
            pago.setPurchaseId(purchaseId);
            pago.setFechaCompra(fechaCompra);

            pagosRealizados.add(paymentRepository.save(pago));
        }

        return pagosRealizados;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByUserEmail(String email) {
        return paymentRepository.findByEmailUsuario(email);
    }

    public List<PaymentWithPrice> getAllPaymentsWithPrice() {
        List<Payment> pagos = paymentRepository.findAll();
        return mapPaymentsToPaymentWithPrice(pagos);
    }

    public List<PaymentWithPrice> getPaymentsWithPriceByUserEmail(String email) {
        List<Payment> pagos = paymentRepository.findByEmailUsuario(email);
        return mapPaymentsToPaymentWithPrice(pagos);
    }

    public List<PurchaseSummary> getAllPurchaseSummaries() {
        List<Payment> pagos = paymentRepository.findAll();
        return agruparPagosPorCompra(pagos);
    }

    public List<PurchaseSummary> getPurchaseSummariesByUser(String email) {
        List<Payment> pagos = paymentRepository.findByEmailUsuario(email);
        return agruparPagosPorCompra(pagos);
    }

    private List<PurchaseSummary> agruparPagosPorCompra(List<Payment> pagos) {
        Map<String, List<Payment>> pagosAgrupados = pagos.stream()
            .collect(Collectors.groupingBy(Payment::getPurchaseId));

        List<PurchaseSummary> resumenes = new ArrayList<>();

        for (List<Payment> grupo : pagosAgrupados.values()) {
            if (grupo.isEmpty()) continue;

            Payment primero = grupo.get(0);
            List<PaymentWithPrice> pagosConPrecio = mapPaymentsToPaymentWithPrice(grupo);

            resumenes.add(new PurchaseSummary(
                primero.getPurchaseId(),
                primero.getEmailUsuario(),
                primero.getFechaCompra(),
                pagosConPrecio
            ));
        }

        return resumenes;
    }

    private List<PaymentWithPrice> mapPaymentsToPaymentWithPrice(List<Payment> pagos) {
        List<PaymentWithPrice> result = new ArrayList<>();

        for (Payment pago : pagos) {
            BookResponseWrapper libroResponse = catalogueClient.getBookById(pago.getBookId());
            if (libroResponse == null || libroResponse.getLibro() == null) {
                logger.warn("No se encontró libro para el pago con bookId {}", pago.getBookId());
                continue;
            }

            double precioUnitario = libroResponse.getLibro().getPrecio();
            double subtotal = precioUnitario * pago.getCantidad();

            PaymentWithPrice dto = new PaymentWithPrice();
            dto.setId(pago.getId());
            dto.setEmailUsuario(pago.getEmailUsuario());
            dto.setBookId(pago.getBookId());
            dto.setCantidad(pago.getCantidad());
            dto.setPrecioUnitario(precioUnitario);
            dto.setSubtotal(subtotal);

            result.add(dto);
        }

        return result;
    }
}
