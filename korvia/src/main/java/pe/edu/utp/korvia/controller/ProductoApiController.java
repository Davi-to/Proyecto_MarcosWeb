package pe.edu.utp.korvia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.korvia.dto.ProductoOpcionDTO;
import pe.edu.utp.korvia.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoApiController {

    private final ProductoService productoService;

    public ProductoApiController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Select dependiente: categoría -> productos
    @GetMapping
    public List<ProductoOpcionDTO> listarPorCategoria(
            @RequestParam(required = false) Long categoriaId) {
        if (categoriaId == null) {
            return List.of();
        }
        return productoService.listarOpcionesPorCategoria(categoriaId);
    }
}
