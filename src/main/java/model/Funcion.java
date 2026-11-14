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
     * Metodo para contar los asientos disponibles de esta funcion
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

    @Override
    public String toString() {
        return "Función [Película: " + thePelicula.getNombre() +
                ", Sala: " + theSala.getCodigoSala() +
                ", Fecha y hora: " + fechaHora +
                ", Precio: " + valorBase +
                ", Disponibles: " + contarAsientosDisponibles() + "]";
    }


    /**
     * Metodo para consultar una funcion y la disponibiidad de sus asientos
     */
    public String consultarDisponibilidadFuncion(Funcion funcionSeleccionada) {
        if (funcionSeleccionada == null) {
            throw new IllegalArgumentException("La función seleccionada no existe.");
        }

        StringBuilder reporte = new StringBuilder();

        reporte.append("Función: ").append(funcionSeleccionada.getThePelicula().getNombre()).append("\n");
        reporte.append("Sala: ").append(funcionSeleccionada.getTheSala().getCodigoSala()).append("\n");
        reporte.append("Hora: ").append(funcionSeleccionada.getFechaHora()).append("\n");
        reporte.append("----------------------------\n"); // Línea divisoria tal como en el diseño

        for (Asiento a : funcionSeleccionada.getListAsientoFuncion()) {
            String estadoTexto = a.isOcupado() ? "Ocupado" : "Disponible";
            reporte.append(a.getCodigoAsiento()).append(" ").append(estadoTexto).append("\n");
        }
        return reporte.toString();
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
