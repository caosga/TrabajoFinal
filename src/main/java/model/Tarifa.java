package model;

import java.util.List;

public class Tarifa {
    private double valor;
    private TipoEntrada tipoEntrada;
    /**
     * Relaciones de la clase Tarifa
     */
    private List<Venta>listVentaTarifa;

    /**
     * Metoodo constructor para la clase Tarifa
     * @param valor
     * @param tipoEntrada
     * @param listVentaTarifa
     */
    public Tarifa(double valor, TipoEntrada tipoEntrada, List<Venta> listVentaTarifa) {
        this.valor = valor;
        this.tipoEntrada = tipoEntrada;
        this.listVentaTarifa = listVentaTarifa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoEntrada getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(TipoEntrada tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public List<Venta> getListVentaTarifa() {
        return listVentaTarifa;
    }

    public void setListVentaTarifa(List<Venta> listVentaTarifa) {
        this.listVentaTarifa = listVentaTarifa;
    }
}
