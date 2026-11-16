package co.edu.uniquindio.poo.cinemauqjfx.controller;

import co.edu.uniquindio.poo.cinemauqjfx.model.Cine;
import co.edu.uniquindio.poo.cinemauqjfx.model.Cliente;
import co.edu.uniquindio.poo.cinemauqjfx.model.CodigoDuplicadoException;
import co.edu.uniquindio.poo.cinemauqjfx.model.TipoCliente;

import java.util.List;

/** Pasa las ordenes de la pantalla de clientes al Cine. */
public class ClienteController {

    private final Cine cine;

    public ClienteController(Cine cine) {
        this.cine = cine;
    }

    public void registrar(String identificacion, String nombre, String correo, TipoCliente tipo)
            throws CodigoDuplicadoException {
        cine.registrarCliente(new Cliente(identificacion, nombre, correo, tipo));
    }

    public List<Cliente> listar() {
        return cine.consultarClientes();
    }
}
