package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.Usuario;
import pe.edu.utp.korvia.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirect) {

        Usuario u = usuarioService.autenticar(email, password);

        if (u != null) {
            // Guardamos solo el id: el usuario se vuelve a consultar en cada request.
            session.setAttribute("usuarioId", u.getId());
            redirect.addFlashAttribute("mensaje",
                    "¡Bienvenido, " + u.getNombreCompleto() + "! (rol: " + u.getRol().getNombre() + ")");
            return "redirect:/";
        }

        redirect.addFlashAttribute("error", "Credenciales incorrectas. Verifica tu email y contraseña.");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirect) {
        session.invalidate();
        redirect.addFlashAttribute("mensaje", "Sesión cerrada correctamente");
        return "redirect:/";
    }
}
