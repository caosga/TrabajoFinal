package controller;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Consultas del operador: peliculas, funciones y disponibilidad. */
public class ConsultaController {

    private final CinemaUQ cine;

    public ConsultaController(CinemaUQ cine) {
        this.cine = cine;
    }

    public List<Pelicula> peliculasDisponibles() {
        return cine.consultarPeliculas();
    }

    public List<Funcion> funcionesProgramadas() {
        List<Funcion> lista = new ArrayList<>();
        for (Funcion f : cine.consultarFunciones()) {
            if (f.getEstadoFuncion() == EstadoFuncion.PROGRAMADA) {
                lista.add(f);
            }
        }
        return lista;
    }

    public List<Funcion> funcionesPorFecha(LocalDate fecha) {
        return cine.consultarFuncionesPorFecha(fecha).funcionesProgramadas();
    }

    public ReporteOcupacionFuncion ocupacion(Funcion funcion) {
        return cine.generarReporteOcupacion(funcion);
    }
}
