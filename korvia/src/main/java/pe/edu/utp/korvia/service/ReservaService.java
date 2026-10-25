package pe.edu.utp.korvia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.korvia.dto.ReservaDetalleDTO;
import pe.edu.utp.korvia.model.Producto;
import pe.edu.utp.korvia.model.Reserva;
import pe.edu.utp.korvia.model.Usuario;
import pe.edu.utp.korvia.repository.ProductoRepository;
import pe.edu.utp.korvia.repository.ReservaRepository;
import pe.edu.utp.korvia.repository.UsuarioRepository;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaService(ReservaRepository reservaRepository,
            ProductoRepository productoRepository,
            UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Reserva crearReserva(Long productoId, Long usuarioId, String nombre,
            String email, Integer cantidad) {
        actualizarEstados();

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (cantidad == null || cantidad < 1) {
            throw new IllegalArgumentException("La cantidad debe ser al menos 1");
        }
        if (producto.getStockDisponible() < cantidad) {
            throw new IllegalStateException("Stock insuficiente. Disponible: "
                    + producto.getStockDisponible());
        }

        producto.setReservado((producto.getReservado() == null ? 0 : producto.getReservado()) + cantidad);
        productoRepository.save(producto);

        Usuario usuario = usuarioId == null ? null
                : usuarioRepository.findById(usuarioId).orElse(null);

        Reserva reserva = new Reserva(null, producto, usuario,
                nombre == null ? "" : nombre.trim(),
                email == null ? "" : email.trim(),
                cantidad);

        return reservaRepository.save(reserva);
    }

    @Transactional
    public List<Reserva> listarTodas() {
        actualizarEstados();
        return reservaRepository.findAllByOrderByFechaReservaDesc();
    }

    @Transactional
    public List<Reserva> listarActivas() {
        actualizarEstados();
        return reservaRepository.findByEstado("ACTIVA");
    }

    @Transactional
    public Reserva buscarPorId(Long id) {
        actualizarEstados();
        return reservaRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva != null && reserva.isActiva()) {
            reserva.setEstado("CANCELADA");
            liberarReserva(reserva);
            reservaRepository.save(reserva);
            return true;
        }
        return false;
    }

    @Transactional
    public int contarActivas() {
        actualizarEstados();
        return reservaRepository.findByEstado("ACTIVA").size();
    }

    @Transactional(readOnly = true)
    public List<ReservaDetalleDTO> listarDetalle() {
        return reservaRepository.listarDetalle();
    }

    @Transactional(readOnly = true)
    public List<ReservaDetalleDTO> listarDetallePorProducto(Long productoId) {
        return reservaRepository.listarDetallePorProducto(productoId);
    }

    private void actualizarEstados() {
        for (Reserva reserva : reservaRepository.findByEstado("ACTIVA")) {
            if (reserva.isExpirada()) {
                reserva.setEstado("EXPIRADA");
                liberarReserva(reserva);
                reservaRepository.save(reserva);
            }
        }
    }

    private void liberarReserva(Reserva reserva) {
        Producto producto = reserva.getProducto();
        if (producto != null) {
            int reservado = producto.getReservado() == null ? 0 : producto.getReservado();
            producto.setReservado(Math.max(0, reservado - reserva.getCantidad()));
            productoRepository.save(producto);
        }
    }
}
