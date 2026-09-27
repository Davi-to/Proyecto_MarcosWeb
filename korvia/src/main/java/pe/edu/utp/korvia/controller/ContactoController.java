package pe.edu.utp.korvia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactoController {

    @GetMapping("/contacto")
    public String contactoForm() {
        return "contacto";
    }

    @PostMapping("/contacto")
    public String enviarMensaje(
            @RequestParam String nombreCont,
            @RequestParam String emailCont,
            @RequestParam String mensaje,
            RedirectAttributes redirect) {

        // Aquí luego se guardará el mensaje en BD
        redirect.addFlashAttribute("mensaje",
                "Gracias " + nombreCont + ", te contactaremos pronto.");
        return "redirect:/contacto";
    }
}