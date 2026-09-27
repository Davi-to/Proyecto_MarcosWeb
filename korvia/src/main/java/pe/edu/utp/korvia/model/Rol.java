package pe.edu.utp.korvia.model;

public class Rol {
    private Long id;
    private String nombre; // "ADMIN", "CLIENTE"

    public Rol() {
    }

    public Rol(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

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
}