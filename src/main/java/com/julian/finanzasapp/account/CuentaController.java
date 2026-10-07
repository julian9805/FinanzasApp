package com.julian.finanzasapp.account;

import com.julian.finanzasapp.account.dto.CuentaRequest;
import com.julian.finanzasapp.account.dto.CuentaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<CuentaResponse> listarPorUsuario(@PathVariable Long usuarioId) {
        return cuentaService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public CuentaResponse buscarPorId(@PathVariable Long id) {
        return cuentaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaResponse crear(@Valid @RequestBody CuentaRequest request) {
        return cuentaService.crear(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        cuentaService.eliminar(id);
    }
}
