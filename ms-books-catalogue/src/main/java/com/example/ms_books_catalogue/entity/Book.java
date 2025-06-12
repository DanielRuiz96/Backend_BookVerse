package com.example.ms_books_catalogue.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @NotBlank(message = "El autor no puede estar vacío")
    private String autor;

    @PastOrPresent(message = "La fecha de publicación no puede ser futura")
    @Column(name = "fecha_publicacion")  // mapeo explícito al nombre de columna
    private LocalDate fechaPublicacion;

    @NotBlank(message = "La categoría no puede estar vacía")
    private String categoria;

    @Pattern(regexp = "\\d{13}", message = "El ISBN debe contener exactamente 13 dígitos")
    @Column(unique = true)
    private String isbn;

    @Min(value = 1, message = "La valoración mínima es 1")
    @Max(value = 5, message = "La valoración máxima es 5")
    private Integer valoracion;

    @NotNull(message = "El campo 'visible' no puede ser nulo")
    private Boolean visible;

    @NotNull(message = "El precio no puede ser nulo")
    private Double precio; 

    @Min(0)
    @NotNull(message = "El stock no puede ser nulo")
    private Integer stock;
}
