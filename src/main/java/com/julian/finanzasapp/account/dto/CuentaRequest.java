package com.julian.finanzasapp.account.dto;

import com.julian.finanzasapp.account.TipoCuenta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CuentaRequest {

    @NotBlank(message = "El nombre de la cuenta es obligatorio")
    private String nombre;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuenta tipo;

    @NotNull(message = "El saldo es obligatorio")
    private BigDecimal saldo;

    @NotNull(message = "El id del usuario es obligatorio")
    private Long usuarioId;
}
