package avaliacao.service;

import avaliacao.entity.Usuario;
import avaliacao.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public void save(Usuario usuario) {
        usuarioRepository.persistAndFlush(usuario);
    }


    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}