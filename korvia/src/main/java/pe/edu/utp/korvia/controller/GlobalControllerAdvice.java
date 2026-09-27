package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("usuarioActual")
    public Usuario usuarioActual(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }
}