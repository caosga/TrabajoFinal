package co.edu.uniquindio.poo.cinemauqjfx.controller;

import co.edu.uniquindio.poo.cinemauqjfx.model.AsientoOcupadoException;
import co.edu.uniquindio.poo.cinemauqjfx.model.Asiento;
import co.edu.uniquindio.poo.cinemauqjfx.model.Cine;
import co.edu.uniquindio.poo.cinemauqjfx.model.Cliente;
import co.edu.uniquindio.poo.cinemauqjfx.model.EstadoFuncion;
import co.edu.uniquindio.poo.cinemauqjfx.model.Funcion;
import co.edu.uniquindio.poo.cinemauqjfx.model.FuncionNoEncontradaException;
import co.edu.uniquindio.poo.cinemauqjfx.model.Operario;
import co.edu.uniquindio.poo.cinemauqjfx.model.SinAsientosDisponiblesException;
import co.edu.uniquindio.poo.cinemauqjfx.model.TipoEntrada;
import co.edu.uniquindio.poo.cinemauqjfx.model.Venta;
import co.edu.uniquindio.poo.cinemauqjfx.model.VentaNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

/**
 * Pasa las ordenes de venta y cancelacion al Operario (que implementa IVender)
 * y las consultas de apoyo al Cine.
 */
public class VentaController {

    private final Cine cine;
    private final Operario operario;

    public VentaController(Cine cine, Operario operario) {
        this.cine = cine;
        this.operario = operario;
    }

    public Venta vender(Cliente cliente, Funcion funcion, Asiento asiento, TipoEntrada tipo)
            throws FuncionNoEncontradaException, SinAsientosDisponiblesException, AsientoOcupadoException {
        String codigo = "V" + (cine.consultarVentas().size() + 1);
        return operario.venderEntrada(cine, codigo, cliente, funcion, asiento, tipo);
    }

    public void cancelar(String codigoVenta) throws VentaNoEncontradaException {
        operario.cancelarVenta(cine, codigoVenta);
    }

    public double valorEntrada(Funcion funcion, TipoEntrada tipo) {
        return cine.calcularValorEntrada(funcion, tipo);
    }

    public List<Venta> listarVentas() {
        return cine.consultarVentas();
    }

    public List<Cliente> listarClientes() {
        return cine.consultarClientes();
    }

    /** Solo las funciones PROGRAMADAS admiten ventas. */
    public List<Funcion> funcionesProgramadas() {
        List<Funcion> lista = new ArrayList<>();
        for (Funcion f : cine.consultarFunciones()) {
            if (f.getEstado() == EstadoFuncion.PROGRAMADA) {
                lista.add(f);
            }
        }
        return lista;
    }
}
