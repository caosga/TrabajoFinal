package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import co.edu.uniquindio.poo.cinemauqjfx.controller.ReporteController;
import co.edu.uniquindio.poo.cinemauqjfx.model.Funcion;
import co.edu.uniquindio.poo.cinemauqjfx.model.Pelicula;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;

/** Reportes del administrador: totales de ventas, pelicula mas vendida y ocupacion por funcion. */
public class ReporteViewController {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private App app;
    private ReporteController reporteController;
    private final ObservableList<Funcion> listFunciones = FXCollections.observableArrayList();

    @FXML
    private Label lblEntradas;

    @FXML
    private Label lblIngresos;

    @FXML
    private Label lblPelicula;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Funcion> tblListFuncion;

    @FXML
    private TableColumn<Funcion, String> tbcCodigo;

    @FXML
    private TableColumn<Funcion, String> tbcPelicula;

    @FXML
    private TableColumn<Funcion, String> tbcFecha;

    @FXML
    private TableColumn<Funcion, String> tbcEstado;

    @FXML
    private TableColumn<Funcion, String> tbcOcupados;

    @FXML
    private TableColumn<Funcion, String> tbcOcupacion;

    @FXML
    void onActualizar() {
        cargarReportes();
    }

    @FXML
    void onVolver() {
        app.openMenuAdmin();
    }

    @FXML
    void initialize() {
        reporteController = new ReporteController(App.cine);
        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        tbcPelicula.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPelicula().getNombre()));
        tbcFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaHora().format(FORMATO)));
        tbcEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstado())));
        tbcOcupados.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().contarOcupados() + " / " + c.getValue().getAsientos().size()));
        tbcOcupacion.setCellValueFactory(c -> new SimpleStringProperty(
                String.format("%.1f%%", c.getValue().porcentajeOcupacion())));
        tblListFuncion.setItems(listFunciones);
        cargarReportes();
    }

    private void cargarReportes() {
        lblEntradas.setText(String.valueOf(reporteController.totalEntradasVendidas()));
        lblIngresos.setText(String.format("$%,.0f", reporteController.totalIngresos()));
        Pelicula masVendida = reporteController.peliculaMasVendida();
        lblPelicula.setText(masVendida == null ? "Todavía no hay ventas" : masVendida.getNombre());
        listFunciones.setAll(reporteController.listarFunciones());
        tblListFuncion.refresh();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
