package co.edu.uniquindio.poo.cinemauqjfx.controller;

import co.edu.uniquindio.poo.cinemauqjfx.model.Cine;
import co.edu.uniquindio.poo.cinemauqjfx.model.Clasificacion;
import co.edu.uniquindio.poo.cinemauqjfx.model.CodigoDuplicadoException;
import co.edu.uniquindio.poo.cinemauqjfx.model.Genero;
import co.edu.uniquindio.poo.cinemauqjfx.model.Pelicula;

import java.util.List;

/**
 * Intermediario entre la vista y el modelo (igual que ClienteController en la guia).
 * No tiene logica propia: recibe la orden del ViewController y se la pasa al Cine.
 */
public class PeliculaController {

    private final Cine cine;

    public PeliculaController(Cine cine) {
        this.cine = cine;
    }

    public void registrar(String codigo, String nombre, Genero genero, int duracion,
                          Clasificacion clasificacion) throws CodigoDuplicadoException {
        cine.registrarPelicula(new Pelicula(codigo, nombre, genero, duracion, clasificacion));
    }

    public void modificar(String codigo, String nombre, Genero genero, int duracion,
                          Clasificacion clasificacion) {
        cine.modificarPelicula(codigo, nombre, genero, duracion, clasificacion);
    }

    public void deshabilitar(String codigo) {
        cine.deshabilitarPelicula(codigo);
    }

    public List<Pelicula> listar() {
        return cine.consultarPeliculas();
    }
}
