package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryViewController {

    private App app;

    @FXML
    private Button btnAdministrador;

    @FXML
    private Button btnOperador;

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
