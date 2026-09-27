package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.Producto;
import pe.edu.utp.korvia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String home(Model model) {
        List<Producto> productos = productoService.listarTodos();
        model.addAttribute("productos", productos);
        return "index";
    }
}