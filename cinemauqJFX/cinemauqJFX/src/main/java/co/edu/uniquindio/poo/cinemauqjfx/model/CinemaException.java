package co.edu.uniquindio.poo.cinemauqjfx.model;

/**
 * Excepcion base del dominio CINEMAUQ.
 * Todas las excepciones propias del sistema heredan de ella,
 * asi se puede atrapar cualquier error del sistema con un solo catch.
 */
public class CinemaException extends Exception {
    public CinemaException(String mensaje) {
        super(mensaje);
    }
}
