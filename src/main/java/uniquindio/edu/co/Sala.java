package uniquindio.edu.co;

import java.util.ArrayList;
import java.util.List;

public class Sala {
private String codigioSala;
private String nombre ;
private int capacidad;
private int filas;
private int asientosPorFila;
private EstadoSala estado;
private List<Asiento> asientos;

    public Sala(String codigoSala, String nombre, int filas, int asientosPorFila) {
        if (filas < 1 || filas > 26) {
            throw new IllegalArgumentException("Las filas deben estar entre 1 y 26 (letras A-Z)");
        }
        if (asientosPorFila < 1) {
            throw new IllegalArgumentException("Debe haber al menos un asiento por fila");
        }
        this.codigioSala= codigioSala;
        this.nombre = nombre;
        this.filas = filas;
        this.asientosPorFila = asientosPorFila;
        this.capacidad = filas * asientosPorFila;
        this.estado = EstadoSala.DISPONIBLE;
        this.asientos = new ArrayList<>();
        generarAsientos();
    }
    /**
     * Crea los asientos A1, A2... B1, B2... segun filas y asientos por fila.
     */
    private void generarAsientos() {
        for (int i = 0; i < filas; i++) {
            String letra = String.valueOf((char) ('A' + i));   // 0->A, 1->B, 2->C...
            for (int numero = 1; numero <= asientosPorFila; numero++) {
                asientos.add(new Asiento(letra + numero, letra, numero));
            }
        }
    }
    public Asiento buscarAsiento(String fila, int numero) {
        for (Asiento a : asientos) {
            if (a.getFila().equalsIgnoreCase(fila) && a.getNumero() == numero) {
                return a;
            }
        }
        return null;
    }

    public boolean estaDisponible() {
        return estado == EstadoSala.DISPONIBLE;
    }

    public void deshabilitar() {
        this.estado = EstadoSala.FUERA_DE_SERVICIO;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigioSala() {
        return codigioSala;
    }

    public String getNombre() {
        return nombre;
    }

    public int getFilas() {
        return filas;
    }

    public int getAsientosPorFila() {
        return asientosPorFila;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public EstadoSala getEstado() {
        return estado;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    @Override
    public String toString() {
        return "Sala " + nombre + " (" + codigioSala + ") | capacidad " + capacidad + " | " + estado;
    }

    }

