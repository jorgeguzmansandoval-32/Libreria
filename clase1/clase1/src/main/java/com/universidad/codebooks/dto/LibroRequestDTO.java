package com.universidad.codebooks.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor



public class LibroRequestDTO {
    
    //No tiene ID (Mysql lo genera)
    //Cliente no debe poder enviarlo

    @NotBlank(message = "Titulo no puede estar vacio")
    private String titulo;

    @NotBlank(message =  "El ISBN no puede estar vacio")
    private String isbn;

    @NotNull (message = "El precio es obligatorio")
    @Positive(message =  "El precio debe ser mayor a 0")
    private BigDecimal precio;


    //Solo el id de la catergoria
    //El servicio busca el objeto categoria en el BD usando este ID

    @NotNull (message = "El categoriaID es obligatoria")
    private long categoriaId;
    





}
