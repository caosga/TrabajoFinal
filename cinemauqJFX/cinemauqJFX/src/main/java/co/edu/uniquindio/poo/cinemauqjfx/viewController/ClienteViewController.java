package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import co.edu.uniquindio.poo.cinemauqjfx.controller.ClienteController;
import co.edu.uniquindio.poo.cinemauqjfx.model.CinemaException;
import co.edu.uniquindio.poo.cinemauqjfx.model.Cliente;
import co.edu.uniquindio.poo.cinemauqjfx.model.TipoCliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/** Pantalla del operador para registrar clientes antes de venderles una entrada. */
public class ClienteViewController {

    private App app;
    private ClienteController clienteController;
    private final ObservableList<Cliente> listClientes = FXCollections.observableArrayList();

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private ComboBox<TipoCliente> cmbTipoCliente;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Cliente> tblListCliente;

    @FXML
    private TableColumn<Cliente, String> tbcIdentificacion;

    @FXML
    private TableColumn<Cliente, String> tbcNombre;

    @FXML
    private TableColumn<Cliente, String> tbcCorreo;

    @FXML
    private TableColumn<Cliente, String> tbcTipo;

    @FXML
    void onRegistrarCliente() {
        try {
            String correo = Utilidades.leerTexto(txtCorreo, "el correo");
            if (!correo.contains("@")) {
                throw new IllegalArgumentException("El correo debe contener @.");
            }
            clienteController.registrar(
                    Utilidades.leerTexto(txtIdentificacion, "la identificación"),
                    Utilidades.leerTexto(txtNombre, "el nombre"),
                    correo,
                    Utilidades.leerSeleccion(cmbTipoCliente, "el tipo de cliente"));
            refrescarTabla();
            limpiar();
            Utilidades.mostrarInformacion("Cliente registrado correctamente.");
        } catch (CinemaException | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onLimpiar() {
        limpiar();
    }

    @FXML
    void onVolver() {
        app.openMenuOperador();
    }

    @FXML
    void initialize() {
        clienteController = new ClienteController(App.cine);
        cmbTipoCliente.setItems(FXCollections.observableArrayList(TipoCliente.values()));
        Utilidades.configurarCombo(cmbTipoCliente, "Seleccione", TipoCliente::toString);
        tbcIdentificacion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        tbcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCorreo()));
        tbcTipo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getTipoCliente())));
        tblListCliente.setItems(listClientes);
        refrescarTabla();
    }

    private void refrescarTabla() {
        listClientes.setAll(clienteController.listar());
        tblListCliente.refresh();
    }

    private void limpiar() {
        txtIdentificacion.clear();
        txtNombre.clear();
        txtCorreo.clear();
        cmbTipoCliente.setValue(null);
    }

    public void setApp(App app) {
        this.app = app;
    }
}
