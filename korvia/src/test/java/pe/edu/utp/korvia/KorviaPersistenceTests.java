package pe.edu.utp.korvia;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.korvia.dto.CategoriaForm;
import pe.edu.utp.korvia.dto.ProductoForm;
import pe.edu.utp.korvia.model.Categoria;
import pe.edu.utp.korvia.model.Producto;
import pe.edu.utp.korvia.model.Reserva;
import pe.edu.utp.korvia.repository.ProductoRepository;
import pe.edu.utp.korvia.service.CategoriaService;
import pe.edu.utp.korvia.service.ProductoService;
import pe.edu.utp.korvia.service.ReporteService;
import pe.edu.utp.korvia.service.ReservaService;
import pe.edu.utp.korvia.service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class KorviaPersistenceTests {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void cargaDatosInicialesYConsultaDto() {
        assertTrue(categoriaService.contar() >= 4);
        assertTrue(productoService.contar() >= 20);
        assertNotNull(usuarioService.autenticar("admin@korvia.pe", "admin123"));

        Categoria laptop = categoriaService.buscarPorCodigo("laptop");
        assertNotNull(laptop);
        assertFalse(productoService.listarOpcionesPorCategoria(laptop.getId()).isEmpty());
        assertFalse(reporteService.listarCategoriasConConteo().isEmpty());
        assertFalse(reporteService.listarProductos().isEmpty());
    }

    @Test
    void crudCategoriaFunciona() {
        Categoria creada = categoriaService.crear(new CategoriaForm("Categoría de Prueba", "categoria-prueba"));
        assertNotNull(creada.getId());

        categoriaService.actualizar(creada.getId(),
                new CategoriaForm("Categoría de Prueba Editada", "categoria-prueba-editada"));

        Categoria actualizada = categoriaService.buscarPorId(creada.getId());
        assertNotNull(actualizada);
        assertEquals("Categoría de Prueba Editada", actualizada.getNombre());
        assertEquals("categoria-prueba-editada", actualizada.getCodigo());

        categoriaService.eliminar(creada.getId());
        assertNull(categoriaService.buscarPorId(creada.getId()));
    }

    @Test
    void crudProductoFunciona() {
        Categoria laptop = categoriaService.buscarPorCodigo("laptop");
        assertNotNull(laptop);

        ProductoForm form = new ProductoForm();
        form.setNombre("Producto de Prueba");
        form.setDescripcion("Producto creado por una prueba automatizada");
        form.setDescripcionLarga("Descripción larga de prueba");
        form.setPrecio(1500.0);
        form.setStock(4);
        form.setImagen("producto-prueba.jpg");
        form.setCategoriaId(laptop.getId());
        form.setCpu("CPU de prueba");
        form.setRam("8GB");
        form.setAlmacenamiento("256GB SSD");
        form.setGpu("GPU de prueba");

        Producto creado = productoService.crear(form);
        assertNotNull(creado.getId());
        assertEquals(0, creado.getReservado());

        form.setNombre("Producto de Prueba Editado");
        form.setPrecio(1600.0);
        form.setStock(3);
        productoService.actualizar(creado.getId(), form);

        Producto actualizado = productoService.buscarPorId(creado.getId());
        assertNotNull(actualizado);
        assertEquals("Producto de Prueba Editado", actualizado.getNombre());
        assertEquals(1600.0, actualizado.getPrecio());
        assertEquals(3, actualizado.getStock());

        productoService.eliminar(creado.getId());
        assertNull(productoService.buscarPorId(creado.getId()));
    }

    @Test
    void reservaIncrementaYLiberaStockReservado() {
        Producto producto = productoRepository.findAll().stream()
                .filter(p -> p.getStockDisponible() > 0)
                .findFirst()
                .orElseThrow();
        int reservadoInicial = producto.getReservado();
        int disponibleInicial = producto.getStockDisponible();

        Reserva reserva = reservaService.crearReserva(producto.getId(), null,
                "Cliente de Prueba", "cliente.prueba@example.com", 1);

        assertNotNull(reserva.getId());
        assertTrue(reserva.isActiva());
        assertEquals(reservadoInicial + 1, producto.getReservado());
        assertEquals(disponibleInicial - 1, producto.getStockDisponible());

        assertTrue(reservaService.cancelarReserva(reserva.getId()));
        assertEquals(reservadoInicial, producto.getReservado());
        assertEquals(disponibleInicial, producto.getStockDisponible());
        assertFalse(reserva.isActiva());
    }
}
