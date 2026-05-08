package com.universidad.codebooks.repository;

import com.universidad.codebooks.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

// ═══════════════════════════════════════════════════
// CLASE 1 · CategoriaRepository.java
// Acceso a datos. JpaRepository provee CRUD completo
// sin escribir SQL ni código de implementación.
// ═══════════════════════════════════════════════════

// JpaRepository<Entidad, TipoPK> hereda:
//   save()        → INSERT o UPDATE automático
//   findById()    → SELECT WHERE id = ?
//   findAll()     → SELECT * FROM categorias
//   deleteById()  → DELETE WHERE id = ?
//   count()       → SELECT COUNT(*)
//   existsById()  → comprueba si existe un id
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // CLASE 1: vacío. Los métodos heredados son suficientes.
    // En Clase 2 agregaremos consultas personalizadas.
}
