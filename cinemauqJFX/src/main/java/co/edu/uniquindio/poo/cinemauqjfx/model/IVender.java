package co.edu.uniquindio.poo.cinemauqjfx.model;

/**
 * Contrato de quien puede vender y cancelar entradas.
 * Solo el Operario lo implementa (el Administrador no vende).
 * Recibe el Cine porque es el que guarda las funciones y las ventas.
 */
public interface IVender {

    Venta venderEntrada(Cine cine, String codigoVenta, Cliente cliente, Funcion funcion,
                        Asiento asiento, TipoEntrada tipo)
            throws FuncionNoEncontradaException, SinAsientosDisponiblesException,
                   AsientoOcupadoException;

    void cancelarVenta(Cine cine, String codigoVenta) throws VentaNoEncontradaException;
}
