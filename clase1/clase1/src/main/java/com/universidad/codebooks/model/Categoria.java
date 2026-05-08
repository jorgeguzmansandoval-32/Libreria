package com.universidad.codebooks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ═══════════════════════════════════════════════════
// CLASE 1 · Categoria.java
// Entidad JPA. Hibernate la convierte en la tabla
// "categorias" de MySQL automáticamente.
// ═══════════════════════════════════════════════════

// ── ANOTACIONES LOMBOK ───────────────────────────────
// @Data: genera automáticamente getters, setters,
//   toString(), equals() y hashCode(). Equivale a
//   escribir ~40 líneas de código repetitivo a mano.
// @NoArgsConstructor: genera el constructor vacío.
//   OBLIGATORIO para JPA: Hibernate lo necesita para
//   reconstruir objetos al leer filas de la BD.
// @AllArgsConstructor: genera constructor con todos
//   los campos. Útil para crear objetos en código.
@Data
@NoArgsConstructor
@AllArgsConstructor

// ── ANOTACIONES JPA ─────────────────────────────────
// @Entity: le dice a Hibernate que esta clase es una tabla.
// @Table: define el nombre exacto de la tabla en MySQL.
@Entity
@Table(name = "categorias")
public class Categoria {

    // @Id       → PRIMARY KEY de la tabla
    // @GeneratedValue → AUTO_INCREMENT de MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank (Validation): rechaza null, "" y "   "
    //   Se activa cuando el Controller usa @Valid.
    //   Spring devuelve 400 Bad Request automáticamente.
    // @Size: limita la longitud del texto.
    // @Column: aplica la restricción NOT NULL en MySQL.
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
    @Column(length = 255)
    private String descripcion;
}
