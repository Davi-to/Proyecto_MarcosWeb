package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.ItemCarrito;
import pe.edu.utp.korvia.model.Producto;
import pe.edu.utp.korvia.service.CarritoService;
import pe.edu.utp.korvia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ProductoService productoService;

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
        if (producto != null && producto.isDisponible()) {
            carritoService.agregarProducto(producto, cantidad);
            redirect.addFlashAttribute("mensaje",
                    "Producto agregado: " + producto.getNombre());
        } else {
            redirect.addFlashAttribute("error", "Producto sin stock");
        }
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