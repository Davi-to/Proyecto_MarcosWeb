package pe.edu.utp.korvia.service;

import pe.edu.utp.korvia.model.Rol;
import pe.edu.utp.korvia.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public UsuarioService() {
        Rol admin = new Rol(1L, "ADMIN");
        Rol cliente = new Rol(2L, "CLIENTE");

        usuarios.add(new Usuario(contadorId.getAndIncrement(), "Admin", "Korvia",
                "admin@korvia.pe", "admin123", admin));
        usuarios.add(new Usuario(contadorId.getAndIncrement(), "Juan", "Pérez",
                "juan@email.com", "123456", cliente));
    }

    // Buscar por email
    public Usuario buscarPorEmail(String email) {
        if (email == null)
            return null;
        return usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    // Autenticar
    public Usuario autenticar(String email, String password) {
        Usuario u = buscarPorEmail(email);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    // Registrar nuevo usuario
    public Usuario registrar(String nombre, String apellido, String email, String password) {
        // Validar que el email no exista
        if (buscarPorEmail(email) != null) {
            return null;
        }

        Rol cliente = new Rol(2L, "CLIENTE");
        Usuario nuevo = new Usuario(contadorId.getAndIncrement(),
                nombre, apellido, email, password, cliente);
        usuarios.add(nuevo);
        return nuevo;
    }

    // Listar todos
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    // Contar usuarios
    public int contar() {
        return usuarios.size();
    }
}