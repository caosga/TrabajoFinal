package controller;

import model.CinemaUQ;
import model.Tarifa;
import model.TipoEntrada;

import java.util.List;

/** Intermediario entre la pantalla de tarifas y CinemaUQ. */
public class TarifaController {

    private final CinemaUQ cine;

    public TarifaController(CinemaUQ cine) {
        this.cine = cine;
    }

    public String registrar(TipoEntrada tipo, double valor) {
        return cine.registrarTarifa(valor, tipo);
    }

    public String modificar(TipoEntrada tipo, double nuevoValor) {
        return cine.modificarTarifa(tipo, nuevoValor);
    }

    public List<Tarifa> listar() {
        return cine.consultarTarifas();
    }
}
