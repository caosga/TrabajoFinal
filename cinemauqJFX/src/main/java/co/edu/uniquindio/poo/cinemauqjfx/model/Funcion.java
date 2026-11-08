package co.edu.uniquindio.poo.cinemauqjfx.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Funcion {

    private String codigo;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDateTime fechaHora;
    private double valorBase;
    private EstadoFuncion estado;
    private List<Asiento> asientos;     // copia propia de los asientos de la sala

    public Funcion(String codigo, Pelicula pelicula, Sala sala, LocalDateTime fechaHora,
                   double valorBase) {
        this.codigo = codigo;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fechaHora = fechaHora;
        this.valorBase = valorBase;
        this.estado = EstadoFuncion.PROGRAMADA;

        // Cada funcion tiene sus propios asientos: un asiento ocupado a las 2:00 p. m.
        // debe seguir libre en la funcion de las 6:00 p. m. (composicion Funcion - Asiento)
        this.asientos = new ArrayList<>();
        for (Asiento a : sala.getAsientos()) {
            asientos.add(new Asiento(a.getCodigo(), a.getFila(), a.getNumero()));
        }
    }

    public int contarDisponibles() {
        int total = 0;
        for (Asiento a : asientos) {
            if (a.getEstado() == EstadoAsiento.DISPONIBLE) {
                total++;
            }
        }
        return total;
    }

    public int contarOcupados() {
        return asientos.size() - contarDisponibles();
    }

    public double porcentajeOcupacion() {
        if (asientos.isEmpty()) {
            return 0;
        }
        return contarOcupados() * 100.0 / asientos.size();
    }

    public Asiento buscarAsiento(String fila, int numero) {
        for (Asiento a : asientos) {
            if (a.getFila().equalsIgnoreCase(fila) && a.getNumero() == numero) {
                return a;
            }
        }
        return null;
    }

    public void cancelar() {
        this.estado = EstadoFuncion.CANCELADA;
    }

    public void finalizar() {
        this.estado = EstadoFuncion.FINALIZADA;
    }

    public String getCodigo() {
        return codigo;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public LocalDate getFecha() {
        return fechaHora.toLocalDate();
    }

    public LocalTime getHora() {
        return fechaHora.toLocalTime();
    }

    public double getValorBase() {
        return valorBase;
    }

    public EstadoFuncion getEstado() {
        return estado;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    @Override
    public String toString() {
        return pelicula.getNombre() + " | Sala " + sala.getNombre() + " | " + getFecha() + " "
                + getHora() + " | $" + (long) valorBase + " | disponibles: "
                + contarDisponibles() + " | " + estado;
    }
}
