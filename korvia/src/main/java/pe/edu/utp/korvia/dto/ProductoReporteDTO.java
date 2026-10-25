package pe.edu.utp.korvia.dto;

public class ProductoReporteDTO {

    private final Long id;
    private final String nombre;
    private final String categoria;
    private final Double precio;
    private final Integer stock;
    private final Integer reservado;

    public ProductoReporteDTO(Long id, String nombre, String categoria,
            Double precio, Integer stock, Integer reservado) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.reservado = reservado;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getReservado() {
        return reservado;
    }

    public int getStockDisponible() {
        return (stock == null ? 0 : stock) - (reservado == null ? 0 : reservado);
    }
}
