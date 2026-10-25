package pe.edu.utp.korvia.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.korvia.dto.ProductoForm;
import pe.edu.utp.korvia.dto.ProductoOpcionDTO;
import pe.edu.utp.korvia.model.Categoria;
import pe.edu.utp.korvia.model.ItemCarrito;
import pe.edu.utp.korvia.model.Producto;
import pe.edu.utp.korvia.repository.CategoriaRepository;
import pe.edu.utp.korvia.repository.ProductoRepository;
import pe.edu.utp.korvia.repository.ReservaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ReservaRepository reservaRepository;

    public ProductoService(ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            ReservaRepository reservaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.reservaRepository = reservaRepository;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Optional<Producto> buscarOptional(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> listarPorCategoria(String codigoCategoria) {
        if (codigoCategoria == null || codigoCategoria.isBlank() || "all".equals(codigoCategoria)) {
            return listarTodos();
        }
        return productoRepository.findByCategoriaCodigo(codigoCategoria);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return listarTodos();
        }
        return productoRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }

    public List<ProductoOpcionDTO> listarOpcionesPorCategoria(Long categoriaId) {
        return productoRepository.listarOpcionesPorCategoria(categoriaId);
    }

    public long contar() {
        return productoRepository.count();
    }

    @Transactional
    public Producto crear(ProductoForm form) {
        Categoria categoria = obtenerCategoria(form.getCategoriaId());

        Producto producto = new Producto();
        producto.setNombre(form.getNombre().trim());
        producto.setDescripcion(form.getDescripcion().trim());
        producto.setDescripcionLarga(descripcionLarga(form));
        producto.setPrecio(form.getPrecio());
        producto.setStock(form.getStock());
        producto.setReservado(0);
        producto.setImagen(form.getImagen().trim());
        producto.setCategoria(categoria);
        producto.setCpu(limpiar(form.getCpu()));
        producto.setRam(limpiar(form.getRam()));
        producto.setAlmacenamiento(limpiar(form.getAlmacenamiento()));
        producto.setGpu(limpiar(form.getGpu()));

        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizar(Long id, ProductoForm form) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        Categoria categoria = obtenerCategoria(form.getCategoriaId());

        int reservado = producto.getReservado() == null ? 0 : producto.getReservado();
        if (form.getStock() != null && form.getStock() < reservado) {
            throw new IllegalArgumentException(
                    "El stock no puede ser menor a las unidades reservadas (" + reservado + ")");
        }

        producto.setNombre(form.getNombre().trim());
        producto.setDescripcion(form.getDescripcion().trim());
        producto.setDescripcionLarga(descripcionLarga(form));
        producto.setPrecio(form.getPrecio());
        producto.setStock(form.getStock());
        producto.setImagen(form.getImagen().trim());
        producto.setCategoria(categoria);
        producto.setCpu(limpiar(form.getCpu()));
        producto.setRam(limpiar(form.getRam()));
        producto.setAlmacenamiento(limpiar(form.getAlmacenamiento()));
        producto.setGpu(limpiar(form.getGpu()));

        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado");
        }
        if (reservaRepository.existsByProductoId(id)) {
            throw new IllegalStateException(
                    "No se puede eliminar el producto porque tiene reservas asociadas");
        }
        productoRepository.deleteById(id);
    }

    /**
     * Descuenta stock de todos los items del carrito en una sola transacción.
     * También libera las unidades reservadas equivalentes para no dejar
     * "reservado" inflado después de una compra.
     */
    @Transactional
    public void descontarStockCarrito(List<ItemCarrito> items) {
        for (ItemCarrito item : items) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new IllegalStateException(
                            "El producto " + item.getNombre() + " ya no existe"));

            int cantidad = item.getCantidad() == null ? 0 : item.getCantidad();
            if (cantidad < 1) {
                throw new IllegalArgumentException("Cantidad inválida para " + producto.getNombre());
            }
            if (producto.getStockDisponible() < cantidad) {
                throw new IllegalStateException(
                        "Stock insuficiente para " + producto.getNombre()
                                + ". Disponible: " + producto.getStockDisponible());
            }

            producto.setStock(producto.getStock() - cantidad);
            int reservado = producto.getReservado() == null ? 0 : producto.getReservado();
            producto.setReservado(Math.max(0, reservado - cantidad));
            productoRepository.save(producto);
        }
    }

    private Categoria obtenerCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
    }

    private String descripcionLarga(ProductoForm form) {
        String descripcionLarga = limpiar(form.getDescripcionLarga());
        return descripcionLarga == null ? form.getDescripcion().trim() : descripcionLarga;
    }

    private String limpiar(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        return valor.trim();
    }
}
