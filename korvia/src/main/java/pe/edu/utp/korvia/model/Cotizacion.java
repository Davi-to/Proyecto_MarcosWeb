package pe.edu.utp.korvia.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Cotizacion {
    private Long id;
    private String numero; // "C001-00000001"
    private String clienteNombre;
    private String clienteEmail;
    private LocalDateTime fecha;
    private LocalDateTime fechaVencimiento;
    private String estado; // PENDIENTE, APROBADA, RECHAZADA, VENCIDA
    private List<ItemCotizacion> items = new ArrayList<>();
    private Double subtotal;
    private Double igv;
    private Double total;

    public Cotizacion() {
    }

    public Cotizacion(Long id, String numero, String clienteNombre, String clienteEmail) {
        this.id = id;
        this.numero = numero;
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.fecha = LocalDateTime.now();
        this.fechaVencimiento = this.fecha.plusDays(7);
        this.estado = "PENDIENTE";
    }

    public void calcularTotales() {
        this.subtotal = items.stream().mapToDouble(ItemCotizacion::getSubtotal).sum();
        this.igv = Math.round(this.subtotal * 0.18 * 100.0) / 100.0;
        this.total = Math.round((this.subtotal + this.igv) * 100.0) / 100.0;
    }

    public boolean isVencida() {
        return LocalDateTime.now().isAfter(fechaVencimiento);
    }

    public boolean isVigente() {
        return "PENDIENTE".equals(estado) && !isVencida();
    }

    public long getDiasRestantes() {
        if (!isVigente())
            return 0;
        return java.time.Duration.between(LocalDateTime.now(), fechaVencimiento).toDays();
    }

    public String getFechaFormateada() {
        if (fecha == null)
            return "";
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getFechaVencimientoFormateada() {
        if (fechaVencimiento == null)
            return "";
        return fechaVencimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<ItemCotizacion> getItems() {
        return items;
    }

    public void setItems(List<ItemCotizacion> items) {
        this.items = items;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}