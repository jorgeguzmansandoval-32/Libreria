package com.universidad.codebooks.repository;

import com.universidad.codebooks.model.Libro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// ═══════════════════════════════════════════════════
// CLASE 1 · LibroRepository.java
// ═══════════════════════════════════════════════════

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // ── TIPO 1: QUERY METHODS ────────────────────────
    // Spring analiza el nombre del método y genera SQL.
    // Atributo debe coincidir EXACTAMENTE con el campo
    // de la entidad Java (mayúsculas incluidas).

    // → SELECT * FROM libros WHERE UPPER(titulo) LIKE UPPER('%?%')
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    // → SELECT * FROM libros WHERE precio < ?
    List<Libro> findByPrecioLessThan(Double precio);

    // → SELECT * FROM libros WHERE precio BETWEEN ? AND ?
    List<Libro> findByPrecioBetween(Double min, Double max);


    // ── TIPO 2: @QUERY CON JPQL ──────────────────────
    // Escrito sobre entidades Java, NO sobre tablas SQL.
    // "Libro" = clase Java,  "l.categoria.id" = atributo.
    // Hibernate lo traduce al SQL correcto según el dialecto.
    // :param es parámetro nombrado, @Param lo enlaza.
    @Query("SELECT l FROM Libro l WHERE l.categoria.id = :categoriaId")
    List<Libro> findByCategoriaId(@Param("categoriaId") Long categoriaId);

    // JPQL con ORDER BY
    @Query("SELECT l FROM Libro l WHERE l.precio <= :precioMax ORDER BY l.precio DESC")
    List<Libro> findLibrosBajoPresupuesto(@Param("precioMax") Double precioMax);


    // ── TIPO 3: SQL NATIVO ───────────────────────────
    // nativeQuery=true: Hibernate manda el SQL tal cual a MySQL.
    // Usar para funciones específicas de MySQL (CONCAT, DATE_FORMAT...)
    // o consultas muy optimizadas que no deben ser reescritas.
    @Query(
        value = "SELECT * FROM libros WHERE titulo LIKE CONCAT('%', :texto, '%')",
        nativeQuery = true
    )
    List<Libro> buscarPorTituloNativo(@Param("texto") String texto);
}
