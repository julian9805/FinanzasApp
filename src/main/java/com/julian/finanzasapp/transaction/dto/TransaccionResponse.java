package com.julian.finanzasapp.transaction.dto;

import com.julian.finanzasapp.category.TipoCategoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionResponse {
    private Long id;
    private String descripcion;
    private BigDecimal monto;
    private LocalDate fecha;
    private TipoCategoria tipo;
    private Long cuentaId;
    private String cuentaNombre;
    private Long categoriaId;
    private String categoriaNombre;
}
