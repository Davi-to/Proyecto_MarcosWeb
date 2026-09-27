package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.service.CotizacionService;
import pe.edu.utp.korvia.service.ProductoService;
import pe.edu.utp.korvia.service.ReservaService;
import pe.edu.utp.korvia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdministracionController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private CotizacionService cotizacionService;

    @Autowired
    private UsuarioService usuarioService;

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
        return "administracion";
    }
}