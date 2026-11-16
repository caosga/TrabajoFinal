package co.edu.uniquindio.poo.cinemauqjfx.controller;

import co.edu.uniquindio.poo.cinemauqjfx.model.Cine;
import co.edu.uniquindio.poo.cinemauqjfx.model.Funcion;
import co.edu.uniquindio.poo.cinemauqjfx.model.Pelicula;

import java.util.List;

/** Reportes del administrador. */
public class ReporteController {

    private final Cine cine;

    public ReporteController(Cine cine) {
        this.cine = cine;
    }

    public int totalEntradasVendidas() {
        return cine.totalEntradasVendidas();
    }

    public double totalIngresos() {
        return cine.totalIngresos();
    }

    /** Puede ser null si todavia no hay ventas. */
    public Pelicula peliculaMasVendida() {
        return cine.peliculaMasVendida();
    }

    public List<Funcion> listarFunciones() {
        return cine.consultarFunciones();
    }
}
