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

    public CuentaResponse buscarPorId(Long id, Long usuarioId) {
        return convertirAResponse(obtenerCuenta(id, usuarioId));
    }

    public CuentaResponse crear(CuentaRequest request, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado con id: " + usuarioId));

        Cuenta cuenta = new Cuenta();
        cuenta.setNombre(request.getNombre());
        cuenta.setTipo(request.getTipo());
        cuenta.setSaldo(request.getSaldo());
        cuenta.setUsuario(usuario);

        return convertirAResponse(cuentaRepository.save(cuenta));
    }

    public void eliminar(Long id, Long usuarioId) {
        cuentaRepository.delete(obtenerCuenta(id, usuarioId));
    }

    private Cuenta obtenerCuenta(Long id, Long usuarioId) {
        return cuentaRepository.findByIdAndUsuarioId(id, usuarioId)
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