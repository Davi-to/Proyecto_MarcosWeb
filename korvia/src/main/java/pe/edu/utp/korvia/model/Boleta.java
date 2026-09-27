package pe.edu.utp.korvia.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Boleta {
    private Long id;
    private String numero; // "B001-00000001"
    private String clienteNombre;
    private String clienteEmail;
    private String metodoPago; // Tarjeta / Efectivo / Transferencia
    private LocalDateTime fecha;
    private List<ItemBoleta> items = new ArrayList<>();
    private Double subtotal;
    private Double igv;
    private Double total;

    public Boleta() {
    }

    public Boleta(Long id, String numero, String clienteNombre, String clienteEmail, String metodoPago) {
        this.id = id;
        this.numero = numero;
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.metodoPago = metodoPago;
        this.fecha = LocalDateTime.now();
    }

    public void calcularTotales() {
        this.subtotal = items.stream().mapToDouble(ItemBoleta::getSubtotal).sum();
        this.igv = Math.round(this.subtotal * 0.18 * 100.0) / 100.0;
        this.total = Math.round((this.subtotal + this.igv) * 100.0) / 100.0;
    }

    public String getFechaFormateada() {
        if (fecha == null)
            return "";
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
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

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<ItemBoleta> getItems() {
        return items;
    }

    public void setItems(List<ItemBoleta> items) {
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