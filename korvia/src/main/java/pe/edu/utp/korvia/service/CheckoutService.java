package pe.edu.utp.korvia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.korvia.model.Boleta;
import pe.edu.utp.korvia.model.ItemCarrito;

import java.util.List;

@Service
public class CheckoutService {

    private final ProductoService productoService;
    private final BoletaService boletaService;
    private final CarritoService carritoService;

    public CheckoutService(ProductoService productoService,
            BoletaService boletaService,
            CarritoService carritoService) {
        this.productoService = productoService;
        this.boletaService = boletaService;
        this.carritoService = carritoService;
    }

    /**
     * El descuento de stock y la creación de la boleta ocurren dentro de la
     * misma transacción. Si falla el descuento, no se confirma nada en MySQL.
     */
    @Transactional
    public Boleta pagar(String clienteNombre, String clienteEmail, String metodoPago) {
        List<ItemCarrito> items = carritoService.listarItems();
        if (items.isEmpty()) {
            throw new IllegalStateException("Tu carrito está vacío");
        }

        productoService.descontarStockCarrito(items);
        Boleta boleta = boletaService.generarBoleta(
                clienteNombre, clienteEmail, metodoPago, items);
        carritoService.vaciar();
        return boleta;
    }
}
