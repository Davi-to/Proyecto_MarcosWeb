package pe.edu.utp.korvia.service;

import pe.edu.utp.korvia.model.Boleta;
import pe.edu.utp.korvia.model.ItemBoleta;
import pe.edu.utp.korvia.model.ItemCarrito;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BoletaService {

    private final List<Boleta> boletas = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);
    private final AtomicLong contadorNumero = new AtomicLong(1);

    public Boleta generarBoleta(String clienteNombre,
            String clienteEmail,
            String metodoPago,
            List<ItemCarrito> itemsCarrito) {

        String numero = String.format("B001-%08d", contadorNumero.getAndIncrement());

        Boleta boleta = new Boleta(contadorId.getAndIncrement(), numero,
                clienteNombre, clienteEmail, metodoPago);

        // Convertir items del carrito a items de boleta (snapshot)
        for (ItemCarrito item : itemsCarrito) {
            boleta.getItems().add(new ItemBoleta(
                    item.getProducto().getNombre(),
                    item.getCantidad(),
                    item.getProducto().getPrecio()));
        }

        boleta.calcularTotales();
        boletas.add(boleta);
        return boleta;
    }

    public List<Boleta> listarTodas() {
        return boletas;
    }

    public Boleta buscarPorId(Long id) {
        return boletas.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public int contarBoletas() {
        return boletas.size();
    }
}