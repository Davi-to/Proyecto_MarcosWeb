package pe.edu.utp.korvia.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import pe.edu.utp.korvia.model.ItemCarrito;
import pe.edu.utp.korvia.model.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Carrito por sesión HTTP. Cada navegador tiene su propio carrito.
 * Los items son snapshots; no se guardan entidades JPA.
 */
@Service
@SessionScope
public class CarritoService {

    private final List<ItemCarrito> items = new ArrayList<>();

    public void agregarProducto(Producto producto, Integer cantidad) {
        if (producto == null || cantidad == null || cantidad < 1) {
            return;
        }
        for (ItemCarrito item : items) {
            if (item.getProductoId().equals(producto.getId())) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    public void quitarProducto(Long productoId) {
        items.removeIf(item -> item.getProductoId().equals(productoId));
    }

    public void actualizarCantidad(Long productoId, Integer cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProductoId().equals(productoId)) {
                item.setCantidad(cantidad);
                return;
            }
        }
    }

    public List<ItemCarrito> listarItems() {
        return List.copyOf(items);
    }

    public Double calcularTotal() {
        return items.stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();
    }

    public Integer contarItems() {
        return items.stream()
                .mapToInt(ItemCarrito::getCantidad)
                .sum();
    }

    public void vaciar() {
        items.clear();
    }
}
