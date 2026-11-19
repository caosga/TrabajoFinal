package controller;

import exceptions.ElementoDuplicadoException;
import model.*;

import java.util.ArrayList;
import java.util.List;

/** Intermediario entre la pantalla de salas y CinemaUQ. */
public class SalaController {

    private final CinemaUQ cine;

    public SalaController(CinemaUQ cine) {
        this.cine = cine;
    }

    public void registrar(String codigo, String nombre, int filas, int asientosPorFila)
            throws ElementoDuplicadoException {
        cine.registrarSala(crearSala(codigo, nombre, filas, asientosPorFila));
    }

    /** Solo cambia el nombre: la capacidad y el estado se mantienen. */
    public String modificar(Sala sala, String nuevoNombre) {
        return cine.modificarSala(sala.getCodigoSala(), nuevoNombre, sala.getCapcacidad(), sala.getEstado());
    }

    public String deshabilitar(String codigo) {
        return cine.deshabilitarSala(codigo);
    }

    public String habilitar(String codigo) {
        return cine.habilitarSala(codigo);
    }

    public Sala buscar(String codigo) {
        return cine.buscarSala(codigo);
    }

    public List<Sala> listar() {
        return cine.consultarSalas();
    }

    /**
     * Crea una sala con sus asientos: filas A, B, C... y numeros 1, 2, 3...
     * El codigo de cada asiento queda como "S01-A1", igual que en la clase Main.
     */
    public static Sala crearSala(String codigo, String nombre, int filas, int asientosPorFila) {
        if (filas <= 0 || asientosPorFila <= 0) {
            throw new IllegalArgumentException("Las filas y los asientos por fila deben ser mayores que cero.");
        }
        if (filas > 26) {
            throw new IllegalArgumentException("Una sala puede tener máximo 26 filas (de la A a la Z).");
        }
        Sala sala = new Sala(codigo, nombre, filas * asientosPorFila, EstadoSala.DISPONIBLE,
                new ArrayList<>(), new ArrayList<>());
        for (int i = 0; i < filas; i++) {
            String letra = String.valueOf((char) ('A' + i));
            for (int j = 1; j <= asientosPorFila; j++) {
                sala.getListAsiento().add(new Asiento(codigo + "-" + letra + j, letra, j,
                        EstadoAsiento.DISPONIBLE, null, new ArrayList<>()));
            }
        }
        return sala;
    }
}
