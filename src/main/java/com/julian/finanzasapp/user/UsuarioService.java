package com.julian.finanzasapp.user;

import com.julian.finanzasapp.exception.RecursoDuplicadoException;
import com.julian.finanzasapp.exception.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
    }

    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RecursoDuplicadoException("Ya existe un usuario con ese email: " + usuario.getEmail());
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario datosActualizados) {
        Usuario usuario = buscarPorId(id);
        usuario.setNombre(datosActualizados.getNombre());
        usuario.setEmail(datosActualizados.getEmail());
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }
}
