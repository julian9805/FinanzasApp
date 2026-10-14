package com.julian.finanzasapp.auth;

import com.julian.finanzasapp.auth.dto.AuthResponse;
import com.julian.finanzasapp.auth.dto.LoginRequest;
import com.julian.finanzasapp.exception.CredencialesInvalidasException;
import com.julian.finanzasapp.security.JwtService;
import com.julian.finanzasapp.user.Usuario;
import com.julian.finanzasapp.user.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(CredencialesInvalidasException::new);

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new CredencialesInvalidasException();
        }

        String token = jwtService.generarToken(usuario);
        return new AuthResponse(token, usuario.getId(), usuario.getNombre());
    }
}
