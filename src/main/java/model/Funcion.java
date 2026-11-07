package model;

import java.time.LocalDateTime;
import java.util.List;

public class Funcion {
    private String codigoFuncion;
    private LocalDateTime fechaHora;
    private double valorBase;
    private  EstadoFuncion estadoFuncion;
    /**
     * Relaciones de la clase Funcion
     */
    private Pelicula thePelicula;
    private Sala theSala;
    private List <Asiento>listAsientoFuncion;
    private List <Venta>listVentaFuncion;

    /**
     * Metodo constructor de la clase Funcion
     * @param codigoFuncion
     * @param fechaHora
     * @param valorBase
     * @param thePelicula
     * @param estadoFuncion
     * @param theSala
     * @param listAsientoFuncion
     * @param listVentaFuncion
     */
    public Funcion(String codigoFuncion, LocalDateTime fechaHora, double valorBase,
                   Pelicula thePelicula, EstadoFuncion estadoFuncion, Sala theSala,
                   List<Asiento> listAsientoFuncion, List<Venta> listVentaFuncion) {
        this.codigoFuncion = codigoFuncion;
        this.fechaHora = fechaHora;
        this.valorBase = valorBase;
        this.thePelicula = thePelicula;
        this.estadoFuncion = estadoFuncion;
        this.theSala = theSala;
        this.listAsientoFuncion = listAsientoFuncion;
        this.listVentaFuncion = listVentaFuncion;
    }

    public String getCodigoFuncion() {
        return codigoFuncion;
    }

    public void setCodigoFuncion(String codigoFuncion) {
        this.codigoFuncion = codigoFuncion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public Pelicula getThePelicula() {
        return thePelicula;
    }

    public void setThePelicula(Pelicula thePelicula) {
        this.thePelicula = thePelicula;
    }

    public EstadoFuncion getEstadoFuncion() {
        return estadoFuncion;
    }

    public void setEstadoFuncion(EstadoFuncion estadoFuncion) {
        this.estadoFuncion = estadoFuncion;
    }

    public Sala getTheSala() {
        return theSala;
    }

    public void setTheSala(Sala theSala) {
        this.theSala = theSala;
    }

    public List<Asiento> getListAsientoFuncion() {
        return listAsientoFuncion;
    }

    public void setListAsientoFuncion(List<Asiento> listAsientoFuncion) {
        this.listAsientoFuncion = listAsientoFuncion;
    }

    public List<Venta> getListVentaFuncion() {
        return listVentaFuncion;
    }

    public void setListVentaFuncion(List<Venta> listVentaFuncion) {
        this.listVentaFuncion = listVentaFuncion;
    }
}
