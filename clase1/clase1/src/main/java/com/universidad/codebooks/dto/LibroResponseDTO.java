package com.universidad.codebooks.dto;
//SIN ANOTACIONES DE VALIDACIONES

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LibroResponseDTO {

    private Long id;
    private String titulo;
    private String isbn;
    private BigDecimal precio;
    
    //Solo el nombre de la categoria, no el objeto completi
    private String categoriaNombre;





}
