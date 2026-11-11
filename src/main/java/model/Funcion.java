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

    /**
     * Metodo para registrar una venta
     * @param venta
     */
    public void registraVenta(Venta venta){
        this.listVentaFuncion.add(venta);
    }

    /**
     * Metodo para verificar si los asientos estan disponibles para la venta
     */
    public List<Boleto>verificaVenta(List<Boleto>boletoComprado){
        for (Boleto b : boletoComprado) {
            Asiento asientoReal = buscarAsientoEnFuncion(b.asiento().getFila(), b.asiento().getNumero());
            if (asientoReal == null) {
                throw new IllegalArgumentException("El asiento "+ b.asiento().getFila() +"-"+ b.asiento().getNumero() + " no existe.");
            }
            if (asientoReal.isOcupado()) {
                throw new IllegalStateException("El asiento "+ asientoReal.getFila() +"-"+ asientoReal.getNumero() + " ya se encuentra ocupado.");
            }

            asientoReal.setOcupado(true);
        }
        return boletoComprado;
    }

    /**
     * Metodo para buscar el asiento de la funcion
     * @param fila
     * @param numero
     * @return
     */
    private Asiento buscarAsientoEnFuncion(String fila, int numero) {
        for (Asiento a : this.listAsientoFuncion) {
            if (a.getFila().equalsIgnoreCase(fila) && a.getNumero() == numero) {
                return a;
            }
        }
        return null;
    }

    /**
     * Metodo para contar los asientos disponibles de una funcion
     * @return
     */
    public int contarAsientosDisponibles() {
        int contador=0;
        for (Asiento a : this.listAsientoFuncion) {
            if (!a.isOcupado()){
                contador++;
            }
        }
        return contador;
    }

    /**
     * Metodo para consultar la disponibilidad de una funcion indicada
     * @param funcionSeleccionada
     * @return
     */
    public String consultarDisponibilidadFuncion(Funcion funcionSeleccionada) {
        if (funcionSeleccionada == null) {
            throw new IllegalArgumentException("La función seleccionada no existe.");
        }

        int totalAsientos= 0;
        int asientosDisponibles= 0;
        int asientosOcupados= 0;

        for (Asiento a : funcionSeleccionada.getListAsientoFuncion()) {
            totalAsientos++;
            if (a.isOcupado()){
                asientosOcupados++;
            } else {
                asientosDisponibles++;
            }
        }

        return "=== DISPONIBILIDAD DE LA FUNCIÓN ===" +
                "\nTotal de asientos: " + totalAsientos +
                "\nAsientos disponibles: " + asientosDisponibles +
                "\nAsientos ocupados: " + asientosOcupados;
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
