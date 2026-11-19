package viewController;

import app.App;
import controller.ReporteController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Funcion;
import model.ReporteGeneralVentas;
import model.ReporteOcupacionFuncion;
import model.ReportePeliculaMasVendida;

import java.time.LocalDate;

/**
 * Reportes del administrador: total de entradas vendidas, total de ingresos,
 * pelicula mas vendida, ocupacion de cada funcion y funciones programadas para una fecha.
 */
public class ReporteViewController {

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
    private DatePicker dpFecha;

    @FXML
    private Button btnFiltrar;

    @FXML
    private Button btnVerTodas;

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
    void onFiltrar() {
        LocalDate fecha = dpFecha.getValue();
        if (fecha == null) {
            Utilidades.mostrarError("Debe seleccionar la fecha.");
            return;
        }
        listFunciones.setAll(reporteController.funcionesPorFecha(fecha));
        tblListFuncion.refresh();
        if (listFunciones.isEmpty()) {
            Utilidades.mostrarInformacion("No hay funciones programadas para esa fecha.");
        }
    }

    @FXML
    void onVerTodas() {
        dpFecha.setValue(null);
        listFunciones.setAll(reporteController.listarFunciones());
        tblListFuncion.refresh();
    }

    @FXML
    void onVolver() {
        app.openMenuAdmin();
    }

    @FXML
    void initialize() {
        reporteController = new ReporteController(App.cine);
        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigoFuncion()));
        tbcPelicula.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getThePelicula().getNombre()));
        tbcFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaHora().format(Utilidades.FORMATO_FECHA_HORA)));
        tbcEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstadoFuncion())));
        tbcOcupados.setCellValueFactory(c -> {
            ReporteOcupacionFuncion r = reporteController.ocupacion(c.getValue());
            return new SimpleStringProperty(r.asientosOcupados() + " / " + (r.asientosOcupados() + r.asientosDisponibles()));
        });
        tbcOcupacion.setCellValueFactory(c -> new SimpleStringProperty(
                String.format("%.1f%%", reporteController.ocupacion(c.getValue()).porcentajeOcupacion())));
        tblListFuncion.setItems(listFunciones);
        cargarReportes();
    }

    private void cargarReportes() {
        ReporteGeneralVentas general = reporteController.reporteGeneral();
        lblEntradas.setText(String.valueOf(general.totalEntradas()));
        lblIngresos.setText(Utilidades.pesos(general.totalIngresos()));

        ReportePeliculaMasVendida masVendida = reporteController.peliculaMasVendida();
        if (masVendida == null || masVendida.totalEntradasVendidas() == 0) {
            lblPelicula.setText("Todavía no hay entradas vendidas");
        } else {
            lblPelicula.setText(masVendida.pelicula().getNombre() + " (" + masVendida.totalEntradasVendidas() + " entradas)");
        }

        onVerTodas();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
