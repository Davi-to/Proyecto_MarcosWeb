package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.Boleta;
import pe.edu.utp.korvia.service.BoletaPdfService;
import pe.edu.utp.korvia.service.BoletaService;
import pe.edu.utp.korvia.service.CarritoService;
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
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private BoletaPdfService boletaPdfService;

    @GetMapping("/checkout")
    public String checkoutForm(Model model, RedirectAttributes redirect) {
        if (carritoService.listarItems().isEmpty()) {
            redirect.addFlashAttribute("error", "Tu carrito está vacío");
            return "redirect:/carrito";
        }
        model.addAttribute("total", carritoService.calcularTotal());
        model.addAttribute("items", carritoService.listarItems());
        return "checkout";
    }

    @PostMapping("/checkout/pagar")
    public String pagar(@RequestParam String clienteNombre,
            @RequestParam String clienteEmail,
            @RequestParam String metodoPago,
            RedirectAttributes redirect) {

        if (carritoService.listarItems().isEmpty()) {
            redirect.addFlashAttribute("error", "Tu carrito está vacío");
            return "redirect:/carrito";
        }

        Boleta boleta = boletaService.generarBoleta(
                clienteNombre, clienteEmail, metodoPago, carritoService.listarItems());

        carritoService.listarItems()
                .forEach(item -> item.getProducto().setStock(item.getProducto().getStock() - item.getCantidad()));

        carritoService.vaciar();
        return "redirect:/boleta/" + boleta.getId();
    }

    @GetMapping("/boleta/{id}")
    public String verBoleta(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Boleta boleta = boletaService.buscarPorId(id);
        if (boleta == null) {
            redirect.addFlashAttribute("error", "Boleta no encontrada");
            return "redirect:/";
        }
        model.addAttribute("boleta", boleta);
        return "boleta";
    }

    @GetMapping("/boletas")
    public String listarBoletas(Model model) {
        model.addAttribute("boletas", boletaService.listarTodas());
        return "boletas-lista";
    }

    @GetMapping("/boleta/{id}/pdf")
    public ResponseEntity<InputStreamResource> descargarPdf(@PathVariable Long id) {
        Boleta boleta = boletaService.buscarPorId(id);
        if (boleta == null)
            return ResponseEntity.notFound().build();

        ByteArrayInputStream pdf = boletaPdfService.generarPdf(boleta);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=boleta-" + boleta.getNumero() + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}