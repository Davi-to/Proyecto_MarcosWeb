package pe.edu.utp.korvia.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pe.edu.utp.korvia.model.Producto;

public class ProductoForm {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 200, message = "El nombre debe tener entre 3 y 200 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no debe superar 500 caracteres")
    private String descripcion;

    private String descripcionLarga;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @NotBlank(message = "La imagen es obligatoria")
    @Size(max = 255, message = "La imagen no debe superar 255 caracteres")
    private String imagen;

    @NotNull(message = "Selecciona una categoría")
    private Long categoriaId;

    @Size(max = 100, message = "CPU no debe superar 100 caracteres")
    private String cpu;

    @Size(max = 100, message = "RAM no debe superar 100 caracteres")
    private String ram;

    @Size(max = 100, message = "Almacenamiento no debe superar 100 caracteres")
    private String almacenamiento;

    @Size(max = 100, message = "GPU no debe superar 100 caracteres")
    private String gpu;

    public ProductoForm() {
    }

    public static ProductoForm desdeProducto(Producto producto) {
        ProductoForm form = new ProductoForm();
        form.setNombre(producto.getNombre());
        form.setDescripcion(producto.getDescripcion());
        form.setDescripcionLarga(producto.getDescripcionLarga());
        form.setPrecio(producto.getPrecio());
        form.setStock(producto.getStock());
        form.setImagen(producto.getImagen());
        form.setCategoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null);
        form.setCpu(producto.getCpu());
        form.setRam(producto.getRam());
        form.setAlmacenamiento(producto.getAlmacenamiento());
        form.setGpu(producto.getGpu());
        return form;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }
}
