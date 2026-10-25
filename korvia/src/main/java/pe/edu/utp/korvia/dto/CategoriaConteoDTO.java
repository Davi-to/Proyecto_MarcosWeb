package pe.edu.utp.korvia.dto;

public class CategoriaConteoDTO {

    private final Long id;
    private final String nombre;
    private final String codigo;
    private final Long totalProductos;
    private final Long stockTotal;
    private final Long reservadoTotal;

    public CategoriaConteoDTO(Long id, String nombre, String codigo,
            Long totalProductos, Long stockTotal, Long reservadoTotal) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.totalProductos = totalProductos == null ? 0L : totalProductos;
        this.stockTotal = stockTotal == null ? 0L : stockTotal;
        this.reservadoTotal = reservadoTotal == null ? 0L : reservadoTotal;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public Long getTotalProductos() {
        return totalProductos;
    }

    public Long getStockTotal() {
        return stockTotal;
    }

    public Long getReservadoTotal() {
        return reservadoTotal;
    }

    public Long getStockDisponibleTotal() {
        return stockTotal - reservadoTotal;
    }
}
