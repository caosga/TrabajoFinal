package viewController;

import app.App;
import controller.ClienteController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Cliente;

/** Pantalla del cine para consultar los clientes registrados (solo consulta). */
public class ConsultaClientesViewController {

    private App app;
    private ClienteController clienteController;
    private final ObservableList<Cliente> listClientes = FXCollections.observableArrayList();

    @FXML
    private TextField txtBuscar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVerTodos;

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
    private TableColumn<Cliente, String> tbcCompras;

    @FXML
    void onBuscar() {
        try {
            String identificacion = Utilidades.leerTexto(txtBuscar, "la identificación a buscar");
            Cliente cliente = clienteController.buscar(identificacion);
            if (cliente == null) {
                Utilidades.mostrarError("No existe un cliente con la identificación " + identificacion + ".");
                return;
            }
            listClientes.setAll(cliente);   // la tabla muestra solo ese cliente
        } catch (IllegalArgumentException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onVerTodos() {
        txtBuscar.clear();
        listClientes.setAll(clienteController.listar());
    }

    @FXML
    void onVolver() {
        app.openMenuCine();
    }

    @FXML
    void initialize() {
        clienteController = new ClienteController(App.cine);
        tbcIdentificacion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        tbcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCorreo()));
        tbcTipo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getTipoCliente())));
        tbcCompras.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(clienteController.contarCompras(c.getValue()))));
        tblListCliente.setItems(listClientes);
        onVerTodos();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
