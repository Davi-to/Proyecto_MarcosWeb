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

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(name = "descripcion_larga", columnDefinition = "TEXT")
    private String descripcionLarga;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer reservado = 0; // unidades reservadas (24h)

    @Column(length = 255)
    private String imagen;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // Especificaciones
    @Column(length = 100)
    private String cpu;

    @Column(length = 100)
    private String ram;

    @Column(length = 100)
    private String almacenamiento;

    @Column(length = 100)
    private String gpu;

    public Producto() {
        this.reservado = 0;
    }

    public Producto(Long id, String nombre, String descripcion, Double precio,
            Integer stock, String imagen, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.reservado = 0;
        this.imagen = imagen;
        this.categoria = categoria;
    }

    // ============ LÓGICA DE NEGOCIO ============
    public boolean isDisponible() {
        return getStockDisponible() > 0;
    }

    public int getStockDisponible() {
        return (stock == null ? 0 : stock) - (reservado == null ? 0 : reservado);
    }

    public boolean isStockCritico() {
        int disp = getStockDisponible();
        return disp > 0 && disp <= 4;
    }

    public boolean isPocasUnidades() {
        int disp = getStockDisponible();
        return disp >= 5 && disp <= 10;
    }

    public boolean isAgotado() {
        return getStockDisponible() <= 0;
    }

    public String getEstadoEtiqueta() {
        int disp = getStockDisponible();
        if (disp <= 0)
            return "AGOTADO";
        if (disp <= 4)
            return "STOCK_CRITICO";
        if (disp <= 10)
            return "POCAS_UNIDADES";
        return "DISPONIBLE";
    }

    public String getEstadoTexto() {
        int disp = getStockDisponible();
        if (disp <= 0)
            return "Agotado";
        if (disp <= 4)
            return "¡Últimas unidades!";
        if (disp <= 10)
            return "Pocas unidades";
        return "Disponible";
    }

    public String getEstadoColorClass() {
        int disp = getStockDisponible();
        if (disp <= 0)
            return "bg-danger";
        if (disp <= 4)
            return "bg-warning text-dark";
        if (disp <= 10)
            return "bg-info text-dark";
        return "bg-success";
    }

    public Producto setEspecificaciones(String cpu, String ram, String almacenamiento, String gpu) {
        this.cpu = cpu;
        this.ram = ram;
        this.almacenamiento = almacenamiento;
        this.gpu = gpu;
        return this;
    }

    public Producto setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
        return this;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getReservado() {
        return reservado;
    }

    public void setReservado(Integer reservado) {
        this.reservado = reservado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }
}
