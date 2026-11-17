package co.edu.uniquindio.poo.cinemauqjfx.model;

/** Se lanza al intentar cancelar una venta que no existe. */
public class VentaNoEncontradaException extends CinemaException {
    public VentaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
