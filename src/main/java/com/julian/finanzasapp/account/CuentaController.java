package com.julian.finanzasapp.account;

import com.julian.finanzasapp.account.dto.CuentaRequest;
import com.julian.finanzasapp.account.dto.CuentaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public List<CuentaResponse> listar(@AuthenticationPrincipal Long usuarioId) {
        return cuentaService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public CuentaResponse buscarPorId(@PathVariable Long id, @AuthenticationPrincipal Long usuarioId) {
        return cuentaService.buscarPorId(id, usuarioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaResponse crear(@Valid @RequestBody CuentaRequest request,
                                @AuthenticationPrincipal Long usuarioId) {
        return cuentaService.crear(request, usuarioId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id, @AuthenticationPrincipal Long usuarioId) {
        cuentaService.eliminar(id, usuarioId);
    }
}