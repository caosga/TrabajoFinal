package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import co.edu.uniquindio.poo.cinemauqjfx.controller.SalaController;
import co.edu.uniquindio.poo.cinemauqjfx.model.CinemaException;
import co.edu.uniquindio.poo.cinemauqjfx.model.Sala;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * ViewController de la pantalla de salas.
 * Al registrar una sala se generan sus asientos (filas A, B, C... y numeros 1, 2, 3...).
 * Una vez creada, solo se puede cambiar el nombre: la capacidad no se modifica.
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
    private Button btnRegistrar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnDeshabilitar;

    @FXML
    private Button btnLimpiar;

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
        } catch (CinemaException | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onActualizarSala() {
        try {
            exigirSeleccion();
            salaController.modificar(selectedSala.getCodigo(), Utilidades.leerTexto(txtNombre, "el nombre"));
            refrescarTabla();
            limpiarSeleccion();
            Utilidades.mostrarInformacion("Sala actualizada correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onDeshabilitarSala() {
        try {
            exigirSeleccion();
            salaController.deshabilitar(selectedSala.getCodigo());
            refrescarTabla();
            limpiarSeleccion();
            Utilidades.mostrarInformacion("La sala quedó FUERA_DE_SERVICIO.");
        } catch (IllegalArgumentException | IllegalStateException e) {
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
        initDataBinding();
        tblListSala.setItems(listSalas);
        refrescarTabla();
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcFilas.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFilas())));
        tbcAsientosPorFila.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAsientosPorFila())));
        tbcCapacidad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCapacidad())));
        tbcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEstado())));
    }

    private void refrescarTabla() {
        listSalas.setAll(salaController.listar());
        tblListSala.refresh();
    }

    private void listenerSelection() {
        tblListSala.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedSala = newSelection;
            mostrarInformacionSala(selectedSala);
        });
    }

    private void mostrarInformacionSala(Sala sala) {
        if (sala != null) {
            txtCodigo.setText(sala.getCodigo());
            txtNombre.setText(sala.getNombre());
            txtFilas.setText(String.valueOf(sala.getFilas()));
            txtAsientosPorFila.setText(String.valueOf(sala.getAsientosPorFila()));
            // el código y la capacidad no se modifican
            txtCodigo.setEditable(false);
            txtFilas.setEditable(false);
            txtAsientosPorFila.setEditable(false);
        }
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
        txtCodigo.setEditable(true);
        txtFilas.setEditable(true);
        txtAsientosPorFila.setEditable(true);
    }

    public void setApp(App app) {
        this.app = app;
    }
}
