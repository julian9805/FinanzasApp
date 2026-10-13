package com.julian.finanzasapp.category.dto;

import com.julian.finanzasapp.category.TipoCategoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponse {
    private Long id;
    private String nombre;
    private TipoCategoria tipo;
    private String icono;
    private Long usuarioId;
}
