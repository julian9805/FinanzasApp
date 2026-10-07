package com.julian.finanzasapp.account;

import com.julian.finanzasapp.account.dto.CuentaRequest;
import com.julian.finanzasapp.account.dto.CuentaResponse;
import com.julian.finanzasapp.exception.RecursoNoEncontradoException;
import com.julian.finanzasapp.user.Usuario;
import com.julian.finanzasapp.user.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;

    public CuentaService(CuentaRepository cuentaRepository, UsuarioRepository usuarioRepository) {
        this.cuentaRepository = cuentaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<CuentaResponse> listarPorUsuario(Long usuarioId) {
        return cuentaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public CuentaResponse buscarPorId(Long id) {
        Cuenta cuenta = obtenerCuenta(id);
        return convertirAResponse(cuenta);
    }

    public CuentaResponse crear(CuentaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado con id: " + request.getUsuarioId()));

        Cuenta cuenta = new Cuenta();
        cuenta.setNombre(request.getNombre());
        cuenta.setTipo(request.getTipo());
        cuenta.setSaldo(request.getSaldo());
        cuenta.setUsuario(usuario);

        Cuenta guardada = cuentaRepository.save(cuenta);
        return convertirAResponse(guardada);
    }

    public void eliminar(Long id) {
        Cuenta cuenta = obtenerCuenta(id);
        cuentaRepository.delete(cuenta);
    }

    private Cuenta obtenerCuenta(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta no encontrada con id: " + id));
    }

    private CuentaResponse convertirAResponse(Cuenta cuenta) {
        return new CuentaResponse(
                cuenta.getId(),
                cuenta.getNombre(),
                cuenta.getTipo(),
                cuenta.getSaldo(),
                cuenta.getUsuario().getId(),
                cuenta.getUsuario().getNombre()
        );
    }
}
