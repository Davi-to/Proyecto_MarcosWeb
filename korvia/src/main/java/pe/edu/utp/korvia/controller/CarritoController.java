package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.ItemCarrito;
import pe.edu.utp.korvia.model.Producto;
import pe.edu.utp.korvia.service.CarritoService;
import pe.edu.utp.korvia.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;
    private final ProductoService productoService;

    public CarritoController(CarritoService carritoService, ProductoService productoService) {
        this.carritoService = carritoService;
        this.productoService = productoService;
    }

    @GetMapping
    public String verCarrito(Model model) {
        List<ItemCarrito> items = carritoService.listarItems();
        model.addAttribute("items", items);
        model.addAttribute("total", carritoService.calcularTotal());
        return "carrito";
    }

    @PostMapping("/agregar")
    public String agregar(@RequestParam Long productoId,
            @RequestParam(defaultValue = "1") Integer cantidad,
            RedirectAttributes redirect) {
        Producto producto = productoService.buscarPorId(productoId);

        if (producto == null) {
            redirect.addFlashAttribute("error", "El producto no existe");
            return "redirect:/carrito";
        }
        if (cantidad == null || cantidad < 1) {
            redirect.addFlashAttribute("error", "La cantidad debe ser al menos 1");
            return "redirect:/catalogo";
        }

        int yaEnCarrito = carritoService.listarItems().stream()
                .filter(item -> item.getProductoId().equals(productoId))
                .mapToInt(ItemCarrito::getCantidad)
                .sum();

        if (producto.getStockDisponible() < yaEnCarrito + cantidad) {
            redirect.addFlashAttribute("error",
                    "No hay stock suficiente. Disponible: " + producto.getStockDisponible());
            return "redirect:/catalogo";
        }

        carritoService.agregarProducto(producto, cantidad);
        redirect.addFlashAttribute("mensaje", "Producto agregado: " + producto.getNombre());
        return "redirect:/carrito";
    }

    @PostMapping("/quitar")
    public String quitar(@RequestParam Long productoId, RedirectAttributes redirect) {
        carritoService.quitarProducto(productoId);
        redirect.addFlashAttribute("mensaje", "Producto quitado del carrito");
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciar(RedirectAttributes redirect) {
        carritoService.vaciar();
        redirect.addFlashAttribute("mensaje", "Carrito vaciado");
        return "redirect:/carrito";
    }
}
