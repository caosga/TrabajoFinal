package co.edu.uniquindio.poo.cinemauqjfx.controller;

import co.edu.uniquindio.poo.cinemauqjfx.model.Cine;
import co.edu.uniquindio.poo.cinemauqjfx.model.EstadoFuncion;
import co.edu.uniquindio.poo.cinemauqjfx.model.Funcion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Consultas del operador: funciones y disponibilidad de asientos. */
public class ConsultaController {

    private final Cine cine;

    public ConsultaController(Cine cine) {
        this.cine = cine;
    }

    public List<Funcion> funcionesProgramadas() {
        List<Funcion> lista = new ArrayList<>();
        for (Funcion f : cine.consultarFunciones()) {
            if (f.getEstado() == EstadoFuncion.PROGRAMADA) {
                lista.add(f);
            }
        }
        return lista;
    }

    public List<Funcion> funcionesPorFecha(LocalDate fecha) {
        return cine.funcionesPorFecha(fecha);
    }
}
