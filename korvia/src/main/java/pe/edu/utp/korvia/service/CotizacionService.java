package pe.edu.utp.korvia.service;

import pe.edu.utp.korvia.model.Cotizacion;
import pe.edu.utp.korvia.model.ItemCarrito;
import pe.edu.utp.korvia.model.ItemCotizacion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CotizacionService {

    private final List<Cotizacion> cotizaciones = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);
    private final AtomicLong contadorNumero = new AtomicLong(1);

    public Cotizacion generarCotizacion(String clienteNombre,
            String clienteEmail,
            List<ItemCarrito> itemsCarrito) {

        String numero = String.format("C001-%08d", contadorNumero.getAndIncrement());
        Cotizacion cot = new Cotizacion(contadorId.getAndIncrement(), numero,
                clienteNombre, clienteEmail);

        for (ItemCarrito item : itemsCarrito) {
            cot.getItems().add(new ItemCotizacion(
                    item.getProducto().getNombre(),
                    item.getCantidad(),
                    item.getProducto().getPrecio()));
        }

        cot.calcularTotales();
        cotizaciones.add(cot);
        return cot;
    }

    public List<Cotizacion> listarTodas() {
        actualizarVencidas();
        return cotizaciones;
    }

    public Cotizacion buscarPorId(Long id) {
        actualizarVencidas();
        return cotizaciones.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean aprobarCotizacion(Long id) {
        Cotizacion c = buscarPorId(id);
        if (c != null && "PENDIENTE".equals(c.getEstado()) && !c.isVencida()) {
            c.setEstado("APROBADA");
            return true;
        }
        return false;
    }

    public boolean rechazarCotizacion(Long id) {
        Cotizacion c = buscarPorId(id);
        if (c != null && "PENDIENTE".equals(c.getEstado())) {
            c.setEstado("RECHAZADA");
            return true;
        }
        return false;
    }

    private void actualizarVencidas() {
        for (Cotizacion c : cotizaciones) {
            if ("PENDIENTE".equals(c.getEstado()) && c.isVencida()) {
                c.setEstado("VENCIDA");
            }
        }
    }

    public int contarPendientes() {
        actualizarVencidas();
        return (int) cotizaciones.stream().filter(Cotizacion::isVigente).count();
    }

    public int contarTodas() {
        return cotizaciones.size();
    }
}