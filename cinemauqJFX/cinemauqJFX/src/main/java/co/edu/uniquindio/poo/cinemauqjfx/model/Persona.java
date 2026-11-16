package co.edu.uniquindio.poo.cinemauqjfx.model;

/**
 * Clase abstracta: no se crean personas "genericas", solo Cliente, Operario o Admin.
 * Cada subclase debe decir cual es su rol.
 */
public abstract class Persona {

    private String identificacion;
    private String nombre;
    private String correo;

    public Persona(String identificacion, String nombre, String correo) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.correo = correo;
    }

    public abstract String obtenerRol();

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    @Override
    public String toString() {
        return obtenerRol() + ": " + nombre + " (" + identificacion + ")";
    }
}
