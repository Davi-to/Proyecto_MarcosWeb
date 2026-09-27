package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.Usuario;
import pe.edu.utp.korvia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String registroForm() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String emailReg,
            @RequestParam String passwordReg,
            RedirectAttributes redirect) {

        Usuario nuevo = usuarioService.registrar(nombre, apellido, emailReg, passwordReg);

        if (nuevo != null) {
            redirect.addFlashAttribute("mensaje",
                    "¡Cuenta creada, " + nuevo.getNombre() + "! Ahora inicia sesión.");
            return "redirect:/login";
        } else {
            redirect.addFlashAttribute("error",
                    "Ese correo ya está registrado. Intenta con otro.");
            return "redirect:/registro";
        }
    }
}