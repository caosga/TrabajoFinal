package viewController;

import app.App;
import controller.ConsultaController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Funcion;
import model.ReporteOcupacionFuncion;

/**
 * Disponibilidad de una funcion: total de asientos, libres, ocupados y la cuadricula
 * (solo consulta) donde se ve cuales estan libres.
 */
public class DisponibilidadViewController {

    private App app;
    private ConsultaController consultaController;

    @FXML
    private ComboBox<Funcion> cmbFuncion;

    @FXML
    private GridPane gridAsientos;

    @FXML
    private Label lblTotal;

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
            lblTotal.setText("-");
            lblLibres.setText("-");
            lblOcupados.setText("-");
            lblOcupacion.setText("-");
            return;
        }
        ReporteOcupacionFuncion reporte = consultaController.ocupacion(funcion);
        lblTotal.setText(String.valueOf(funcion.getListAsientoFuncion().size()));
        lblLibres.setText(String.valueOf(reporte.asientosDisponibles()));
        lblOcupados.setText(String.valueOf(reporte.asientosOcupados()));
        lblOcupacion.setText(String.format("%.1f%%", reporte.porcentajeOcupacion()));
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
