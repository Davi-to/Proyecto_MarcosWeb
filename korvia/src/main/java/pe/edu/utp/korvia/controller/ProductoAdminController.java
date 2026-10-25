package pe.edu.utp.korvia.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.utp.korvia.dto.ProductoForm;
import pe.edu.utp.korvia.model.Producto;
import pe.edu.utp.korvia.service.CategoriaService;
import pe.edu.utp.korvia.service.ProductoService;

@Controller
@RequestMapping("/admin/producto")
public class ProductoAdminController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ProductoAdminController(ProductoService productoService,
            CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    // ============ FORM NUEVO ============
    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        ProductoForm form = new ProductoForm();
        prepararFormulario(model, form, "nuevo", null);
        return "admin-producto-form";
    }

    // ============ CREAR ============
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("productoForm") ProductoForm form,
            BindingResult result,
            Model model,
            RedirectAttributes redirect) {

        if (result.hasErrors()) {
            prepararFormulario(model, form, "nuevo", null);
            return "admin-producto-form";
        }

        try {
            productoService.crear(form);
            redirect.addFlashAttribute("mensaje", "Producto creado correctamente");
            return "redirect:/administracion";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            prepararFormulario(model, form, "nuevo", null);
            return "admin-producto-form";
        }
    }

    // ============ FORM EDITAR ============
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Producto producto = productoService.buscarPorId(id);
        if (producto == null) {
            redirect.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/administracion";
        }
        ProductoForm form = ProductoForm.desdeProducto(producto);
        prepararFormulario(model, form, "editar", id);
        return "admin-producto-form";
    }

    // ============ ACTUALIZAR ============
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
            @Valid @ModelAttribute("productoForm") ProductoForm form,
            BindingResult result,
            Model model,
            RedirectAttributes redirect) {

        if (result.hasErrors()) {
            prepararFormulario(model, form, "editar", id);
            return "admin-producto-form";
        }

        try {
            productoService.actualizar(id, form);
            redirect.addFlashAttribute("mensaje", "Producto actualizado");
            return "redirect:/administracion";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            prepararFormulario(model, form, "editar", id);
            return "admin-producto-form";
        }
    }

    // ============ ELIMINAR ============
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            productoService.eliminar(id);
            redirect.addFlashAttribute("mensaje", "Producto eliminado");
        } catch (RuntimeException ex) {
            redirect.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/administracion";
    }

    private void prepararFormulario(Model model, ProductoForm form, String modo, Long productoId) {
        model.addAttribute("productoForm", form);
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("modo", modo);
        model.addAttribute("productoId", productoId);
        model.addAttribute("formAction", "nuevo".equals(modo)
                ? "/admin/producto/guardar"
                : "/admin/producto/actualizar/" + productoId);
    }
}
