package com.universidad.codebooks.config;

import com.universidad.codebooks.model.Categoria;
import com.universidad.codebooks.model.Libro;
import com.universidad.codebooks.repository.CategoriaRepository;
import com.universidad.codebooks.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// ═══════════════════════════════════════════════════
// CLASE 1 · DataInitializer.java
// Inserta datos de prueba al arrancar la aplicación.
//
// ESTRATEGIA "insertar una sola vez":
//   Antes de insertar, preguntamos cuántos registros
//   hay en la BD. Si ya hay datos (count > 0), no
//   hacemos nada. Así:
//     - Al borrar manualmente datos en phpMyAdmin
//       NO se vuelven a insertar (conteo > 0 si hay
//       otros datos, o hay que reiniciar con BD vacía).
//     - Si la BD está completamente vacía (nuevo despliegue
//       o BD recreada), se insertan automáticamente.
//   Esta es la forma más simple y directa para clase.
// ═══════════════════════════════════════════════════

// @Slf4j (Lombok): genera automáticamente un logger
//   'log' para usar log.info(), log.warn(), etc.
//   Sin Lombok sería: private static final Logger log = ...
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    // CommandLineRunner obliga a implementar run().
    // Spring lo ejecuta automáticamente después de
    // que el contexto y la BD están listos.

    private final CategoriaRepository categoriaRepository;
    private final LibroRepository libroRepository;

    @Override
    public void run(String... args) {

        // ── GUARDIA PRINCIPAL ────────────────────────
        // Si ya existen categorías en la BD, no hacemos nada.
        // Esto evita duplicar datos en cada reinicio del servidor.
        // Si un estudiante borra una categoría desde phpMyAdmin
        // pero quedan otras, el inicializador NO actúa.
        // Para "resetear", vaciar la BD manualmente y reiniciar.
        if (categoriaRepository.count() > 0) {
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial.");
            return;
        }

        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");

        // ── CATEGORÍAS ───────────────────────────────
        // save() devuelve el objeto con el id asignado por MySQL.
        // Guardamos la referencia para usarla en los libros.
        Categoria prog = categoriaRepository.save(
                new Categoria(null, "Programación", "Libros de lenguajes y frameworks"));
        Categoria bd = categoriaRepository.save(
                new Categoria(null, "Bases de Datos", "SQL, NoSQL y diseño de datos"));

        // ── LIBROS ───────────────────────────────────
        // Asociamos cada libro a la categoría creada arriba.
        // El campo 'id' va null porque MySQL lo genera (AUTO_INCREMENT).
        // libroRepository.save(new Libro(1, "Clean Code","978-01", 45.99, prog));
        // libroRepository.save(new Libro(2, "Spring Boot in Action","978-02", 52.00,
        // prog));
        // libroRepository.save(new Libro(3, "MySQL Avanzado","978-03", 38.50, bd));
        // libroRepository.save(new Libro(4, "NoSQL Distilled","978-04", 29.99, bd));
        libroRepository.save(new Libro(null, "Clean Code", "978-01", new java.math.BigDecimal("45.99"), prog));
        libroRepository
                .save(new Libro(null, "Spring Boot in Action", "978-02", new java.math.BigDecimal("52.00"), prog));
        libroRepository.save(new Libro(null, "MySQL Avanzado", "978-03", new java.math.BigDecimal("38.50"), bd));
        libroRepository.save(new Libro(null, "NoSQL Distilled", "978-04", new java.math.BigDecimal("29.99"), bd));
        log.info(">>> DataInitializer: {} categorías y {} libros insertados correctamente.",
                categoriaRepository.count(), libroRepository.count());
    }
}
