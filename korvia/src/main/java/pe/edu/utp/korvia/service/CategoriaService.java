package pe.edu.utp.korvia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.korvia.dto.CategoriaConteoDTO;
import pe.edu.utp.korvia.dto.CategoriaForm;
import pe.edu.utp.korvia.model.Categoria;
import pe.edu.utp.korvia.repository.CategoriaRepository;
import pe.edu.utp.korvia.repository.ProductoRepository;

import java.util.List;
import java.util.Locale;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAllByOrderByNombreAsc();
    }

    public List<CategoriaConteoDTO> listarConConteo() {
        return categoriaRepository.listarConConteo();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria buscarPorCodigo(String codigo) {
        return categoriaRepository.findByCodigo(codigo).orElse(null);
    }

    public boolean existeCodigo(String codigo, Long idExcluir) {
        String codigoNormalizado = normalizarCodigo(codigo);
        if (idExcluir == null) {
            return categoriaRepository.existsByCodigo(codigoNormalizado);
        }
        return categoriaRepository.existsByCodigoAndIdNot(codigoNormalizado, idExcluir);
    }

    public boolean existeNombre(String nombre, Long idExcluir) {
        String nombreNormalizado = normalizarNombre(nombre);
        if (idExcluir == null) {
            return categoriaRepository.existsByNombreIgnoreCase(nombreNormalizado);
        }
        return categoriaRepository.existsByNombreIgnoreCaseAndIdNot(nombreNormalizado, idExcluir);
    }

    @Transactional
    public Categoria crear(CategoriaForm form) {
        String codigo = normalizarCodigo(form.getCodigo());
        String nombre = normalizarNombre(form.getNombre());

        if (categoriaRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe una categoría con ese código");
        }
        if (categoriaRepository.existsByNombreIgnoreCase(nombre)) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }

        Categoria categoria = new Categoria(null, nombre, codigo);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria actualizar(Long id, CategoriaForm form) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        String codigo = normalizarCodigo(form.getCodigo());
        String nombre = normalizarNombre(form.getNombre());

        if (categoriaRepository.existsByCodigoAndIdNot(codigo, id)) {
            throw new IllegalArgumentException("Ya existe otra categoría con ese código");
        }
        if (categoriaRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
            throw new IllegalArgumentException("Ya existe otra categoría con ese nombre");
        }

        categoria.setNombre(nombre);
        categoria.setCodigo(codigo);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void eliminar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        if (productoRepository.existsByCategoriaId(id)) {
            throw new IllegalStateException(
                    "No se puede eliminar la categoría porque tiene productos asociados");
        }

        categoriaRepository.delete(categoria);
    }

    public long contar() {
        return categoriaRepository.count();
    }

    private String normalizarCodigo(String codigo) {
        return codigo == null ? null : codigo.trim().toLowerCase(Locale.ROOT);
    }

    private String normalizarNombre(String nombre) {
        return nombre == null ? null : nombre.trim().replaceAll("\\s+", " ");
    }
}
