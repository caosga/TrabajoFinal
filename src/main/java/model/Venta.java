package model;

import java.time.LocalDateTime;
import java.util.List;

public class Venta {
    private String codigoVenta;
    private LocalDateTime fechaHora;
    private EstadoVenta estadoVenta;
    /**
     * Relaciones de clase Venta
     */
    private Tarifa theTarifa;
    private Funcion theFuncion;
    private List<Asiento> listAsientoVenta;
    private Cliente theCliente;
    private Operario theOperario;
    private List<Boleto>listBoleto;

    /**
     * Metodo constructor para la clase Venta
     * @param codigoVenta
     * @param fechaHora
     * @param estadoVenta
     * @param theTarifa
     * @param theFuncion
     * @param listAsientoVenta
     * @param theCliente
     */
    public Venta(String codigoVenta, LocalDateTime fechaHora, EstadoVenta estadoVenta,
                 Tarifa theTarifa, Funcion theFuncion, List<Asiento>listAsientoVenta, Cliente theCliente, Operario theOperario,
                 List<Boleto>listBoleto) {
        this.codigoVenta = codigoVenta;
        this.fechaHora = fechaHora;
        this.estadoVenta = estadoVenta;
        this.theTarifa = theTarifa;
        this.theFuncion = theFuncion;
        this.listAsientoVenta = listAsientoVenta;
        this.theCliente = theCliente;
        this.theOperario = theOperario;
        this.listBoleto=listBoleto;
    }

    /**
     * Metodo para calcular el valor total de la venta
     * @return
     */
    public double calcularValorTotal() {
        double total = 0.0;
        for (Boleto b : this.listBoleto) {
            total += b.tarifa().getValor();
        }
        return total;
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

    public List<Asiento> getListAsientoVenta() {
        return listAsientoVenta;
    }

    public void setListAsientoVenta(List<Asiento> listAsientoVenta) {
        this.listAsientoVenta = listAsientoVenta;
    }

    public Cliente getTheCliente() {
        return theCliente;
    }

    public void setTheCliente(Cliente theCliente) {
        this.theCliente = theCliente;
    }

    public Operario getTheOperario() {
        return theOperario;
    }

    public void setTheOperario(Operario theOperario) {
        this.theOperario = theOperario;
    }

    public List<Boleto> getListBoleto() {
        return listBoleto;
    }

    public void setListBoleto(List<Boleto> listBoleto) {
        this.listBoleto = listBoleto;
    }
}
