package pe.edu.utp.korvia.repository;

import org.springframework.data.jpa.repository.Query;
import pe.edu.utp.korvia.dto.CategoriaConteoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.korvia.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByCodigo(String codigo);

    Optional<Categoria> findByNombreIgnoreCase(String nombre);

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, Long id);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    List<Categoria> findAllByOrderByNombreAsc();

    @Query("""
            select new pe.edu.utp.korvia.dto.CategoriaConteoDTO(
                c.id, c.nombre, c.codigo, count(p.id), sum(p.stock), sum(p.reservado))
            from Categoria c
            left join c.productos p
            group by c.id, c.nombre, c.codigo
            order by c.nombre asc
            """)
    List<CategoriaConteoDTO> listarConConteo();
}
