package pe.edu.utp.korvia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CategoriaForm {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar 100 caracteres")
    private String nombre;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 50, message = "El código no debe superar 50 caracteres")
    @Pattern(regexp = "(?i)[a-z0-9-]+", message = "El código solo admite letras, números y guiones")
    private String codigo;

    public CategoriaForm() {
    }

    public CategoriaForm(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
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
}
