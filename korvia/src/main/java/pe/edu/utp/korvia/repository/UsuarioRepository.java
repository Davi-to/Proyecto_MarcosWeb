package pe.edu.utp.korvia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.korvia.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    List<Usuario> findAllByOrderByIdAsc();
}
