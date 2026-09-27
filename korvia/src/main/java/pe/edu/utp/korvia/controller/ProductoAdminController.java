package pe.edu.utp.korvia.controller;

import pe.edu.utp.korvia.model.Categoria;
import pe.edu.utp.korvia.model.Producto;
import pe.edu.utp.korvia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/producto")
public class ProductoAdminController {

    @Autowired
    private ProductoService productoService;

    // ============ FORM NUEVO ============
    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", productoService.listarCategorias());
        model.addAttribute("modo", "nuevo");
        return "admin-producto-form";
    }

    // ============ CREAR ============
    @PostMapping("/guardar")
    public String guardar(@RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam(required = false) String descripcionLarga,
            @RequestParam Double precio,
            @RequestParam Integer stock,
            @RequestParam String imagen,
            @RequestParam String categoriaCodigo,
            @RequestParam(required = false) String cpu,
            @RequestParam(required = false) String ram,
            @RequestParam(required = false) String almacenamiento,
            @RequestParam(required = false) String gpu,
            RedirectAttributes redirect) {

        Categoria categoria = productoService.listarCategorias().stream()
                .filter(c -> c.getCodigo().equals(categoriaCodigo))
                .findFirst()
                .orElse(productoService.listarCategorias().get(0));

        Producto p = new Producto(null, nombre, descripcion, precio, stock, imagen, categoria);
        p.setCpu(cpu);
        p.setRam(ram);
        p.setAlmacenamiento(almacenamiento);
        p.setGpu(gpu);
        p.setDescripcionLarga(descripcionLarga != null && !descripcionLarga.isEmpty()
                ? descripcionLarga
                : descripcion);

        productoService.agregarProducto(p);
        redirect.addFlashAttribute("mensaje", "Producto creado correctamente");
        return "redirect:/administracion";
    }

    // ============ FORM EDITAR ============
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Producto producto = productoService.buscarPorId(id);
        if (producto == null) {
            redirect.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/administracion";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", productoService.listarCategorias());
        model.addAttribute("modo", "editar");
        return "admin-producto-form";
    }

    // ============ ACTUALIZAR ============
    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam(required = false) String descripcionLarga,
            @RequestParam Double precio,
            @RequestParam Integer stock,
            @RequestParam String imagen,
            @RequestParam String categoriaCodigo,
            @RequestParam(required = false) String cpu,
            @RequestParam(required = false) String ram,
            @RequestParam(required = false) String almacenamiento,
            @RequestParam(required = false) String gpu,
            RedirectAttributes redirect) {

        Categoria categoria = productoService.listarCategorias().stream()
                .filter(c -> c.getCodigo().equals(categoriaCodigo))
                .findFirst()
                .orElse(productoService.listarCategorias().get(0));

        Producto datos = new Producto();
        datos.setNombre(nombre);
        datos.setDescripcion(descripcion);
        datos.setDescripcionLarga(descripcionLarga);
        datos.setPrecio(precio);
        datos.setStock(stock);
        datos.setImagen(imagen);
        datos.setCategoria(categoria);
        datos.setCpu(cpu);
        datos.setRam(ram);
        datos.setAlmacenamiento(almacenamiento);
        datos.setGpu(gpu);

        productoService.actualizarProducto(id, datos);
        redirect.addFlashAttribute("mensaje", "Producto actualizado");
        return "redirect:/administracion";
    }

    // ============ ELIMINAR ============
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirect) {
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado) {
            redirect.addFlashAttribute("mensaje", "Producto eliminado");
        } else {
            redirect.addFlashAttribute("error", "No se pudo eliminar el producto");
        }
        return "redirect:/administracion";
    }
}