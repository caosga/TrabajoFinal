package controller;

import exceptions.ElementoDuplicadoException;
import exceptions.SalaNoDisponibleException;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Intermediario entre la pantalla de funciones y CinemaUQ. */
public class FuncionController {

    private final CinemaUQ cine;

    public FuncionController(CinemaUQ cine) {
        this.cine = cine;
    }

    public String programar(String codigo, LocalDateTime fechaHora, double valorBase,
                            Pelicula pelicula, Sala sala)
            throws SalaNoDisponibleException, ElementoDuplicadoException {
        return cine.programarFuncion(codigo, fechaHora, valorBase, pelicula.getCodigoPel(), sala.getCodigoSala());
    }

    public Funcion buscar(String codigo) {
        return cine.buscarFuncion(codigo);
    }

    public List<Funcion> listar() {
        return cine.consultarFunciones();
    }

    /** Peliculas DISPONIBLES para el desplegable. */
    public List<Pelicula> peliculasDisponibles() {
        return cine.consultarPeliculas();
    }

    /**
     * Todas las salas. Se muestran tambien las que estan FUERA_DE_SERVICIO para que,
     * si el usuario elige una, el cine lance SalaNoDisponibleException.
     */
    public List<Sala> salas() {
        return new ArrayList<>(cine.consultarSalas());
    }
}
