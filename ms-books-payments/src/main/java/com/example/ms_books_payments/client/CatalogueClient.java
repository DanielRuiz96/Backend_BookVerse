package com.example.ms_books_payments.client;

import com.example.ms_books_payments.dto.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-books-catalogue")
public interface CatalogueClient {

    @GetMapping("/books/{id}")
    BookResponse getBookById(@PathVariable("id") Long id);

    @PutMapping("/books/{id}/stock")
    BookResponse discountStock(@PathVariable("id") Long id, @RequestParam("cantidadComprada") Integer cantidadComprada);


}
