package com.julian.finanzasapp.account;

import com.julian.finanzasapp.user.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la cuenta es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCuenta tipo;

    @NotNull(message = "El saldo es obligatorio")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
