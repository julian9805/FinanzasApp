package com.julian.finanzasapp.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crear(@Valid @RequestBody Usuario usuario) {
        return usuarioService.crear(usuario);
    }

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return usuarioService.actualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
}
