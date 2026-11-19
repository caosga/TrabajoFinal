package viewController;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/** Menu del cine: registrar administradores y operarios, y consultar clientes. */
public class MenuCineViewController {

    private App app;

    @FXML
    private Button btnAdministradores;

    @FXML
    private Button btnOperarios;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnVolver;

    @FXML
    void onAdministradores() {
        app.openCrudAdministrador();
    }

    @FXML
    void onOperarios() {
        app.openCrudOperario();
    }

    @FXML
    void onClientes() {
        app.openConsultaClientes();
    }

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
