package pe.edu.utp.korvia.dto;

public class ProductoOpcionDTO {

    private final Long id;
    private final String nombre;
    private final Integer stock;
    private final Integer reservado;

    public ProductoOpcionDTO(Long id, String nombre, Integer stock, Integer reservado) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.reservado = reservado;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
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
