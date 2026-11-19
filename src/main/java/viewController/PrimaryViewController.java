package viewController;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryViewController {

    private App app;

    @FXML
    private Button btnAdministrador;

    @FXML
    private Button btnOperador;

    @FXML
    private Button btnCine;

    @FXML
    void onCine() {
        app.openMenuCine();
    }

    @FXML
    void onAdministrador() {
        app.openMenuAdmin();
    }

    @FXML
    void onOperador() {
        app.openMenuOperador();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
