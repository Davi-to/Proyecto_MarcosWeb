package pe.edu.utp.korvia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pe.edu.utp.korvia.service.CategoriaService;
import pe.edu.utp.korvia.service.ProductoService;
import pe.edu.utp.korvia.service.ReporteService;

@Controller
public class ReporteController {

    private final ReporteService reporteService;
    private final CategoriaService categoriaService;
    private final ProductoService productoService;

    public ReporteController(ReporteService reporteService,
            CategoriaService categoriaService,
            ProductoService productoService) {
        this.reporteService = reporteService;
        this.categoriaService = categoriaService;
        this.productoService = productoService;
    }

    @GetMapping("/admin/reportes")
    public String reportes(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("categoriasConteo", reporteService.listarCategoriasConConteo());
        model.addAttribute("productosReporte", reporteService.listarProductos());
        model.addAttribute("reservasDetalle", reporteService.listarReservas());
        model.addAttribute("totalProductos", productoService.contar());
        model.addAttribute("totalCategorias", categoriaService.contar());
        return "admin-reportes";
    }
}
