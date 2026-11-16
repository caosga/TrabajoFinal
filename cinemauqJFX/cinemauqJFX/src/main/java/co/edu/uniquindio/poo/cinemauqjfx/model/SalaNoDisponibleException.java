package co.edu.uniquindio.poo.cinemauqjfx.model;

/** Se lanza al programar una funcion en una sala fuera de servicio. */
public class SalaNoDisponibleException extends CinemaException {
    public SalaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
