package pe.edu.utp.korvia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.korvia.dto.CategoriaConteoDTO;
import pe.edu.utp.korvia.dto.ProductoReporteDTO;
import pe.edu.utp.korvia.dto.ReservaDetalleDTO;
import pe.edu.utp.korvia.repository.CategoriaRepository;
import pe.edu.utp.korvia.repository.ProductoRepository;
import pe.edu.utp.korvia.repository.ReservaRepository;

import java.util.List;

@Service
public class ReporteService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final ReservaRepository reservaRepository;

    public ReporteService(CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository,
            ReservaRepository reservaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.reservaRepository = reservaRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaConteoDTO> listarCategoriasConConteo() {
        return categoriaRepository.listarConConteo();
    }

    @Transactional(readOnly = true)
    public List<ProductoReporteDTO> listarProductos() {
        return productoRepository.listarReporte();
    }

    @Transactional(readOnly = true)
    public List<ReservaDetalleDTO> listarReservas() {
        return reservaRepository.listarDetalle();
    }

    @Transactional(readOnly = true)
    public List<ReservaDetalleDTO> listarReservasPorProducto(Long productoId) {
        return reservaRepository.listarDetallePorProducto(productoId);
    }
}
