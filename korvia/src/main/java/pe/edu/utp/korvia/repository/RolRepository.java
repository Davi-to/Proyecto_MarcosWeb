package pe.edu.utp.korvia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.korvia.model.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);
}
