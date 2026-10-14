package com.julian.finanzasapp.user;

import com.julian.finanzasapp.user.dto.UsuarioRequest;
import com.julian.finanzasapp.user.dto.UsuarioResponse;
import com.julian.finanzasapp.user.dto.UsuarioUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse crear(@Valid @RequestBody UsuarioRequest request) {
        return usuarioService.crear(request);
    }

    @GetMapping("/me")
    public UsuarioResponse perfil(@AuthenticationPrincipal Long usuarioId) {
        return usuarioService.buscarPorId(usuarioId);
    }

    @PutMapping("/me")
    public UsuarioResponse actualizar(@AuthenticationPrincipal Long usuarioId,
                                      @Valid @RequestBody UsuarioUpdateRequest request) {
        return usuarioService.actualizar(usuarioId, request);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@AuthenticationPrincipal Long usuarioId) {
        usuarioService.eliminar(usuarioId);
    }
}