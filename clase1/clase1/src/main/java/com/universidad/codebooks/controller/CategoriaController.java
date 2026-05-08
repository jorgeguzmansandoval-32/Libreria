package com.universidad.codebooks.controller;

import com.universidad.codebooks.model.Categoria;
import com.universidad.codebooks.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ═══════════════════════════════════════════════════
// CLASE 1 · CategoriaController.java
// Capa web: recibe HTTP, delega al Service, responde JSON.
// Usa ResponseEntity para controlar códigos HTTP exactos.
// ═══════════════════════════════════════════════════

// @RestController = @Controller + @ResponseBody
//   Todos los métodos retornan JSON automáticamente.
// @RequestMapping: ruta base /api/categorias para todos.
// @RequiredArgsConstructor: Lombok inyecta el Service.
@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    // ── GET /api/categorias ──────────────────────────
    // 200 OK con lista de categorías (puede ser vacía []).
    // ResponseEntity<List<...>> permite controlar el código.
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodas() {
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }

    // ── GET /api/categorias/{id} ─────────────────────
    // 200 OK si existe, 404 Not Found si no.
    // @PathVariable extrae el {id} de la URL.
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)                   // 200 con el objeto
                .orElse(ResponseEntity.notFound().build()); // 404 sin cuerpo
    }

    // ── POST /api/categorias ─────────────────────────
    // 201 Created con el objeto recién guardado (incluye el id generado).
    // @Valid activa las validaciones de la entidad (@NotBlank, @Size, etc.)
    // Si falla la validación → Spring devuelve 400 Bad Request automáticamente.
    @PostMapping
    public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria categoria) {
        Categoria nueva = categoriaService.guardar(categoria);
        return ResponseEntity.status(201).body(nueva);
    }

    // ── PUT /api/categorias/{id} ─────────────────────
    // 200 OK con el objeto actualizado, o 404 si no existe el id.
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Categoria datos) {
        return categoriaService.obtenerPorId(id)
                .map(existente -> {
                    datos.setId(id); // aseguramos que se actualice el correcto
                    return ResponseEntity.ok(categoriaService.guardar(datos));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ── DELETE /api/categorias/{id} ──────────────────
    // 204 No Content: éxito sin cuerpo de respuesta.
    // 404 si el id no existe en la BD.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (categoriaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
