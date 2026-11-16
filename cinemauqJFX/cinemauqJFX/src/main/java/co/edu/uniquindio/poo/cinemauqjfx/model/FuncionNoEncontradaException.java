package co.edu.uniquindio.poo.cinemauqjfx.model;

/** Se lanza al buscar o vender para una funcion que no existe. */
public class FuncionNoEncontradaException extends CinemaException {
    public FuncionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
