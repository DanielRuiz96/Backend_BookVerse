package com.example.ms_books_payments.controller;

import com.example.ms_books_payments.dto.PaymentWithPrice;
import com.example.ms_books_payments.dto.PurchaseSummary;
import com.example.ms_books_payments.entity.Payment;
import com.example.ms_books_payments.service.PaymentService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void testGetAllPurchaseSummaries() throws Exception {
        // Arrange: crear dos pagos con precio
        PaymentWithPrice pago1 = new PaymentWithPrice(1L, "daniel.ruiz@unir.com", 4L, 2, 22.0);
        pago1.setSubtotal(44.0);

        PaymentWithPrice pago2 = new PaymentWithPrice(2L, "daniel.ruiz@unir.com", 3L, 1, 29.99);
        pago2.setSubtotal(29.99);

        List<PaymentWithPrice> listaPagos = Arrays.asList(pago1, pago2);

        PurchaseSummary resumen = new PurchaseSummary(
                "24defa7b-c5b9-4095-bf9d-7355eac1e0b3",
                "daniel.ruiz@unir.com",
                LocalDateTime.of(2025, 6, 13, 1, 8, 48),
                listaPagos
        );

        when(paymentService.getAllPurchaseSummaries()).thenReturn(List.of(resumen));

        // Act & Assert
        mockMvc.perform(get("/payments").accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].purchaseId").value("24defa7b-c5b9-4095-bf9d-7355eac1e0b3"))
            .andExpect(jsonPath("$[0].emailUsuario").value("daniel.ruiz@unir.com"))
            .andExpect(jsonPath("$[0].pagos[0].bookId").value(4))
            .andExpect(jsonPath("$[0].pagos[0].subtotal").value(44.0))
            .andExpect(jsonPath("$[0].totalCompra").value(73.99));
    }

    @Test
    public void testMakePayment() throws Exception {
        // JSON de entrada simulado (como el que envías desde Postman)
        String requestBody = """
            {
                "emailUsuario": "daniel.ruiz@unir.com",
                "items": [
                    { "bookId": 1, "cantidad": 2 },
                    { "bookId": 2, "cantidad": 1 }
                ]
            }
        """;

        // Simulación de pagos retornados por el servicio
        Payment pago1 = new Payment();
        pago1.setId(3L);
        pago1.setEmailUsuario("daniel.ruiz@unir.com");
        pago1.setBookId(1L);
        pago1.setCantidad(2);
        pago1.setFechaCompra(LocalDateTime.of(2025, 6, 22, 15, 6, 16));
        pago1.setPurchaseId("862b1035-ae97-4eb7-bac4-d2d152ed6fb8");

        Payment pago2 = new Payment();
        pago2.setId(4L);
        pago2.setEmailUsuario("daniel.ruiz@unir.com");
        pago2.setBookId(2L);
        pago2.setCantidad(1);
        pago2.setFechaCompra(LocalDateTime.of(2025, 6, 22, 15, 6, 16));
        pago2.setPurchaseId("862b1035-ae97-4eb7-bac4-d2d152ed6fb8");

        when(paymentService.registerPayments(any())).thenReturn(Arrays.asList(pago1, pago2));

        // Ejecuta y verifica
        mockMvc.perform(post("/payments")
                .contentType(APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(3))
            .andExpect(jsonPath("$[0].emailUsuario").value("daniel.ruiz@unir.com"))
            .andExpect(jsonPath("$[0].bookId").value(1))
            .andExpect(jsonPath("$[1].id").value(4))
            .andExpect(jsonPath("$[1].bookId").value(2));
    }

}
