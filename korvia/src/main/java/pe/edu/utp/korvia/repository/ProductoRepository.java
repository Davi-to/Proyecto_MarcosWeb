package pe.edu.utp.korvia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.utp.korvia.dto.ProductoOpcionDTO;
import pe.edu.utp.korvia.dto.ProductoReporteDTO;
import pe.edu.utp.korvia.model.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoriaCodigo(String codigo);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByCategoriaIdOrderByNombreAsc(Long categoriaId);

    boolean existsByCategoriaId(Long categoriaId);

    @Query("""
            select new pe.edu.utp.korvia.dto.ProductoOpcionDTO(
                p.id, p.nombre, p.stock, p.reservado)
            from Producto p
            where p.categoria.id = :categoriaId
            order by p.nombre asc
            """)
    List<ProductoOpcionDTO> listarOpcionesPorCategoria(@Param("categoriaId") Long categoriaId);

    @Query("""
            select new pe.edu.utp.korvia.dto.ProductoReporteDTO(
                p.id, p.nombre, c.nombre, p.precio, p.stock, p.reservado)
            from Producto p
            join p.categoria c
            order by c.nombre asc, p.nombre asc
            """)
    List<ProductoReporteDTO> listarReporte();
}
