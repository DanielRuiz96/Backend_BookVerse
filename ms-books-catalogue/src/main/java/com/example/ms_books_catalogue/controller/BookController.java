package com.example.ms_books_catalogue.controller;

import com.example.ms_books_catalogue.entity.Book;
import com.example.ms_books_catalogue.exception.BookNotFoundException;
import com.example.ms_books_catalogue.repository.BookRepository;
import com.example.ms_books_catalogue.repository.BookSpecifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
@CrossOrigin // permite llamadas desde frontend React
public class BookController {

    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }

    /* ---------- CRUD básico ---------- */

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @PostMapping
    public Book create(@Valid @RequestBody Book b) {
        return repo.save(b);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody Book b) {
        b.setId(id);
        return repo.save(b);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    /* ---------- Búsqueda dinámica ---------- */

    @GetMapping
    public List<Book> search(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer valoracion,
            @RequestParam(required = false) Boolean visible,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPublicacionDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPublicacionHasta,
            @RequestParam(required = false) Integer stockMinimo,
            @RequestParam(required = false) Double precioMinimo,
            @RequestParam(required = false) Double precioMaximo) 
            {

        Specification<Book> spec = Specification.where(BookSpecifications.tituloContiene(titulo))
                .and(BookSpecifications.autorContiene(autor))
                .and(BookSpecifications.categoriaEs(categoria))
                .and(BookSpecifications.isbnEs(isbn))
                .and(BookSpecifications.valoracionEs(valoracion))
                .and(BookSpecifications.visibleEs(visible))
                .and(BookSpecifications.fechaPublicacionEntre(fechaPublicacionDesde, fechaPublicacionHasta))
                .and(BookSpecifications.precioMayorOIgual(precioMinimo))
                .and(BookSpecifications.precioMenorOIgual(precioMaximo));

        return repo.findAll(spec);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Book> discountStock(@PathVariable Long id, @RequestParam Integer cantidadComprada) {
        Book book = repo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        int nuevoStock = (book.getStock() != null ? book.getStock() : 0) - cantidadComprada;
        if (nuevoStock < 0) {
            return ResponseEntity.badRequest().body(null);
        }
        book.setStock(nuevoStock);
        repo.save(book);
        return ResponseEntity.ok(book);
    }

}
