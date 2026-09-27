package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.Cotizacion;
import pe.edu.utp.korvia.service.CarritoService;
import pe.edu.utp.korvia.service.CotizacionPdfService;
import pe.edu.utp.korvia.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;

@Controller
public class CotizacionController {

    @Autowired
    private CotizacionService cotizacionService;

    @Autowired
    private CotizacionPdfService cotizacionPdfService;

    @Autowired
    private CarritoService carritoService;

    // Formulario de cotización (reutiliza el carrito)
    @GetMapping("/cotizacion/solicitar")
    public String solicitarForm(Model model, RedirectAttributes redirect) {
        if (carritoService.listarItems().isEmpty()) {
            redirect.addFlashAttribute("error", "Tu carrito está vacío");
            return "redirect:/carrito";
        }
        model.addAttribute("total", carritoService.calcularTotal());
        model.addAttribute("items", carritoService.listarItems());
        return "cotizacion-solicitar";
    }

    // Generar cotización
    @PostMapping("/cotizacion/generar")
    public String generar(@RequestParam String clienteNombre,
            @RequestParam String clienteEmail,
            RedirectAttributes redirect) {

        if (carritoService.listarItems().isEmpty()) {
            redirect.addFlashAttribute("error", "Tu carrito está vacío");
            return "redirect:/carrito";
        }

        Cotizacion cot = cotizacionService.generarCotizacion(
                clienteNombre, clienteEmail, carritoService.listarItems());

        // La cotización NO bloquea stock, pero SÍ vaciamos el carrito
        carritoService.vaciar();

        return "redirect:/cotizacion/" + cot.getId();
    }

    // Ver cotización
    @GetMapping("/cotizacion/{id}")
    public String verCotizacion(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Cotizacion cot = cotizacionService.buscarPorId(id);
        if (cot == null) {
            redirect.addFlashAttribute("error", "Cotización no encontrada");
            return "redirect:/";
        }
        model.addAttribute("cotizacion", cot);
        return "cotizacion";
    }

    // Listar todas las cotizaciones (admin)
    @GetMapping("/cotizaciones")
    public String listar(Model model) {
        model.addAttribute("cotizaciones", cotizacionService.listarTodas());
        model.addAttribute("pendientes", cotizacionService.contarPendientes());
        return "cotizaciones-lista";
    }

    // Aprobar cotización
    @PostMapping("/cotizacion/aprobar/{id}")
    public String aprobar(@PathVariable Long id, RedirectAttributes redirect) {
        boolean ok = cotizacionService.aprobarCotizacion(id);
        if (ok) {
            redirect.addFlashAttribute("mensaje", "Cotización aprobada");
        } else {
            redirect.addFlashAttribute("error", "No se pudo aprobar la cotización");
        }
        return "redirect:/cotizaciones";
    }

    // Rechazar cotización
    @PostMapping("/cotizacion/rechazar/{id}")
    public String rechazar(@PathVariable Long id, RedirectAttributes redirect) {
        boolean ok = cotizacionService.rechazarCotizacion(id);
        if (ok) {
            redirect.addFlashAttribute("mensaje", "Cotización rechazada");
        } else {
            redirect.addFlashAttribute("error", "No se pudo rechazar la cotización");
        }
        return "redirect:/cotizaciones";
    }

    // PDF de cotización
    @GetMapping("/cotizacion/{id}/pdf")
    public ResponseEntity<InputStreamResource> descargarPdf(@PathVariable Long id) {
        Cotizacion cot = cotizacionService.buscarPorId(id);
        if (cot == null)
            return ResponseEntity.notFound().build();

        ByteArrayInputStream pdf = cotizacionPdfService.generarPdf(cot);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=cotizacion-" + cot.getNumero() + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}