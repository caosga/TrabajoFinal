package co.edu.uniquindio.poo.cinemauqjfx.controller;

import co.edu.uniquindio.poo.cinemauqjfx.model.Cine;
import co.edu.uniquindio.poo.cinemauqjfx.model.CodigoDuplicadoException;
import co.edu.uniquindio.poo.cinemauqjfx.model.Sala;

import java.util.List;

/** Pasa las ordenes de la pantalla de salas al Cine. */
public class SalaController {

    private final Cine cine;

    public SalaController(Cine cine) {
        this.cine = cine;
    }

    public void registrar(String codigo, String nombre, int filas, int asientosPorFila)
            throws CodigoDuplicadoException {
        cine.registrarSala(new Sala(codigo, nombre, filas, asientosPorFila));
    }

    public void modificar(String codigo, String nombre) {
        cine.modificarSala(codigo, nombre);
    }

    public void deshabilitar(String codigo) {
        cine.deshabilitarSala(codigo);
    }

    public List<Sala> listar() {
        return cine.consultarSalas();
    }
}
