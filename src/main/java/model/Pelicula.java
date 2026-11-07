package model;

import java.util.List;

public class Pelicula {
    private String codigoPel;
    private String nombre;
    private int duracion;
    private EstadoPelicula estadoPelicula;
    private Genero genero;
    private Clasificacion clasificacion;
    /**
     * Relaciones de la clase Pelicula
     */
    private List<Funcion>listFuncionPelicula;

    /**
     * Metodo constructor de la clase Pelicula
     * @param codigoPel
     * @param nombre
     * @param duracion
     * @param estadoPelicula
     * @param genero
     * @param clasificacion
     * @param listFuncionPelicula
     */
    public Pelicula(String codigoPel, String nombre, int duracion,
                    EstadoPelicula estadoPelicula, Genero genero, Clasificacion clasificacion,
                    List<Funcion> listFuncionPelicula) {
        this.codigoPel = codigoPel;
        this.nombre = nombre;
        this.duracion = duracion;
        this.estadoPelicula = estadoPelicula;
        this.genero = genero;
        this.clasificacion = clasificacion;
        this.listFuncionPelicula = listFuncionPelicula;
    }

    public String getCodigoPel() {
        return codigoPel;
    }

    public void setCodigoPel(String codigoPel) {
        this.codigoPel = codigoPel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public EstadoPelicula getEstadoPelicula() {
        return estadoPelicula;
    }

    public void setEstadoPelicula(EstadoPelicula estadoPelicula) {
        this.estadoPelicula = estadoPelicula;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public List<Funcion> getListFuncionPelicula() {
        return listFuncionPelicula;
    }

    public void setListFuncionPelicula(List<Funcion> listFuncionPelicula) {
        this.listFuncionPelicula = listFuncionPelicula;
    }
}
