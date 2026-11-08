package co.edu.uniquindio.poo.cinemauqjfx.model;

import java.time.LocalDateTime;

/**
 * Una venta representa la adquisicion de UNA entrada, asi que hace de boleta.
 * Cuando se cancela no se borra: queda en estado CANCELADA para conservar el historial.
 */
public class Venta {

    private String codigo;
    private Cliente cliente;
    private Funcion funcion;
    private Asiento asiento;
    private TipoEntrada tipoEntrada;
    private LocalDateTime fechaHora;
    private double valorPagado;
    private EstadoVenta estado;

    public Venta(String codigo, Cliente cliente, Funcion funcion, Asiento asiento,
                 TipoEntrada tipoEntrada, double valorPagado) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asiento = asiento;
        this.tipoEntrada = tipoEntrada;
        this.valorPagado = valorPagado;
        this.fechaHora = LocalDateTime.now();
        this.estado = EstadoVenta.ACTIVA;
    }

    public void cancelar() {
        this.estado = EstadoVenta.CANCELADA;
    }

    public boolean estaActiva() {
        return estado == EstadoVenta.ACTIVA;
    }

    /** Informacion de la entrada lista para mostrar al cliente. */
    public String generarEntrada() {
        return "===== ENTRADA " + codigo + " =====\n"
                + "Pelicula: " + funcion.getPelicula().getNombre() + "\n"
                + "Sala:     " + funcion.getSala().getNombre() + "\n"
                + "Fecha:    " + funcion.getFecha() + "  Hora: " + funcion.getHora() + "\n"
                + "Asiento:  " + asiento.getCodigo() + "\n"
                + "Cliente:  " + cliente.getNombre() + "\n"
                + "Tipo:     " + tipoEntrada + "\n"
                + "Valor:    $" + (long) valorPagado + "\n"
                + "Vendida:  " + fechaHora.withNano(0) + "\n"
                + "Estado:   " + estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public TipoEntrada getTipoEntrada() {
        return tipoEntrada;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return codigo + " | " + cliente.getNombre() + " | " + asiento.getCodigo() + " | $"
                + (long) valorPagado + " | " + estado;
    }
}
