package com.julian.finanzasapp.category.dto;

import com.julian.finanzasapp.category.TipoCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoriaRequest {

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombre;

    @NotNull(message = "El tipo de categoría es obligatorio")
    private TipoCategoria tipo;

    private String icono;
}
