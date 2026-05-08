package com.universidad.codebooks.service;

import com.universidad.codebooks.dto.LibroResponseDTO;
import com.universidad.codebooks.model.Categoria;
import com.universidad.codebooks.model.Libro;
import com.universidad.codebooks.repository.CategoriaRepository;
import com.universidad.codebooks.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// ═══════════════════════════════════════════════════
// CLASE 1 · LibroService.java
// ═══════════════════════════════════════════════════

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;

    
    //Necesario para buscar la categoria por ID
    //Cunaod el DTO trae solo la categoriaId
    private final CategoriaRepository categoriaRepository;

    //Mappeo : Enitdad -> RespondeDTO
    //Solo lo usa este Service nadie mas. El controller y el respository nunca conocen el DTO ni la identidad del otro.
    private LibroResponseDTO mapToDTO(Libro libro){
        return new LibroResponseDTO(
            libro.getId(),
            libro.getTitulo(),
            libro.getIsbn(),
            libro.getPrecio(),
            libro.getCategoria().getNombre()
        );



    }


   //Nuevo para buscar con el nombre categoria
    public List<LibroResponseDTO> obtenerTodos() { 
        return libroRepository.findAll()
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());        
    }

    public Optional<LibroResponseDTO> obtenerPorId(Long id) { 
        return libroRepository.findById(id).map(this::mapToDTO); 
    }


    public LibroResponseDTO guardar(LibroResponseDTO libroDTO) {
        Optional<Categoria> categoria = categoriaRepository.findById(libroDTO.getCategoriaId());
        
        Libro libro = new Libro(
            id:null,
            libroDTO.getTitulo(),
            libroDTO.getIsbn(),
            libroDTO.getIsbn(),
            libroDTO.getPrecio(),
            categoria
        );
        return libroRepository.save(libro); 
    }


    public void eliminar(Long id) { 
        libroRepository.deleteById(id); 
    }

    // ── NUEVOS MÉTODOS CLASE 2 ───────────────────────
    // El Service delega al Repository. Encapsula la llamada
    // para que el Controller no conozca los detalles del repo.

    // Usa Query Method
    public List<Libro> buscarPorTitulo(String texto) {
        return libroRepository.findByTituloContainingIgnoreCase(texto);
    }

    // Usa @Query JPQL
    public List<Libro> buscarPorCategoria(Long categoriaId) {
        return libroRepository.findByCategoriaId(categoriaId);
    }

    // Usa @Query JPQL con precio máximo
    public List<Libro> buscarBajoPresupuesto(Double precioMax) {
        return libroRepository.findLibrosBajoPresupuesto(precioMax);
    }

    // Usa SQL nativo
    public List<Libro> buscarPorTituloNativo(String texto) {
        return libroRepository.buscarPorTituloNativo(texto);
    }
}
