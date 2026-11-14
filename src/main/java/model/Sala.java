package model;
import java.util.List;

public class Sala {
    private String codigoSala;
    private String nombre;
    private int capacidad;
    private EstadoSala estado;

    /**
     * Relaciones de la clase Sala
     */
    private List<Asiento>listAsiento;
    private List<Funcion>listFuncionSala;

    /**
     * Metodo constructor de la clase Sala
     * @param codigoSala
     * @param nombre
     * @param capcacidad
     * @param estado
     * @param listAsiento
     * @param listFuncionSala
     */
    public Sala(String codigoSala, String nombre, int capcacidad,
                EstadoSala estado, List<Asiento> listAsiento, List<Funcion> listFuncionSala) {
        this.codigoSala = codigoSala;
        this.nombre = nombre;
        this.capacidad = capcacidad;
        this.estado = estado;
        this.listAsiento = listAsiento;
        this.listFuncionSala = listFuncionSala;
    }

    public String getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapcacidad() {
        return capacidad;
    }

    public void setCapcacidad(int capcacidad) {
        this.capacidad = capcacidad;
    }

    public EstadoSala getEstado() {
        return estado;
    }

    public void setEstado(EstadoSala estado) {
        this.estado = estado;
    }

    public List<Asiento> getListAsiento() {
        return listAsiento;
    }

    public void setListAsiento(List<Asiento> listAsiento) {
        this.listAsiento = listAsiento;
    }

    public List<Funcion> getListFuncionSala() {
        return listFuncionSala;
    }

    public void setListFuncionSala(List<Funcion> listFuncionSala) {
        this.listFuncionSala = listFuncionSala;
    }
}
