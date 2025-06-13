package com.example.ms_books_catalogue.controller;

import com.example.ms_books_catalogue.entity.Book;
import com.example.ms_books_catalogue.exception.BookNotFoundException;
import com.example.ms_books_catalogue.repository.BookRepository;
import com.example.ms_books_catalogue.repository.BookSpecifications;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/books")
@CrossOrigin
public class BookController {

    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }

    /* ---------- CRUD básico ---------- */

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Book book = repo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Libro encontrado");
        response.put("libro", book);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Book b) {
        Book saved = repo.save(b);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Libro creado exitosamente");
        response.put("libro", saved);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Book b) {
        if (!repo.existsById(id)) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Libro no encontrado con id: " + id);
            return ResponseEntity.status(404).body(error);
        }
        b.setId(id);
        Book updated = repo.save(b);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Libro actualizado exitosamente");
        response.put("libro", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        repo.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Libro eliminado exitosamente");
        return ResponseEntity.ok(response);
    }

    /* ---------- Búsqueda dinámica ---------- */

    @GetMapping
    public ResponseEntity<?> search(
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
            @RequestParam(required = false) Double precioMaximo) {

        Specification<Book> spec = Specification.where(BookSpecifications.tituloContiene(titulo))
                .and(BookSpecifications.autorContiene(autor))
                .and(BookSpecifications.categoriaEs(categoria))
                .and(BookSpecifications.isbnEs(isbn))
                .and(BookSpecifications.valoracionEs(valoracion))
                .and(BookSpecifications.visibleEs(visible))
                .and(BookSpecifications.fechaPublicacionEntre(fechaPublicacionDesde, fechaPublicacionHasta))
                .and(BookSpecifications.stockMayorA(stockMinimo))
                .and(BookSpecifications.precioMayorOIgual(precioMinimo))
                .and(BookSpecifications.precioMenorOIgual(precioMaximo));

        List<Book> resultado = repo.findAll(spec);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Consulta realizada exitosamente");
        response.put("total", resultado.size());
        response.put("libros", resultado);
        return ResponseEntity.ok(response);
    }

    /* ---------- Actualizar stock ---------- */

    @PutMapping("/{id}/stock")
    public ResponseEntity<?> discountStock(@PathVariable Long id, @RequestParam Integer cantidadComprada) {
        if (cantidadComprada == null || cantidadComprada <= 0) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "La cantidad comprada debe ser mayor a cero");
            return ResponseEntity.badRequest().body(error);
        }

        Book book = repo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        int nuevoStock = (book.getStock() != null ? book.getStock() : 0) - cantidadComprada;
        if (nuevoStock < 0) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", "Stock insuficiente para el libro con id: " + id);
            return ResponseEntity.badRequest().body(error);
        }
        book.setStock(nuevoStock);
        Book updatedBook = repo.save(book);
        return ResponseEntity.ok(updatedBook);
    }

}
