package pe.edu.utp.korvia.service;

import pe.edu.utp.korvia.model.Producto;
import pe.edu.utp.korvia.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservaService {

    private final List<Reserva> reservas = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    @Autowired
    private ProductoService productoService;

    public Reserva crearReserva(Long productoId, String nombre, String email, Integer cantidad) {
        Producto producto = productoService.buscarPorId(productoId);
        if (producto == null)
            return null;

        if (producto.getStockDisponible() < cantidad)
            return null;

        Reserva reserva = new Reserva(contadorId.getAndIncrement(), producto, nombre, email, cantidad);
        reservas.add(reserva);

        producto.setReservado(producto.getReservado() + cantidad);

        return reserva;
    }

    public List<Reserva> listarTodas() {
        actualizarEstados();
        return reservas;
    }

    public List<Reserva> listarActivas() {
        actualizarEstados();
        return reservas.stream().filter(Reserva::isActiva).toList();
    }

    public Reserva buscarPorId(Long id) {
        actualizarEstados();
        return reservas.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean cancelarReserva(Long id) {
        Reserva r = buscarPorId(id);
        if (r != null && r.isActiva()) {
            r.setEstado("CANCELADA");
            Producto p = r.getProducto();
            p.setReservado(Math.max(0, p.getReservado() - r.getCantidad()));
            return true;
        }
        return false;
    }

    private void actualizarEstados() {
        for (Reserva r : reservas) {
            if ("ACTIVA".equals(r.getEstado()) && r.isExpirada()) {
                r.setEstado("EXPIRADA");
                Producto p = r.getProducto();
                p.setReservado(Math.max(0, p.getReservado() - r.getCantidad()));
            }
        }
    }

    public int contarActivas() {
        actualizarEstados();
        return (int) reservas.stream().filter(Reserva::isActiva).count();
    }
}