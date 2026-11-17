package model;

import exceptions.AsientoOcupadoException;
import exceptions.SalaNoDisponibleException;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public void registraVenta(Venta venta) {
        if (venta == null) {
            return;
        }
        this.listVentaFuncion.add(venta);

        /* Marca ocuapdo el asiento vendido en el boleto*/
        if (venta.getListBoleto() != null) {
            for (Boleto b : venta.getListBoleto()) {
                Asiento asientoBoleto = b.asiento();
                if (asientoBoleto != null) {
                    Asiento asientoEnFuncion = buscarAsientoEnFuncion(
                            asientoBoleto.getFila(),
                            asientoBoleto.getNumero()
                    );
                    if (asientoEnFuncion != null) {
                        asientoEnFuncion.setEstadoAsiento(EstadoAsiento.OCUPADO);
                    }
                }
            }
        }
    }

    public List<Boleto> verificaVenta(List<Boleto> boletosSolicitados)
            throws SalaNoDisponibleException, AsientoOcupadoException {
        /*  Verifica que si haya asientos disponibles para la funcion*/
        if (this.contarAsientosDisponibles() <= 0 || this.contarAsientosDisponibles() < boletosSolicitados.size()) {
            throw new SalaNoDisponibleException("No hay suficientes asientos disponibles para esta función.");
        }

        /* Valida que el cliente no tome un asiento ocupado o duplicado*/
        List<String> asientosRevisados = new ArrayList<>();
        for (Boleto b : boletosSolicitados) {
            Asiento asiento = b.asiento();
            if (asiento == null || !esAsientoDisponible(asiento) || asientosRevisados.contains(asiento.getCodigoAsiento())) {
                throw new AsientoOcupadoException("El asiento " + (asiento != null ? asiento.getCodigoAsiento() : "") + " ya está ocupado o duplicado.");
            }
            asientosRevisados.add(asiento.getCodigoAsiento());
        }
        return boletosSolicitados;
    }

    /**
     * Verifica si un asiento específico está disponible para esta función
     * @return true si está disponible, false si esta en una venta activa.
     */
    private boolean esAsientoDisponible(Asiento asientoBuscado) {
        if (asientoBuscado == null) {
            return false;
        }
        Asiento asientoEnFuncion = buscarAsientoEnFuncion( asientoBuscado.getFila(),  asientoBuscado.getNumero() );

        if (asientoEnFuncion == null) {
            return false;
        }
        return asientoEnFuncion.getEstadoAsiento() == EstadoAsiento.DISPONIBLE;
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
        int disponibles = 0;
        for (Asiento a : this.listAsientoFuncion) {
            if (a.getEstadoAsiento() == EstadoAsiento.DISPONIBLE) {
                disponibles++;
            }
        }
        return disponibles;
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
        reporte.append("----------------------------\n");

        for (Asiento a : funcionSeleccionada.getListAsientoFuncion()) {
            String estadoTexto = (a.getEstadoAsiento() == EstadoAsiento.OCUPADO) ? "Ocupado" : "Disponible";
            reporte.append(a.getCodigoAsiento()).append(" ").append(estadoTexto).append("\n");
        }
        return reporte.toString();
    }

    /**
     * Metodo para liberar asientos asociados a una lista de boletos de una venta cancelada.
     */
    public void liberarAsientos(List<Boleto> boletosCancelados) {
        if (boletosCancelados == null) {
            return;
        }

        for (Boleto b : boletosCancelados) {
            Asiento asientoBoleto = b.asiento();
            if (asientoBoleto != null) {
                Asiento asientoEnFuncion = buscarAsientoEnFuncion(asientoBoleto.getFila(), asientoBoleto.getNumero());

                if (asientoEnFuncion != null) {
                    asientoEnFuncion.setEstadoAsiento(EstadoAsiento.DISPONIBLE);
                }
            }
        }
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
