package pe.edu.utp.korvia.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.utp.korvia.dto.ReservaForm;
import pe.edu.utp.korvia.model.Reserva;
import pe.edu.utp.korvia.model.Usuario;
import pe.edu.utp.korvia.service.CategoriaService;
import pe.edu.utp.korvia.service.ProductoService;
import pe.edu.utp.korvia.service.ReservaService;
import pe.edu.utp.korvia.service.UsuarioService;

import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final CategoriaService categoriaService;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public ReservaController(ReservaService reservaService,
            CategoriaService categoriaService,
            ProductoService productoService,
            UsuarioService usuarioService) {
        this.reservaService = reservaService;
        this.categoriaService = categoriaService;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    // Listar todas las reservas y preparar el formulario con selects en cascada
    @GetMapping
    public String listar(Model model, HttpSession session) {
        ReservaForm form = new ReservaForm();

        // Si el usuario está logueado, precargamos sus datos.
        Object id = session.getAttribute("usuarioId");
        if (id instanceof Long usuarioId) {
            Usuario usuario = usuarioService.buscarPorId(usuarioId);
            if (usuario != null) {
                form.setNombre(usuario.getNombreCompleto());
                form.setEmail(usuario.getEmail());
            }
        }

        model.addAttribute("reservaForm", form);
        cargarModelo(model, form);
        return "reservas";
    }

    // Crear reserva con validación Bean Validation
    @PostMapping("/crear")
    public String crear(@Valid @ModelAttribute("reservaForm") ReservaForm form,
            BindingResult result,
            HttpSession session,
            Model model,
            RedirectAttributes redirect) {

        if (result.hasErrors()) {
            cargarModelo(model, form);
            return "reservas";
        }

        try {
            Long usuarioId = (Long) session.getAttribute("usuarioId");
            reservaService.crearReserva(form.getProductoId(), usuarioId,
                    form.getNombre(), form.getEmail(), form.getCantidad());

            redirect.addFlashAttribute("mensaje",
                    "Reserva creada. Tienes 24 horas para concretar la compra.");
            return "redirect:/reservas";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            cargarModelo(model, form);
            return "reservas";
        }
    }

    // Cancelar reserva
    @PostMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, RedirectAttributes redirect) {
        boolean ok = reservaService.cancelarReserva(id);
        if (ok) {
            redirect.addFlashAttribute("mensaje", "Reserva cancelada");
        } else {
            redirect.addFlashAttribute("error", "No se pudo cancelar la reserva");
        }
        return "redirect:/reservas";
    }

    private void cargarModelo(Model model, ReservaForm form) {
        List<Reserva> reservas = reservaService.listarTodas();
        model.addAttribute("reservas", reservas);
        model.addAttribute("activas", reservas.stream().filter(Reserva::isActiva).count());
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("productos", form.getCategoriaId() == null
                ? List.of()
                : productoService.listarOpcionesPorCategoria(form.getCategoriaId()));
    }
}
