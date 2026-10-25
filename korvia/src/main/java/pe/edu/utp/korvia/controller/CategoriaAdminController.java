package pe.edu.utp.korvia.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.utp.korvia.dto.CategoriaForm;
import pe.edu.utp.korvia.model.Categoria;
import pe.edu.utp.korvia.service.CategoriaService;

@Controller
@RequestMapping("/admin")
public class CategoriaAdminController {

    private final CategoriaService categoriaService;

    public CategoriaAdminController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // ============ LISTADO ============
    @GetMapping("/categorias")
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarConConteo());
        return "admin-categorias";
    }

    // ============ FORM NUEVO ============
    @GetMapping("/categoria/nuevo")
    public String nuevoForm(Model model) {
        prepararFormulario(model, new CategoriaForm(), "nuevo", null);
        return "admin-categoria-form";
    }

    // ============ CREAR ============
    @PostMapping("/categoria/guardar")
    public String guardar(@Valid @ModelAttribute("categoriaForm") CategoriaForm form,
            BindingResult result,
            Model model,
            RedirectAttributes redirect) {

        if (result.hasErrors()) {
            prepararFormulario(model, form, "nuevo", null);
            return "admin-categoria-form";
        }

        try {
            categoriaService.crear(form);
            redirect.addFlashAttribute("mensaje", "Categoría creada correctamente");
            return "redirect:/admin/categorias";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            prepararFormulario(model, form, "nuevo", null);
            return "admin-categoria-form";
        }
    }

    // ============ FORM EDITAR ============
    @GetMapping("/categoria/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Categoria categoria = categoriaService.buscarPorId(id);
        if (categoria == null) {
            redirect.addFlashAttribute("error", "Categoría no encontrada");
            return "redirect:/admin/categorias";
        }
        CategoriaForm form = new CategoriaForm(categoria.getNombre(), categoria.getCodigo());
        prepararFormulario(model, form, "editar", id);
        return "admin-categoria-form";
    }

    // ============ ACTUALIZAR ============
    @PostMapping("/categoria/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
            @Valid @ModelAttribute("categoriaForm") CategoriaForm form,
            BindingResult result,
            Model model,
            RedirectAttributes redirect) {

        if (result.hasErrors()) {
            prepararFormulario(model, form, "editar", id);
            return "admin-categoria-form";
        }

        try {
            categoriaService.actualizar(id, form);
            redirect.addFlashAttribute("mensaje", "Categoría actualizada");
            return "redirect:/admin/categorias";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            prepararFormulario(model, form, "editar", id);
            return "admin-categoria-form";
        }
    }

    // ============ ELIMINAR ============
    @PostMapping("/categoria/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            categoriaService.eliminar(id);
            redirect.addFlashAttribute("mensaje", "Categoría eliminada");
        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/categorias";
    }

    private void prepararFormulario(Model model, CategoriaForm form, String modo, Long categoriaId) {
        model.addAttribute("categoriaForm", form);
        model.addAttribute("modo", modo);
        model.addAttribute("categoriaId", categoriaId);
        model.addAttribute("formAction", "nuevo".equals(modo)
                ? "/admin/categoria/guardar"
                : "/admin/categoria/actualizar/" + categoriaId);
    }
}
