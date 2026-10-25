package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.Usuario;
import pe.edu.utp.korvia.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UsuarioService usuarioService;

    public GlobalControllerAdvice(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ModelAttribute("usuarioActual")
    public Usuario usuarioActual(HttpSession session) {
        Object id = session.getAttribute("usuarioId");
        if (id instanceof Long usuarioId) {
            return usuarioService.buscarPorId(usuarioId);
        }
        return null;
    }
}
