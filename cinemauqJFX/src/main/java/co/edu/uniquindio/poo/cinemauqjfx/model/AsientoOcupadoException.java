package co.edu.uniquindio.poo.cinemauqjfx.model;

/** Se lanza al intentar vender un asiento que ya esta ocupado. */
public class AsientoOcupadoException extends CinemaException {
    public AsientoOcupadoException(String mensaje) {
        super(mensaje);
    }
}
