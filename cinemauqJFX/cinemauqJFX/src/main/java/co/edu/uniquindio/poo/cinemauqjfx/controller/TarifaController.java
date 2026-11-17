package co.edu.uniquindio.poo.cinemauqjfx.controller;

import co.edu.uniquindio.poo.cinemauqjfx.model.Cine;
import co.edu.uniquindio.poo.cinemauqjfx.model.Tarifa;
import co.edu.uniquindio.poo.cinemauqjfx.model.TipoEntrada;

import java.util.List;

/** Pasa las ordenes de la pantalla de tarifas al Cine. */
public class TarifaController {

    private final Cine cine;

    public TarifaController(Cine cine) {
        this.cine = cine;
    }

    /** Crea la tarifa si no existe o actualiza su valor si ya existe. */
    public void definir(TipoEntrada tipo, double valor) {
        cine.definirTarifa(tipo, valor);
    }

    public List<Tarifa> listar() {
        return cine.consultarTarifas();
    }
}
