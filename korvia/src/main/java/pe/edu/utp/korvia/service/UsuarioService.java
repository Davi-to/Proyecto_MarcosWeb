package pe.edu.utp.korvia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.korvia.model.Rol;
import pe.edu.utp.korvia.model.Usuario;
import pe.edu.utp.korvia.repository.RolRepository;
import pe.edu.utp.korvia.repository.UsuarioRepository;

import java.util.List;
import java.util.Locale;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        return usuarioRepository.findByEmailIgnoreCase(email.trim()).orElse(null);
    }

    public Usuario autenticar(String email, String password) {
        Usuario usuario = buscarPorEmail(email);
        if (usuario != null && password != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }

    @Transactional
    public Usuario registrar(String nombre, String apellido, String email, String password) {
        String emailNormalizado = email == null ? null : email.trim().toLowerCase(Locale.ROOT);
        if (emailNormalizado == null || usuarioRepository.existsByEmailIgnoreCase(emailNormalizado)) {
            return null;
        }

        Rol cliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new IllegalStateException("El rol CLIENTE no está configurado"));

        Usuario nuevo = new Usuario(null,
                nombre == null ? "" : nombre.trim(),
                apellido == null ? "" : apellido.trim(),
                emailNormalizado,
                password,
                cliente);

        return usuarioRepository.save(nuevo);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAllByOrderByIdAsc();
    }

    public long contar() {
        return usuarioRepository.count();
    }
}
