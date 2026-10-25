package pe.edu.utp.korvia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.utp.korvia.dto.ReservaDetalleDTO;
import pe.edu.utp.korvia.model.Reserva;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findAllByOrderByFechaReservaDesc();

    List<Reserva> findByEstado(String estado);

    boolean existsByProductoId(Long productoId);

    @Query("""
            select new pe.edu.utp.korvia.dto.ReservaDetalleDTO(
                r.id, p.nombre, c.nombre, r.clienteNombre,
                coalesce(u.email, r.clienteEmail), r.cantidad, r.estado,
                r.fechaReserva, r.fechaExpiracion)
            from Reserva r
            join r.producto p
            join p.categoria c
            left join r.usuario u
            order by r.fechaReserva desc
            """)
    List<ReservaDetalleDTO> listarDetalle();

    @Query("""
            select new pe.edu.utp.korvia.dto.ReservaDetalleDTO(
                r.id, p.nombre, c.nombre, r.clienteNombre,
                coalesce(u.email, r.clienteEmail), r.cantidad, r.estado,
                r.fechaReserva, r.fechaExpiracion)
            from Reserva r
            join r.producto p
            join p.categoria c
            left join r.usuario u
            where p.id = :productoId
            order by r.fechaReserva desc
            """)
    List<ReservaDetalleDTO> listarDetallePorProducto(@Param("productoId") Long productoId);
}
