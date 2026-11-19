package controller;

import exceptions.ElementoDuplicadoException;
import model.*;

import java.util.ArrayList;
import java.util.List;

/** Intermediario entre la pantalla de clientes y CinemaUQ. */
public class ClienteController {

    private final CinemaUQ cine;

    public ClienteController(CinemaUQ cine) {
        this.cine = cine;
    }

    public String registrar(String identificacion, String nombre, String correo, TipoCliente tipo)
            throws ElementoDuplicadoException {
        if (buscar(identificacion) != null) {
            throw new ElementoDuplicadoException("Ya existe un cliente con la identificación: " + identificacion);
        }
        return cine.agregarCliente(new Cliente(identificacion, nombre, correo, tipo, new ArrayList<>()));
    }

    /** Devuelve el cliente con esa identificacion o null si no existe. */
    public Cliente buscar(String identificacion) {
        for (Cliente c : cine.getListCineCliente()) {
            if (c.getIdentificacion().equalsIgnoreCase(identificacion)) {
                return c;
            }
        }
        return null;
    }

    /** Cuantas ventas ACTIVAS tiene el cliente (se cuentan en la lista de ventas del cine). */
    public int contarCompras(Cliente cliente) {
        int compras = 0;
        for (Venta v : cine.getListCineVenta()) {
            if (v.getTheCliente() == cliente && v.getEstadoVenta() == EstadoVenta.ACTIVA) {
                compras++;
            }
        }
        return compras;
    }

    public List<Cliente> listar() {
        return cine.getListCineCliente();
    }
}
