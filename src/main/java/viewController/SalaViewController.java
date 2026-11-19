package viewController;

import app.App;
import controller.SalaController;
import exceptions.ElementoDuplicadoException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Asiento;
import model.Sala;

import java.util.ArrayList;
import java.util.List;

/**
 * Pantalla de gestion de salas (administrador): registrar, buscar, modificar,
 * deshabilitar y habilitar. Al registrar una sala se crean sus asientos
 * (filas A, B, C... y numeros 1, 2, 3...). Despues solo se puede cambiar el nombre.
 */
public class SalaViewController {

    private App app;
    private SalaController salaController;
    private final ObservableList<Sala> listSalas = FXCollections.observableArrayList();
    private Sala selectedSala;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtFilas;

    @FXML
    private TextField txtAsientosPorFila;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnDeshabilitar;

    @FXML
    private Button btnHabilitar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Sala> tblListSala;

    @FXML
    private TableColumn<Sala, String> tbcCodigo;

    @FXML
    private TableColumn<Sala, String> tbcNombre;

    @FXML
    private TableColumn<Sala, String> tbcFilas;

    @FXML
    private TableColumn<Sala, String> tbcAsientosPorFila;

    @FXML
    private TableColumn<Sala, String> tbcCapacidad;

    @FXML
    private TableColumn<Sala, String> tbcEstado;

    // ------------------------------------------------------------------
    // Eventos de los botones
    // ------------------------------------------------------------------
    @FXML
    void onRegistrarSala() {
        try {
            salaController.registrar(
                    Utilidades.leerTexto(txtCodigo, "el código"),
                    Utilidades.leerTexto(txtNombre, "el nombre"),
                    Utilidades.leerEntero(txtFilas, "el número de filas"),
                    Utilidades.leerEntero(txtAsientosPorFila, "los asientos por fila"));
            refrescarTabla();
            limpiarSeleccion();
            Utilidades.mostrarInformacion("Sala registrada correctamente.");
        } catch (ElementoDuplicadoException | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onActualizarSala() {
        try {
            exigirSeleccion();
            String mensaje = salaController.modificar(selectedSala, Utilidades.leerTexto(txtNombre, "el nombre"));
            refrescarTabla();
            limpiarSeleccion();
            Utilidades.mostrarInformacion(mensaje);
        } catch (IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onDeshabilitarSala() {
        try {
            exigirSeleccion();
            String mensaje = salaController.deshabilitar(selectedSala.getCodigoSala());
            refrescarTabla();
            limpiarSeleccion();
            Utilidades.mostrarInformacion(mensaje);
        } catch (IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onHabilitarSala() {
        try {
            exigirSeleccion();
            String mensaje = salaController.habilitar(selectedSala.getCodigoSala());
            refrescarTabla();
            limpiarSeleccion();
            Utilidades.mostrarInformacion(mensaje);
        } catch (IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onBuscar() {
        try {
            String codigo = Utilidades.leerTexto(txtBuscar, "el código a buscar");
            Sala sala = salaController.buscar(codigo);
            if (sala == null) {
                Utilidades.mostrarError("No existe una sala con el código " + codigo + ".");
                return;
            }
            tblListSala.getSelectionModel().select(sala);   // la selecciona y la pasa al formulario
        } catch (IllegalArgumentException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void onVolver() {
        app.openMenuAdmin();
    }

    // ------------------------------------------------------------------
    // Inicializacion
    // ------------------------------------------------------------------
    @FXML
    void initialize() {
        salaController = new SalaController(App.cine);

        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigoSala()));
        tbcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcFilas.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(contarFilas(c.getValue()))));
        tbcAsientosPorFila.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(asientosPorFila(c.getValue()))));
        tbcCapacidad.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCapcacidad())));
        tbcEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstado())));

        tblListSala.setItems(listSalas);
        refrescarTabla();

        tblListSala.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nueva) -> {
            selectedSala = nueva;
            mostrarInformacionSala(nueva);
        });
    }

    private void refrescarTabla() {
        listSalas.setAll(salaController.listar());
        tblListSala.refresh();
    }

    private void mostrarInformacionSala(Sala sala) {
        if (sala != null) {
            txtCodigo.setText(sala.getCodigoSala());
            txtNombre.setText(sala.getNombre());
            txtFilas.setText(String.valueOf(contarFilas(sala)));
            txtAsientosPorFila.setText(String.valueOf(asientosPorFila(sala)));
            // el código y la capacidad no se modifican
            txtCodigo.setEditable(false);
            txtFilas.setEditable(false);
            txtAsientosPorFila.setEditable(false);
        }
    }

    /** Cuenta cuantas letras de fila distintas tiene la sala (A, B, C...). */
    private int contarFilas(Sala sala) {
        List<String> filas = new ArrayList<>();
        for (Asiento a : sala.getListAsiento()) {
            if (!filas.contains(a.getFila())) {
                filas.add(a.getFila());
            }
        }
        return filas.size();
    }

    private int asientosPorFila(Sala sala) {
        int filas = contarFilas(sala);
        return filas == 0 ? 0 : sala.getListAsiento().size() / filas;
    }

    private void exigirSeleccion() {
        if (selectedSala == null) {
            throw new IllegalArgumentException("Seleccione una sala de la tabla.");
        }
    }

    private void limpiarSeleccion() {
        tblListSala.getSelectionModel().clearSelection();
        selectedSala = null;
        txtCodigo.clear();
        txtNombre.clear();
        txtFilas.clear();
        txtAsientosPorFila.clear();
        txtBuscar.clear();
        txtCodigo.setEditable(true);
        txtFilas.setEditable(true);
        txtAsientosPorFila.setEditable(true);
    }

    public void setApp(App app) {
        this.app = app;
    }
}
