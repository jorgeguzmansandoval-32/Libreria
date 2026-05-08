package com.universidad.codebooks.controller;

import com.universidad.codebooks.model.Libro;
import com.universidad.codebooks.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ═══════════════════════════════════════════════════
// CLASE 1 · LibroController.java
// Endpoints REST para el recurso Libro.
// ═══════════════════════════════════════════════════

// NOTA CLASE 1: el POST recibe el objeto Categoria completo
// en el JSON (con su id). En Clase 3 mejoramos con DTOs.
// Ejemplo body POST:
//   { "titulo":"Clean Code", "isbn":"978-01", "precio":45.99,
//     "categoria": { "id": 1 } }
@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    // GET /api/libros → 200 OK con lista completa
    @GetMapping
    public ResponseEntity<List<Libro>> obtenerTodos() {
        return ResponseEntity.ok(libroService.obtenerTodos());
    }

    // GET /api/libros/{id} → 200 OK o 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerPorId(@PathVariable Long id) {
        return libroService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/libros → 201 Created con el libro guardado
    @PostMapping
    public ResponseEntity<Libro> crear(@Valid @RequestBody Libro libro) {
        return ResponseEntity.status(201).body(libroService.guardar(libro));
    }

    // PUT /api/libros/{id} → 200 OK o 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Libro datos) {
        return libroService.obtenerPorId(id)
                .map(existente -> {
                    datos.setId(id);
                    return ResponseEntity.ok(libroService.guardar(datos));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/libros/{id} → 204 No Content o 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (libroService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
