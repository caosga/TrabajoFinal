package viewController;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.Asiento;
import model.EstadoAsiento;
import model.Funcion;

import java.util.List;
import java.util.function.Consumer;

/**
 * Dibuja los asientos de una funcion en un GridPane: una fila por letra (A, B, C...)
 * y una columna por numero. Un asiento ocupado se muestra como "A1 (X)" y no se puede
 * presionar; un asiento elegido se muestra como "A1 *".
 * Si alHacerClic es null la cuadricula es solo de consulta.
 */
public final class CuadriculaAsientos {

    private CuadriculaAsientos() {
    }

    public static void dibujar(GridPane grid, Funcion funcion, List<Asiento> elegidos, Consumer<Asiento> alHacerClic) {
        grid.getChildren().clear();
        if (funcion == null) {
            return;
        }
        for (Asiento asiento : funcion.getListAsientoFuncion()) {
            int fila = asiento.getFila().toUpperCase().charAt(0) - 'A';   // A = 0, B = 1, C = 2...
            int columna = asiento.getNumero() - 1;                         // el asiento 1 va en la columna 0
            String texto = asiento.getFila() + asiento.getNumero();
            Button boton = new Button(texto);
            boton.setPrefSize(70, 34);
            if (asiento.getEstadoAsiento() == EstadoAsiento.OCUPADO) {
                boton.setText(texto + " (X)");
                boton.setDisable(true);
            } else if (elegidos != null && elegidos.contains(asiento)) {
                boton.setText(texto + " *");
                boton.setStyle("-fx-base: #8fd18f;");
            }
            if (alHacerClic != null && asiento.getEstadoAsiento() == EstadoAsiento.DISPONIBLE) {
                boton.setOnAction(e -> alHacerClic.accept(asiento));
            }
            grid.add(boton, columna, fila);
        }
    }
}
