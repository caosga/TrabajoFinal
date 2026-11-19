package viewController;

import app.App;
import controller.AdministradorController;
import exceptions.ElementoDuplicadoException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Administrador;

/** Pantalla del cine para registrar y consultar administradores. */
public class AdministradorViewController {

    private App app;
    private AdministradorController administradorController;
    private final ObservableList<Administrador> listAdministradores = FXCollections.observableArrayList();

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtSalario;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Administrador> tblListAdministrador;

    @FXML
    private TableColumn<Administrador, String> tbcIdentificacion;

    @FXML
    private TableColumn<Administrador, String> tbcNombre;

    @FXML
    private TableColumn<Administrador, String> tbcCorreo;

    @FXML
    private TableColumn<Administrador, String> tbcSalario;

    @FXML
    void onRegistrarAdministrador() {
        try {
            String correo = Utilidades.leerTexto(txtCorreo, "el correo");
            if (!correo.contains("@")) {
                throw new IllegalArgumentException("El correo debe contener @.");
            }
            double salario = Utilidades.leerDecimal(txtSalario, "el salario");
            if (salario <= 0) {
                throw new IllegalArgumentException("El salario debe ser mayor que cero.");
            }
            String mensaje = administradorController.registrar(
                    Utilidades.leerTexto(txtIdentificacion, "la identificación"),
                    Utilidades.leerTexto(txtNombre, "el nombre"),
                    correo,
                    salario);
            refrescarTabla();
            limpiar();
            Utilidades.mostrarInformacion(mensaje);
        } catch (ElementoDuplicadoException | IllegalArgumentException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onLimpiar() {
        limpiar();
    }

    @FXML
    void onVolver() {
        app.openMenuCine();
    }

    @FXML
    void initialize() {
        administradorController = new AdministradorController(App.cine);
        tbcIdentificacion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        tbcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCorreo()));
        tbcSalario.setCellValueFactory(c -> new SimpleStringProperty(Utilidades.pesos(c.getValue().getSalario())));
        tblListAdministrador.setItems(listAdministradores);
        refrescarTabla();
    }

    private void refrescarTabla() {
        listAdministradores.setAll(administradorController.listar());
        tblListAdministrador.refresh();
    }

    private void limpiar() {
        txtIdentificacion.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtSalario.clear();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
