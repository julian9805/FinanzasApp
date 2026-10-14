package com.julian.finanzasapp.transaction;

import com.julian.finanzasapp.transaction.dto.TransaccionRequest;
import com.julian.finanzasapp.transaction.dto.TransaccionResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping
    public List<TransaccionResponse> listar(@AuthenticationPrincipal Long usuarioId) {
        return transaccionService.listarPorUsuario(usuarioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransaccionResponse crear(@Valid @RequestBody TransaccionRequest request,
                                     @AuthenticationPrincipal Long usuarioId) {
        return transaccionService.crear(request, usuarioId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id, @AuthenticationPrincipal Long usuarioId) {
        transaccionService.eliminar(id, usuarioId);
    }
}
