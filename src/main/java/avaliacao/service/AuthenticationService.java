package avaliacao.service;

import avaliacao.entity.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.HashMap;

@ApplicationScoped
public class AuthenticationService {

    @Inject
    UsuarioService usuarioService;

    public String autenticar(String username, String password) {
        Usuario usuario = usuarioService.findByUsername(username);
        if (usuario != null && usuario.getSenha().equals(password)) {

            HashMap<String, Object> claims = new HashMap<>();
            claims.put("roles", usuario.getRole().getName());


            return Jwt.issuer("https://github.com/julianoosmir")
                    .subject(usuario.getUsername())
                    .expiresIn(3600)
                    .groups(usuario.getRole().getName())
                    .claim("realm_access", claims)
                    .sign();
        }
        return null;
    }
}
