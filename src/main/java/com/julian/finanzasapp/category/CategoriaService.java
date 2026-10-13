package com.julian.finanzasapp.category;

import com.julian.finanzasapp.category.dto.CategoriaRequest;
import com.julian.finanzasapp.category.dto.CategoriaResponse;
import com.julian.finanzasapp.exception.RecursoNoEncontradoException;
import com.julian.finanzasapp.user.Usuario;
import com.julian.finanzasapp.user.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<CategoriaResponse> listarPorUsuario(Long usuarioId) {
        return categoriaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public CategoriaResponse crear(CategoriaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado con id: " + request.getUsuarioId()));

        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoria.setTipo(request.getTipo());
        categoria.setIcono(request.getIcono());
        categoria.setUsuario(usuario);

        Categoria guardada = categoriaRepository.save(categoria);
        return convertirAResponse(guardada);
    }

    public void eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con id: " + id));
        categoriaRepository.delete(categoria);
    }

    private CategoriaResponse convertirAResponse(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getTipo(),
                categoria.getIcono(),
                categoria.getUsuario().getId()
        );
    }
}
