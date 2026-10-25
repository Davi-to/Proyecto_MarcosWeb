package pe.edu.utp.korvia.model;

/**
 * Snapshot de un producto dentro del carrito.
 *
 * No guardamos la entidad JPA Producto: guardamos los datos que la vista
 * necesita y el id para volver a consultar el producto real al pagar.
 * Así se evita trabajar con entidades detached.
 */
public class ItemCarrito {

    private Long productoId;
    private String nombre;
    private String categoriaNombre;
    private String imagen;
    private Double precio;
    private Integer cantidad;

    public ItemCarrito() {
    }

    public ItemCarrito(Producto producto, Integer cantidad) {
        this.productoId = producto.getId();
        this.nombre = producto.getNombre();
        this.categoriaNombre = producto.getCategoria() != null ? producto.getCategoria().getNombre() : "";
        this.imagen = producto.getImagen();
        this.precio = producto.getPrecio();
        this.cantidad = cantidad;
    }

    // Subtotal del item
    public Double getSubtotal() {
        return precio * cantidad;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
