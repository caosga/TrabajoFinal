package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import co.edu.uniquindio.poo.cinemauqjfx.controller.TarifaController;
import co.edu.uniquindio.poo.cinemauqjfx.model.Tarifa;
import co.edu.uniquindio.poo.cinemauqjfx.model.TipoEntrada;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * ViewController de la pantalla de tarifas.
 * Hay una tarifa por cada tipo de entrada: si ya existe se actualiza su valor y si no, se crea.
 */
public class TarifaViewController {

    private App app;
    private TarifaController tarifaController;
    private final ObservableList<Tarifa> listTarifas = FXCollections.observableArrayList();

    @FXML
    private ComboBox<TipoEntrada> cmbTipo;

    @FXML
    private TextField txtValor;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Tarifa> tblListTarifa;

    @FXML
    private TableColumn<Tarifa, String> tbcTipo;

    @FXML
    private TableColumn<Tarifa, String> tbcValor;

    // ------------------------------------------------------------------
    // Eventos de los botones
    // ------------------------------------------------------------------
    @FXML
    void onGuardarTarifa() {
        try {
            TipoEntrada tipo = Utilidades.leerSeleccion(cmbTipo, "un tipo de entrada");
            double valor = Utilidades.leerDecimal(txtValor, "el valor");
            if (valor <= 0) {
                throw new IllegalArgumentException("El valor debe ser mayor que cero.");
            }
            tarifaController.definir(tipo, valor);
            refrescarTabla();
            limpiar();
            Utilidades.mostrarInformacion("Tarifa guardada correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onLimpiar() {
        limpiar();
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
        tarifaController = new TarifaController(App.cine);
        cmbTipo.setItems(FXCollections.observableArrayList(TipoEntrada.values()));
        Utilidades.configurarCombo(cmbTipo, "Seleccione", TipoEntrada::toString);
        initDataBinding();
        tblListTarifa.setItems(listTarifas);
        refrescarTabla();
        listenerSelection();
    }

    private void initDataBinding() {
        tbcTipo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTipoEntrada())));
        tbcValor.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("$%,.0f", cellData.getValue().getValor())));
    }

    private void refrescarTabla() {
        listTarifas.setAll(tarifaController.listar());
        tblListTarifa.refresh();
    }

    private void listenerSelection() {
        tblListTarifa.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cmbTipo.setValue(newSelection.getTipoEntrada());
                txtValor.setText(String.valueOf((long) newSelection.getValor()));
            }
        });
    }

    private void limpiar() {
        tblListTarifa.getSelectionModel().clearSelection();
        cmbTipo.setValue(null);
        txtValor.clear();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
