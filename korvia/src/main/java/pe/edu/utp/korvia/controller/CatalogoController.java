package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.Producto;
import pe.edu.utp.korvia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String catalogo(
            @RequestParam(required = false, defaultValue = "all") String categoria,
            @RequestParam(required = false) String buscar,
            Model model) {

        List<Producto> productos;
        if (buscar != null && !buscar.isEmpty()) {
            productos = productoService.buscarPorNombre(buscar);
        } else {
            productos = productoService.listarPorCategoria(categoria);
        }

        model.addAttribute("productos", productos);
        model.addAttribute("categoriaActual", categoria);
        return "catalogo";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Producto producto = productoService.buscarPorId(id);
        model.addAttribute("producto", producto);
        return "detalle";
    }
}