package co.edu.uniquindio.poo.cinemauqjfx.model;

public class Tarifa {

    private TipoEntrada tipoEntrada;
    private double valor;

    public Tarifa(TipoEntrada tipoEntrada, double valor) {
        this.tipoEntrada = tipoEntrada;
        this.valor = valor;
    }

    public TipoEntrada getTipoEntrada() {
        return tipoEntrada;
    }

    public double getValor() {
        return valor;
    }

    public void modificarValor(double nuevoValor) {
        this.valor = nuevoValor;
    }

    @Override
    public String toString() {
        return tipoEntrada + " $" + (long) valor;
    }
}
