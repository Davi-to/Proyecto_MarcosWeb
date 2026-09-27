package pe.edu.utp.korvia.service;

import pe.edu.utp.korvia.model.ItemCarrito;
import pe.edu.utp.korvia.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoService {

    // Carrito en memoria
    private List<ItemCarrito> items = new ArrayList<>();

    // Agregar producto (si ya existe, suma cantidad)
    public void agregarProducto(Producto producto, Integer cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getId().equals(producto.getId())) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    // Quitar un producto
    public void quitarProducto(Long productoId) {
        items.removeIf(item -> item.getProducto().getId().equals(productoId));
    }

    // Actualizar cantidad
    public void actualizarCantidad(Long productoId, Integer cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getId().equals(productoId)) {
                item.setCantidad(cantidad);
                return;
            }
        }
    }

    // Listar items
    public List<ItemCarrito> listarItems() {
        return items;
    }

    // Total del carrito
    public Double calcularTotal() {
        return items.stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();
    }

    // Cantidad total de items (para el badge del navbar)
    public Integer contarItems() {
        return items.stream()
                .mapToInt(ItemCarrito::getCantidad)
                .sum();
    }

    // Vaciar carrito
    public void vaciar() {
        items.clear();
    }
}