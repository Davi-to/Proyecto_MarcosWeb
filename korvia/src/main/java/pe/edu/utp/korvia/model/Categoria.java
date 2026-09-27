package pe.edu.utp.korvia.model;

public class Categoria {
    private Long id;
    private String nombre;
    private String codigo;

    public Categoria() {
    }

    public Categoria(Long id, String nombre, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    // ¿Esta categoría usa especificaciones tipo PC (CPU, RAM, etc.)?
    public boolean isUsaEspecificaciones() {
        return "desktop".equals(codigo)
                || "laptop".equals(codigo)
                || "all-in-one".equals(codigo);
    }
}