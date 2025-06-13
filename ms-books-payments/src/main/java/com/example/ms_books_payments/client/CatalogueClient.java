package com.example.ms_books_payments.client;

import com.example.ms_books_payments.dto.BookResponseWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-books-catalogue")
public interface CatalogueClient {

    @GetMapping("/books/{id}")
    BookResponseWrapper getBookById(@PathVariable("id") Long id);

    @PutMapping("/books/{id}/stock")
    BookResponseWrapper discountStock(@PathVariable("id") Long id, @RequestParam("cantidadComprada") Integer cantidadComprada);
}
