package com.julian.finanzasapp.account.dto;

import com.julian.finanzasapp.account.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponse {
    private Long id;
    private String nombre;
    private TipoCuenta tipo;
    private BigDecimal saldo;
    private Long usuarioId;
    private String usuarioNombre;
}
