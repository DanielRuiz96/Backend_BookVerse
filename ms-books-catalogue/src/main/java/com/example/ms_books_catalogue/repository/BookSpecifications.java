package com.example.ms_books_catalogue.repository;

import com.example.ms_books_catalogue.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BookSpecifications {

    public static Specification<Book> tituloContiene(String titulo) {
        return (root, query, builder) -> {
            if (titulo == null || titulo.isBlank()) {
                return builder.conjunction();
            }
            return builder.like(builder.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%");
        };
    }

    public static Specification<Book> autorContiene(String autor) {
        return (root, query, builder) -> {
            if (autor == null || autor.isBlank()) {
                return builder.conjunction();
            }
            return builder.like(builder.lower(root.get("autor")), "%" + autor.toLowerCase() + "%");
        };
    }

    public static Specification<Book> categoriaEs(String categoria) {
        return (root, query, builder) -> {
            if (categoria == null || categoria.isBlank()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("categoria"), categoria);
        };
    }

    public static Specification<Book> isbnEs(String isbn) {
        return (root, query, builder) -> {
            if (isbn == null || isbn.isBlank()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("isbn"), isbn);
        };
    }

    public static Specification<Book> valoracionEs(Integer valoracion) {
        return (root, query, builder) -> {
            if (valoracion == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("valoracion"), valoracion);
        };
    }

    public static Specification<Book> visibleEs(Boolean visible) {
        return (root, query, builder) -> {
            if (visible == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("visible"), visible);
        };
    }

    public static Specification<Book> fechaPublicacionEntre(LocalDate desde, LocalDate hasta) {
        return (root, query, builder) -> {
            if (desde == null && hasta == null) {
                return builder.conjunction();
            }
            if (desde != null && hasta != null) {
                return builder.between(root.get("fechaPublicacion"), desde, hasta);
            }
            if (desde != null) {
                return builder.greaterThanOrEqualTo(root.get("fechaPublicacion"), desde);
            }
            return builder.lessThanOrEqualTo(root.get("fechaPublicacion"), hasta);
        };
    }

    public static Specification<Book> stockMayorA(Integer minimoStock) {
        return (root, query, builder) -> {
            if (minimoStock == null) return null;
            return builder.greaterThanOrEqualTo(root.get("stock"), minimoStock);
        };
    }

    public static Specification<Book> precioMayorOIgual(Double precioMinimo) {
        return (root, query, builder) -> {
            if (precioMinimo == null) return null;
            return builder.greaterThanOrEqualTo(root.get("precio"), precioMinimo);
        };
    }
    
    public static Specification<Book> precioMenorOIgual(Double precioMaximo) {
        return (root, query, builder) -> {
            if (precioMaximo == null) return null;
            return builder.lessThanOrEqualTo(root.get("precio"), precioMaximo);
        };
    }
    
    
}
