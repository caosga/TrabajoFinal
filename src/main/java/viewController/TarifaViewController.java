package viewController;

import app.App;
import controller.TarifaController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Tarifa;
import model.TipoEntrada;

/**
 * Pantalla de tarifas (administrador): registrar y consultar.
 * Hay una tarifa por tipo de entrada: si el tipo ya tiene tarifa, el boton Guardar
 * modifica su valor (modificarTarifa); si no, la registra (registrarTarifa).
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

    @FXML
    void onGuardarTarifa() {
        try {
            TipoEntrada tipo = Utilidades.leerSeleccion(cmbTipo, "un tipo de entrada");
            double valor = Utilidades.leerDecimal(txtValor, "el valor");
            if (valor <= 0) {
                throw new IllegalArgumentException("El valor debe ser mayor que cero.");
            }
            String mensaje;
            if (App.cine.buscarTarifa(tipo) == null) {
                mensaje = tarifaController.registrar(tipo, valor);
            } else {
                mensaje = tarifaController.modificar(tipo, valor);
            }
            refrescarTabla();
            limpiar();
            Utilidades.mostrarInformacion(mensaje);
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

    @FXML
    void initialize() {
        tarifaController = new TarifaController(App.cine);
        cmbTipo.setItems(FXCollections.observableArrayList(TipoEntrada.values()));
        Utilidades.configurarCombo(cmbTipo, "Seleccione", TipoEntrada::toString);

        tbcTipo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getTipoEntrada())));
        tbcValor.setCellValueFactory(c -> new SimpleStringProperty(Utilidades.pesos(c.getValue().getValor())));
        tblListTarifa.setItems(listTarifas);
        refrescarTabla();

        tblListTarifa.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nueva) -> {
            if (nueva != null) {
                cmbTipo.setValue(nueva.getTipoEntrada());
                txtValor.setText(String.valueOf((long) nueva.getValor()));
            }
        });
    }

    private void refrescarTabla() {
        listTarifas.setAll(tarifaController.listar());
        tblListTarifa.refresh();
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
