package pe.edu.utp.korvia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "cliente_nombre", nullable = false, length = 150)
    private String clienteNombre;

    @Column(name = "cliente_email", nullable = false, length = 150)
    private String clienteEmail;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;

    @Column(nullable = false, length = 20)
    private String estado; // ACTIVA, EXPIRADA, CANCELADA

    public Reserva() {
    }

    public Reserva(Long id, Producto producto, String clienteNombre, String clienteEmail, Integer cantidad) {
        this(id, producto, null, clienteNombre, clienteEmail, cantidad);
    }

    public Reserva(Long id, Producto producto, Usuario usuario, String clienteNombre,
            String clienteEmail, Integer cantidad) {
        this.id = id;
        this.producto = producto;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
