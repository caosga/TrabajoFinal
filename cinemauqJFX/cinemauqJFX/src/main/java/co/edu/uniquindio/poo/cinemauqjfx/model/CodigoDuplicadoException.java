package co.edu.uniquindio.poo.cinemauqjfx.model;

/** Se lanza al registrar una pelicula, sala o funcion con un codigo que ya existe. */
public class CodigoDuplicadoException extends CinemaException {
    public CodigoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
