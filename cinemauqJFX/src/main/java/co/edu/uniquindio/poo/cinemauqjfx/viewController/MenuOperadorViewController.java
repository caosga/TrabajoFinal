package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuOperadorViewController {

    private App app;

    @FXML
    private Button btnVolver;

    @FXML
    void onVolver() {
        app.openViewPrincipal();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
