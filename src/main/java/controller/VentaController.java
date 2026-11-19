package controller;

import exceptions.*;
import model.*;

import java.util.ArrayList;
import java.util.List;

/** Intermediario entre las pantallas de venta / cancelacion y CinemaUQ. */
public class VentaController {

    private final CinemaUQ cine;

    public VentaController(CinemaUQ cine) {
        this.cine = cine;
    }

    /**
     * Arma un Boleto por cada asiento elegido (todos con la misma tarifa) y llama a realizarVenta.
     * El codigo de la venta se genera solo: V1, V2, V3...
     */
    public Venta vender(Operario operario, Cliente cliente, Funcion funcion, List<Asiento> asientos, TipoEntrada tipo)
            throws FuncionNoEncontradaException, SalaNoDisponibleException,
            AsientoOcupadoException, ElementoDuplicadoException {
        Tarifa tarifa = buscarTarifa(tipo);
        List<Boleto> boletos = new ArrayList<>();
        for (Asiento a : asientos) {
            boletos.add(new Boleto(a, tarifa, funcion));
        }
        return cine.realizarVenta(generarCodigo(), cliente, operario, funcion, boletos);
    }

    public void cancelar(Venta venta) throws VentaNoEncontradaException {
        if (venta.getEstadoVenta() == EstadoVenta.CANCELADA) {
            throw new IllegalStateException("La venta " + venta.getCodigoVenta() + " ya estaba cancelada.");
        }
        cine.cancelarVenta(venta.getCodigoVenta());
    }

    /** Devuelve la tarifa del tipo de entrada o lanza un error si el administrador no la ha registrado. */
    public Tarifa buscarTarifa(TipoEntrada tipo) {
        Tarifa tarifa = cine.buscarTarifa(tipo);
        if (tarifa == null) {
            throw new IllegalStateException("No hay una tarifa registrada para " + tipo
                    + ". El administrador debe registrarla primero.");
        }
        return tarifa;
    }

    public List<Venta> listarVentas() {
        return cine.getListCineVenta();
    }

    public List<Operario> listarOperarios() {
        return cine.getListCineOperario();
    }

    public List<Cliente> listarClientes() {
        return cine.getListCineCliente();
    }

    /** Solo las funciones PROGRAMADAS admiten ventas. */
    public List<Funcion> funcionesProgramadas() {
        List<Funcion> lista = new ArrayList<>();
        for (Funcion f : cine.consultarFunciones()) {
            if (f.getEstadoFuncion() == EstadoFuncion.PROGRAMADA) {
                lista.add(f);
            }
        }
        return lista;
    }

    private String generarCodigo() {
        int numero = cine.getListCineVenta().size() + 1;
        while (cine.buscarVenta("V" + numero) != null) {
            numero++;
        }
        return "V" + numero;
    }
}
