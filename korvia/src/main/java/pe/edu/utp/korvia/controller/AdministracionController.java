package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.service.CategoriaService;
import pe.edu.utp.korvia.service.CotizacionService;
import pe.edu.utp.korvia.service.ProductoService;
import pe.edu.utp.korvia.service.ReservaService;
import pe.edu.utp.korvia.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdministracionController {

    private final ProductoService productoService;
    private final ReservaService reservaService;
    private final CotizacionService cotizacionService;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;

    public AdministracionController(ProductoService productoService,
            ReservaService reservaService,
            CotizacionService cotizacionService,
            UsuarioService usuarioService,
            CategoriaService categoriaService) {
        this.productoService = productoService;
        this.reservaService = reservaService;
        this.cotizacionService = cotizacionService;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
    }

    @GetMapping("/administracion")
    public String admin(@RequestParam(required = false) String buscar, Model model) {
        if (buscar != null && !buscar.isEmpty()) {
            model.addAttribute("productos", productoService.buscarPorNombre(buscar));
            model.addAttribute("buscar", buscar);
        } else {
            model.addAttribute("productos", productoService.listarTodos());
        }
        model.addAttribute("reservas", reservaService.listarTodas());
        model.addAttribute("reservasActivas", reservaService.contarActivas());
        model.addAttribute("cotizaciones", cotizacionService.listarTodas());
        model.addAttribute("totalCotizaciones", cotizacionService.contarTodas());
        model.addAttribute("totalUsuarios", usuarioService.contar());
        model.addAttribute("totalProductos", productoService.contar());
        model.addAttribute("categorias", categoriaService.listarConConteo());
        model.addAttribute("totalCategorias", categoriaService.contar());
        return "administracion";
    }
}
