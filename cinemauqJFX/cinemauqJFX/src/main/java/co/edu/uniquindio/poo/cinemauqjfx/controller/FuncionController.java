package co.edu.uniquindio.poo.cinemauqjfx.controller;

import co.edu.uniquindio.poo.cinemauqjfx.model.Cine;
import co.edu.uniquindio.poo.cinemauqjfx.model.CodigoDuplicadoException;
import co.edu.uniquindio.poo.cinemauqjfx.model.EstadoSala;
import co.edu.uniquindio.poo.cinemauqjfx.model.Funcion;
import co.edu.uniquindio.poo.cinemauqjfx.model.FuncionNoEncontradaException;
import co.edu.uniquindio.poo.cinemauqjfx.model.Pelicula;
import co.edu.uniquindio.poo.cinemauqjfx.model.Sala;
import co.edu.uniquindio.poo.cinemauqjfx.model.SalaNoDisponibleException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Pasa las ordenes de la pantalla de funciones al Cine. */
public class FuncionController {

    private final Cine cine;

    public FuncionController(Cine cine) {
        this.cine = cine;
    }

    public void programar(String codigo, Pelicula pelicula, Sala sala, LocalDateTime fechaHora,
                          double valorBase) throws SalaNoDisponibleException, CodigoDuplicadoException {
        cine.programarFuncion(new Funcion(codigo, pelicula, sala, fechaHora, valorBase));
    }

    public void cancelar(String codigo) throws FuncionNoEncontradaException {
        cine.cancelarFuncion(codigo);
    }

    public void finalizar(String codigo) throws FuncionNoEncontradaException {
        cine.finalizarFuncion(codigo);
    }

    public List<Funcion> listar() {
        return cine.consultarFunciones();
    }

    /** Solo las peliculas DISPONIBLES se pueden programar. */
    public List<Pelicula> peliculasDisponibles() {
        List<Pelicula> lista = new ArrayList<>();
        for (Pelicula p : cine.consultarPeliculas()) {
            if (p.estaDisponible()) {
                lista.add(p);
            }
        }
        return lista;
    }

    /** Solo las salas que no estan fuera de servicio. */
    public List<Sala> salasDisponibles() {
        List<Sala> lista = new ArrayList<>();
        for (Sala s : cine.consultarSalas()) {
            if (s.getEstado() == EstadoSala.DISPONIBLE) {
                lista.add(s);
            }
        }
        return lista;
    }
}
