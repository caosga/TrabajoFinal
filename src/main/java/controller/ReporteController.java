package controller;

import model.*;

import java.time.LocalDate;
import java.util.List;

/** Reportes del administrador (usa los metodos de reportes de CinemaUQ). */
public class ReporteController {

    private final CinemaUQ cine;

    public ReporteController(CinemaUQ cine) {
        this.cine = cine;
    }

    public ReporteGeneralVentas reporteGeneral() {
        return cine.generarReporteGeneralVentas();
    }

    /** Puede ser null si no hay peliculas registradas. */
    public ReportePeliculaMasVendida peliculaMasVendida() {
        return cine.obtenerPeliculaMasVendida();
    }

    public ReporteOcupacionFuncion ocupacion(Funcion funcion) {
        return cine.generarReporteOcupacion(funcion);
    }

    public List<Funcion> listarFunciones() {
        return cine.consultarFunciones();
    }

    public List<Funcion> funcionesPorFecha(LocalDate fecha) {
        return cine.consultarFuncionesPorFecha(fecha).funcionesProgramadas();
    }
}
