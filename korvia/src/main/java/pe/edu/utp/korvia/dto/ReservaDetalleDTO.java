package pe.edu.utp.korvia.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservaDetalleDTO {

    private final Long id;
    private final String producto;
    private final String categoria;
    private final String cliente;
    private final String email;
    private final Integer cantidad;
    private final String estado;
    private final LocalDateTime fechaReserva;
    private final LocalDateTime fechaExpiracion;

    public ReservaDetalleDTO(Long id, String producto, String categoria,
            String cliente, String email, Integer cantidad, String estado,
            LocalDateTime fechaReserva, LocalDateTime fechaExpiracion) {
        this.id = id;
        this.producto = producto;
        this.categoria = categoria;
        this.cliente = cliente;
        this.email = email;
        this.cantidad = cantidad;
        this.estado = estado;
        this.fechaReserva = fechaReserva;
        this.fechaExpiracion = fechaExpiracion;
    }

    public Long getId() {
        return id;
    }

    public String getProducto() {
        return producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEmail() {
        return email;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public String getFechaReservaFormateada() {
        return formatear(fechaReserva);
    }

    public String getFechaExpiracionFormateada() {
        return formatear(fechaExpiracion);
    }

    private String formatear(LocalDateTime fecha) {
        if (fecha == null) {
            return "";
        }
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
