package viewController;

import app.App;
import controller.OperarioController;
import exceptions.ElementoDuplicadoException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Operario;

/** Pantalla del cine para registrar y consultar operarios. */
public class OperarioViewController {

    private App app;
    private OperarioController operarioController;
    private final ObservableList<Operario> listOperarios = FXCollections.observableArrayList();

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
    private TableView<Operario> tblListOperario;

    @FXML
    private TableColumn<Operario, String> tbcIdentificacion;

    @FXML
    private TableColumn<Operario, String> tbcNombre;

    @FXML
    private TableColumn<Operario, String> tbcCorreo;

    @FXML
    private TableColumn<Operario, String> tbcSalario;

    @FXML
    void onRegistrarOperario() {
        try {
            String correo = Utilidades.leerTexto(txtCorreo, "el correo");
            if (!correo.contains("@")) {
                throw new IllegalArgumentException("El correo debe contener @.");
            }
            double salario = Utilidades.leerDecimal(txtSalario, "el salario");
            if (salario <= 0) {
                throw new IllegalArgumentException("El salario debe ser mayor que cero.");
            }
            String mensaje = operarioController.registrar(
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
        operarioController = new OperarioController(App.cine);
        tbcIdentificacion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        tbcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCorreo()));
        tbcSalario.setCellValueFactory(c -> new SimpleStringProperty(Utilidades.pesos(c.getValue().getSalario())));
        tblListOperario.setItems(listOperarios);
        refrescarTabla();
    }

    private void refrescarTabla() {
        listOperarios.setAll(operarioController.listar());
        tblListOperario.refresh();
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
