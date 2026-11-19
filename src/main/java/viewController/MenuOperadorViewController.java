package viewController;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuOperadorViewController {

    private App app;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnVender;

    @FXML
    private Button btnCancelarVenta;

    @FXML
    private Button btnConsultas;

    @FXML
    private Button btnDisponibilidad;

    @FXML
    private Button btnVolver;

    @FXML
    void onClientes() {
        app.openCrudCliente();
    }

    @FXML
    void onVender() {
        app.openVenta();
    }

    @FXML
    void onCancelarVenta() {
        app.openCancelarVenta();
    }

    @FXML
    void onConsultas() {
        app.openConsultas();
    }

    @FXML
    void onDisponibilidad() {
        app.openDisponibilidad();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
