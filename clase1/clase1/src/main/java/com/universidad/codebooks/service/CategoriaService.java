package com.universidad.codebooks.service;

import com.universidad.codebooks.model.Categoria;
import com.universidad.codebooks.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// ═══════════════════════════════════════════════════
// CLASE 1 · CategoriaService.java
// Lógica de negocio. Intermediario entre Controller
// y Repository. Nunca accede a la BD directamente.
// ═══════════════════════════════════════════════════

// @RequiredArgsConstructor (Lombok): genera el constructor
//   con todos los campos 'final'. Reemplaza el constructor
//   manual de inyección de dependencias.
// @Service: registra esta clase en el contenedor Spring
//   como componente de lógica de negocio.
@Service
@RequiredArgsConstructor
public class CategoriaService {

    // 'final' + @RequiredArgsConstructor → Spring inyecta
    // automáticamente el repositorio por constructor.
    private final CategoriaRepository categoriaRepository;

    // SELECT * FROM categorias
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    // SELECT * FROM categorias WHERE id = ?
    // Devuelve Optional: el Controller decide si hay 404.
    public Optional<Categoria> obtenerPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    // INSERT o UPDATE según si tiene id o no
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // DELETE WHERE id = ?
    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}
