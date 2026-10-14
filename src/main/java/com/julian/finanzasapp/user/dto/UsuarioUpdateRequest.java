package com.julian.finanzasapp.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioUpdateRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "El email no es válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;
}
