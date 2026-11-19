package viewController;

import model.Funcion;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * Metodos de apoyo que comparten todos los ViewController:
 * ventanas de mensaje, lectura y validacion de campos y configuracion de ComboBox.
 */
public final class Utilidades {

    private Utilidades() {
    }

    public static void mostrarError(String mensaje) {
        mostrar(Alert.AlertType.ERROR, "Error", mensaje);
    }

    public static void mostrarInformacion(String mensaje) {
        mostrar(Alert.AlertType.INFORMATION, "Información", mensaje);
    }

    /** Pregunta Aceptar / Cancelar y devuelve true si el usuario acepta. */
    public static boolean confirmar(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        return alerta.showAndWait().filter(boton -> boton == ButtonType.OK).isPresent();
    }

    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /** Texto corto de una funcion para mostrar en los desplegables. */
    public static String textoFuncion(Funcion f) {
        return f.getCodigoFuncion() + " - " + f.getThePelicula().getNombre() + " - " + f.getTheSala().getNombre()
                + " - " + f.getFechaHora().format(FORMATO_FECHA_HORA);
    }

    /** Muestra un valor en pesos, por ejemplo $18.000 */
    public static String pesos(double valor) {
        return String.format("$%,.0f", valor);
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static String leerTexto(TextField campo, String nombreCampo) {
        String valor = campo.getText() == null ? "" : campo.getText().trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("Debe escribir " + nombreCampo + ".");
        }
        return valor;
    }

    public static <T> T leerSeleccion(ComboBox<T> combo, String nombreCampo) {
        if (combo.getValue() == null) {
            throw new IllegalArgumentException("Debe seleccionar " + nombreCampo + ".");
        }
        return combo.getValue();
    }

    public static int leerEntero(TextField campo, String nombreCampo) {
        String texto = leerTexto(campo, nombreCampo);
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Revise " + nombreCampo + ": debe ser un número entero.");
        }
    }

    public static double leerDecimal(TextField campo, String nombreCampo) {
        String texto = leerTexto(campo, nombreCampo).replace(",", ".");
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Revise " + nombreCampo + ": debe ser un número.");
        }
    }

    /**
     * Configura como se ve un ComboBox: el texto de cada opcion y un texto de ayuda
     * que reaparece cada vez que el combo se vacia (setValue(null)), cosa que JavaFX
     * no hace solo.
     */
    public static <T> void configurarCombo(ComboBox<T> combo, String textoVacio, Function<T, String> texto) {
        combo.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? textoVacio : texto.apply(item));
            }
        });
        combo.setCellFactory(lista -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : texto.apply(item));
            }
        });
    }
}
