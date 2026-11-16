package co.edu.uniquindio.poo.cinemauqjfx.model;

/** Se lanza al intentar vender una entrada para una funcion que ya no tiene asientos libres. */
public class SinAsientosDisponiblesException extends CinemaException {
    public SinAsientosDisponiblesException(String mensaje) {
        super(mensaje);
    }
}
