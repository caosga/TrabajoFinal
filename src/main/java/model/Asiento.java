package model;

import java.util.List;

public class Asiento {
    private String codigoAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estadoAsiento;
    private boolean ocupado;
    /**
     * Relaciones de clase Asiento
     */
    private Funcion theFuncion;
    private List<Venta>listAsientoVenta;

    /**
     * Metodo constructor de la clase Asiento
     * @param codigoAsiento
     * @param fila
     * @param numero
     * @param estadoAsiento
     * @param theFuncion
     * @param listAsientoVenta
     * @param ocupado
     */
    public Asiento(String codigoAsiento, String fila, int numero, EstadoAsiento estadoAsiento,
                   Funcion theFuncion, List<Venta> listAsientoVenta, boolean ocupado) {
        this.codigoAsiento = codigoAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estadoAsiento = estadoAsiento;
        this.theFuncion = theFuncion;
        this.listAsientoVenta = listAsientoVenta;
        this.ocupado=ocupado;
    }

    public String getCodigoAsiento() {
        return codigoAsiento;
    }

    public void setCodigoAsiento(String codigoAsiento) {
        this.codigoAsiento = codigoAsiento;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public EstadoAsiento getEstadoAsiento() {
        return estadoAsiento;
    }

    public void setEstadoAsiento(EstadoAsiento estadoAsiento) {
        this.estadoAsiento = estadoAsiento;
    }

    public Funcion getTheFuncion() {
        return theFuncion;
    }

    public void setTheFuncion(Funcion theFuncion) {
        this.theFuncion = theFuncion;
    }

    public List<Venta> getListAsientoVenta() {
        return listAsientoVenta;
    }

    public void setListAsientoVenta(List<Venta> listAsientoVenta) {
        this.listAsientoVenta = listAsientoVenta;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
}
