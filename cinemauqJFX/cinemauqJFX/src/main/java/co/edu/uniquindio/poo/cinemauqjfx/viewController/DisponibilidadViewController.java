package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import co.edu.uniquindio.poo.cinemauqjfx.controller.ConsultaController;
import co.edu.uniquindio.poo.cinemauqjfx.model.Funcion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/** Muestra, para una funcion, la cuadricula de asientos (solo consulta) y sus totales. */
public class DisponibilidadViewController {

    private App app;
    private ConsultaController consultaController;

    @FXML
    private ComboBox<Funcion> cmbFuncion;

    @FXML
    private GridPane gridAsientos;

    @FXML
    private Label lblLibres;

    @FXML
    private Label lblOcupados;

    @FXML
    private Label lblOcupacion;

    @FXML
    private Button btnVolver;

    @FXML
    void onFuncionElegida() {
        Funcion funcion = cmbFuncion.getValue();
        CuadriculaAsientos.dibujar(gridAsientos, funcion, null, null);
        if (funcion == null) {
            lblLibres.setText("-");
            lblOcupados.setText("-");
            lblOcupacion.setText("-");
            return;
        }
        lblLibres.setText(String.valueOf(funcion.contarDisponibles()));
        lblOcupados.setText(String.valueOf(funcion.contarOcupados()));
        lblOcupacion.setText(String.format("%.1f%%", funcion.porcentajeOcupacion()));
    }

    @FXML
    void onVolver() {
        app.openMenuOperador();
    }

    @FXML
    void initialize() {
        consultaController = new ConsultaController(App.cine);
        cmbFuncion.setItems(FXCollections.observableArrayList(consultaController.funcionesProgramadas()));
        Utilidades.configurarCombo(cmbFuncion, "Seleccione una función", Utilidades::textoFuncion);
        onFuncionElegida();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
