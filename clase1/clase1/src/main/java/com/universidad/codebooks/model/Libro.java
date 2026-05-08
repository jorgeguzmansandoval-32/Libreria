package com.universidad.codebooks.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ═══════════════════════════════════════════════════
// CLASE 1 · Libro.java
// Segunda entidad. Introduce la relación @ManyToOne
// con Categoria → FOREIGN KEY en MySQL.
// ═══════════════════════════════════════════════════

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 200, message = "El título no puede superar 200 caracteres")
    @Column(nullable = false, length = 200)
    private String titulo;

    // unique = true → índice UNIQUE en MySQL (no puede haber ISBN repetidos)
    @NotBlank(message = "El ISBN no puede estar vacío")
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    // @Positive: el precio debe ser > 0
    // DECIMAL(10,2) en MySQL: hasta 99,999,999.99
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    // ── LLAVE FORÁNEA ────────────────────────────────
    // @ManyToOne: muchos libros → una categoría.
    // @JoinColumn: nombre de la columna FK en "libros".
    // Hibernate genera: categoria_id INT NOT NULL,
    //   FOREIGN KEY (categoria_id) REFERENCES categorias(id)
    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
