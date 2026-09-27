package pe.edu.utp.korvia.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reserva {

    private Long id;
    private Producto producto;
    private String clienteNombre;
    private String clienteEmail;
    private Integer cantidad;
    private LocalDateTime fechaReserva;
    private LocalDateTime fechaExpiracion;
    private String estado; // ACTIVA, EXPIRADA, CANCELADA

    public Reserva() {
    }

    public Reserva(Long id, Producto producto, String clienteNombre, String clienteEmail, Integer cantidad) {
        this.id = id;
        this.producto = producto;
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.cantidad = cantidad;
        this.fechaReserva = LocalDateTime.now();
        this.fechaExpiracion = this.fechaReserva.plusHours(24);
        this.estado = "ACTIVA";
    }

    // ¿Está activa todavía?
    public boolean isActiva() {
        return "ACTIVA".equals(estado) && LocalDateTime.now().isBefore(fechaExpiracion);
    }

    // ¿Ya expiró?
    public boolean isExpirada() {
        return LocalDateTime.now().isAfter(fechaExpiracion);
    }

    // Horas restantes
    public long getHorasRestantes() {
        if (!isActiva())
            return 0;
        return java.time.Duration.between(LocalDateTime.now(), fechaExpiracion).toHours();
    }

    public String getFechaReservaFormateada() {
        if (fechaReserva == null)
            return "";
        return fechaReserva.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getFechaExpiracionFormateada() {
        if (fechaExpiracion == null)
            return "";
        return fechaExpiracion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    // ============ GETTERS Y SETTERS ============
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}