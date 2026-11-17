package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.model.Asiento;
import co.edu.uniquindio.poo.cinemauqjfx.model.EstadoAsiento;
import co.edu.uniquindio.poo.cinemauqjfx.model.Funcion;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

/**
 * Dibuja los asientos de una funcion en un GridPane: una fila por letra (A, B, C...)
 * y una columna por numero. Un asiento ocupado se muestra como "A1 (X)" y no se puede
 * presionar; el asiento seleccionado se muestra como "A1 *".
 * Si alElegir es null la cuadricula es solo de consulta.
 */
public final class CuadriculaAsientos {

    private CuadriculaAsientos() {
    }

    public static void dibujar(GridPane grid, Funcion funcion, Asiento seleccionado, Consumer<Asiento> alElegir) {
        grid.getChildren().clear();
        if (funcion == null) {
            return;
        }
        for (Asiento asiento : funcion.getAsientos()) {
            int fila = asiento.getFila().charAt(0) - 'A';      // A = 0, B = 1, C = 2...
            int columna = asiento.getNumero() - 1;             // el asiento 1 va en la columna 0
            Button boton = new Button(asiento.getCodigo());
            boton.setPrefSize(70, 34);
            if (asiento.getEstado() == EstadoAsiento.OCUPADO) {
                boton.setText(asiento.getCodigo() + " (X)");
                boton.setDisable(true);
            } else if (seleccionado != null && seleccionado.getCodigo().equals(asiento.getCodigo())) {
                boton.setText(asiento.getCodigo() + " *");
            }
            if (alElegir != null && asiento.getEstado() == EstadoAsiento.DISPONIBLE) {
                boton.setOnAction(e -> alElegir.accept(asiento));
            }
            grid.add(boton, columna, fila);
        }
    }
}
