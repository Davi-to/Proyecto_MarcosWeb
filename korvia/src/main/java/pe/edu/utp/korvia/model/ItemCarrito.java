package pe.edu.utp.korvia.model;

public class ItemCarrito {
    private Producto producto;
    private Integer cantidad;

    public ItemCarrito() {
    }

    public ItemCarrito(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Subtotal del item
    public Double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}