package model;
import java.util.List;

public class CinemaUQ {
    private String nombreCine, nit, direccion;
    /**
     * Relaciones de la clase CinemaUQ
     */
    private List<Sala>listCineSala;
    private List<Pelicula>listCinePelicula;
    private List<Funcion>listCineFuncion;
    private List<Tarifa>listCineTarifa;
    private List<Venta>listCineVenta;
    private List<Persona>listCinePersona;

    /**
     * Metodo constructor de la clase CinemaUQ
     * @param nombreCine
     * @param direccion
     * @param nit
     * @param listCineSala
     * @param listCinePelicula
     * @param listCineFuncion
     * @param listCineTarifa
     * @param listCineVenta
     * @param listCinePersona
     */

    public CinemaUQ(String nombreCine, String direccion, String nit, List<Sala> listCineSala,
                    List<Pelicula> listCinePelicula, List<Funcion> listCineFuncion,
                    List<Tarifa> listCineTarifa, List<Venta> listCineVenta, List<Persona> listCinePersona) {
        this.nombreCine = nombreCine;
        this.direccion = direccion;
        this.nit = nit;
        this.listCineSala = listCineSala;
        this.listCinePelicula = listCinePelicula;
        this.listCineFuncion = listCineFuncion;
        this.listCineTarifa = listCineTarifa;
        this.listCineVenta = listCineVenta;
        this.listCinePersona = listCinePersona;
    }


    public String getNombreCine() {
        return nombreCine;
    }

    public void setNombreCine(String nombreCine) {
        this.nombreCine = nombreCine;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Sala> getListCineSala() {
        return listCineSala;
    }

    public void setListCineSala(List<Sala> listCineSala) {
        this.listCineSala = listCineSala;
    }

    public List<Pelicula> getListCinePelicula() {
        return listCinePelicula;
    }

    public void setListCinePelicula(List<Pelicula> listCinePelicula) {
        this.listCinePelicula = listCinePelicula;
    }

    public List<Funcion> getListCineFuncion() {
        return listCineFuncion;
    }

    public void setListCineFuncion(List<Funcion> listCineFuncion) {
        this.listCineFuncion = listCineFuncion;
    }

    public List<Tarifa> getListCineTarifa() {
        return listCineTarifa;
    }

    public void setListCineTarifa(List<Tarifa> listCineTarifa) {
        this.listCineTarifa = listCineTarifa;
    }

    public List<Venta> getListCineVenta() {
        return listCineVenta;
    }

    public void setListCineVenta(List<Venta> listCineVenta) {
        this.listCineVenta = listCineVenta;
    }

    public List<Persona> getListCinePersona() {
        return listCinePersona;
    }

    public void setListCinePersona(List<Persona> listCinePersona) {
        this.listCinePersona = listCinePersona;
    }
}
