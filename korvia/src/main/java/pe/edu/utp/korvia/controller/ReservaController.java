package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.Reserva;
import pe.edu.utp.korvia.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Listar todas las reservas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        model.addAttribute("activas", reservaService.contarActivas());
        return "reservas";
    }

    // Crear reserva
    @PostMapping("/crear")
    public String crear(@RequestParam Long productoId,
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam(defaultValue = "1") Integer cantidad,
            RedirectAttributes redirect) {
        Reserva reserva = reservaService.crearReserva(productoId, nombre, email, cantidad);
        if (reserva != null) {
            redirect.addFlashAttribute("mensaje",
                    "Reserva creada. Tienes 24 horas para concretar la compra.");
        } else {
            redirect.addFlashAttribute("error",
                    "No se pudo crear la reserva. Verifica el stock disponible.");
        }
        return "redirect:/reservas";
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
}