package com.julian.finanzasapp.user;

import com.julian.finanzasapp.exception.RecursoDuplicadoException;
import com.julian.finanzasapp.exception.RecursoNoEncontradoException;
import com.julian.finanzasapp.user.dto.UsuarioRequest;
import com.julian.finanzasapp.user.dto.UsuarioResponse;
import com.julian.finanzasapp.user.dto.UsuarioUpdateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public UsuarioResponse buscarPorId(Long id) {
        return convertirAResponse(obtenerUsuario(id));
    }

    public UsuarioResponse crear(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RecursoDuplicadoException("Ya existe un usuario con ese email: " + request.getEmail());
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        return convertirAResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponse actualizar(Long id, UsuarioUpdateRequest request) {
        Usuario usuario = obtenerUsuario(id);

        boolean emailCambiado = !usuario.getEmail().equals(request.getEmail());
        if (emailCambiado && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RecursoDuplicadoException("Ya existe un usuario con ese email: " + request.getEmail());
        }

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());

        return convertirAResponse(usuarioRepository.save(usuario));
    }

    public void eliminar(Long id) {
        usuarioRepository.delete(obtenerUsuario(id));
    }

    private Usuario obtenerUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
    }

    private UsuarioResponse convertirAResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getFechaCreacion()
        );
    }
}