package controller;

import exceptions.ElementoDuplicadoException;
import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Intermediario entre la pantalla de peliculas y CinemaUQ.
 * Recibe la orden del ViewController y se la pasa al cine.
 */
public class PeliculaController {

    private final CinemaUQ cine;

    public PeliculaController(CinemaUQ cine) {
        this.cine = cine;
    }

    public void registrar(String codigo, String nombre, int duracion, Genero genero,
                          Clasificacion clasificacion) throws ElementoDuplicadoException {
        cine.registrarPelicula(new Pelicula(codigo, nombre, duracion, EstadoPelicula.DISPONIBLE,
                genero, clasificacion, new ArrayList<>()));
    }

    public String modificar(String codigo, String nombre, int duracion, Genero genero, Clasificacion clasificacion) {
        return cine.modificarPelicula(codigo, nombre, duracion, genero, clasificacion);
    }

    public String deshabilitar(String codigo) {
        return cine.deshabilitarPelicula(codigo);
    }

    public String habilitar(String codigo) {
        return cine.habilitarPelicula(codigo);
    }

    public Pelicula buscar(String codigo) {
        return cine.buscarPelicula(codigo);
    }

    /** Todas las peliculas (disponibles y no disponibles), para la pantalla del administrador. */
    public List<Pelicula> listarTodas() {
        return cine.getListCinePelicula();
    }

    /** Solo las peliculas DISPONIBLES (consultarPeliculas del cine). */
    public List<Pelicula> listarDisponibles() {
        return cine.consultarPeliculas();
    }
}
