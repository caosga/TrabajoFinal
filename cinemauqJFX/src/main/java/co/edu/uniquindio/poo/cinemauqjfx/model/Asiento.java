package co.edu.uniquindio.poo.cinemauqjfx.model;

public class Asiento {

    private String codigo;      // letra de la fila + numero, por ejemplo "A1"
    private String fila;        // letra: "A", "B", "C"...
    private int numero;         // numero dentro de la fila
    private EstadoAsiento estado;

    public Asiento(String codigo, String fila, int numero) {
        this.codigo = codigo;
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public void ocupar() {
        this.estado = EstadoAsiento.OCUPADO;
    }

    public void liberar() {
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getFila() {
        return fila;
    }

    public int getNumero() {
        return numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return codigo + " " + (estado == EstadoAsiento.DISPONIBLE ? "Disponible" : "Ocupado");
    }
}
