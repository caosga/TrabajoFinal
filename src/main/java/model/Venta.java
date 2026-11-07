package model;

import java.time.LocalDateTime;

public class Venta {
    private String codigoVenta;
    private LocalDateTime fechaHora;
    private EstadoVenta estadoVenta;
    /**
     * Relaciones de clase Venta
     */
    private Tarifa theTarifa;
    private Funcion theFuncion;
    private Asiento theAsiento;
    private Cliente theCliente;

    /**
     * Metodo constructor para la clase Venta
     * @param codigoVenta
     * @param fechaHora
     * @param estadoVenta
     * @param theTarifa
     * @param theFuncion
     * @param theAsiento
     * @param theCliente
     */
    public Venta(String codigoVenta, LocalDateTime fechaHora, EstadoVenta estadoVenta,
                 Tarifa theTarifa, Funcion theFuncion, Asiento theAsiento, Cliente theCliente) {
        this.codigoVenta = codigoVenta;
        this.fechaHora = fechaHora;
        this.estadoVenta = estadoVenta;
        this.theTarifa = theTarifa;
        this.theFuncion = theFuncion;
        this.theAsiento = theAsiento;
        this.theCliente = theCliente;
    }

    public String getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(String codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoVenta getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(EstadoVenta estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public Tarifa getTheTarifa() {
        return theTarifa;
    }

    public void setTheTarifa(Tarifa theTarifa) {
        this.theTarifa = theTarifa;
    }

    public Funcion getTheFuncion() {
        return theFuncion;
    }

    public void setTheFuncion(Funcion theFuncion) {
        this.theFuncion = theFuncion;
    }

    public Asiento getTheAsiento() {
        return theAsiento;
    }

    public void setTheAsiento(Asiento theAsiento) {
        this.theAsiento = theAsiento;
    }

    public Cliente getTheCliente() {
        return theCliente;
    }

    public void setTheCliente(Cliente theCliente) {
        this.theCliente = theCliente;
    }
}
