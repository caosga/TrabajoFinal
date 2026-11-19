package viewController;

import app.App;
import controller.ConsultaController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Funcion;
import model.Pelicula;

import java.time.LocalDate;

/** Consultas del operador: peliculas disponibles y funciones programadas (con filtro por fecha). */
public class ConsultaViewController {

    private App app;
    private ConsultaController consultaController;
    private final ObservableList<Pelicula> listPeliculas = FXCollections.observableArrayList();
    private final ObservableList<Funcion> listFunciones = FXCollections.observableArrayList();

    @FXML
    private DatePicker dpFecha;

    @FXML
    private Button btnFiltrar;

    @FXML
    private Button btnVerTodas;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Pelicula> tblListPelicula;

    @FXML
    private TableColumn<Pelicula, String> tbcPCodigo;

    @FXML
    private TableColumn<Pelicula, String> tbcPNombre;

    @FXML
    private TableColumn<Pelicula, String> tbcPGenero;

    @FXML
    private TableColumn<Pelicula, String> tbcPDuracion;

    @FXML
    private TableColumn<Pelicula, String> tbcPClasificacion;

    @FXML
    private TableColumn<Pelicula, String> tbcPEstado;

    @FXML
    private TableView<Funcion> tblListFuncion;

    @FXML
    private TableColumn<Funcion, String> tbcFCodigo;

    @FXML
    private TableColumn<Funcion, String> tbcFPelicula;

    @FXML
    private TableColumn<Funcion, String> tbcFSala;

    @FXML
    private TableColumn<Funcion, String> tbcFFecha;

    @FXML
    private TableColumn<Funcion, String> tbcFHora;

    @FXML
    private TableColumn<Funcion, String> tbcFValor;

    @FXML
    private TableColumn<Funcion, String> tbcFLibres;

    @FXML
    private TableColumn<Funcion, String> tbcFOcupados;

    @FXML
    void onFiltrar() {
        LocalDate fecha = dpFecha.getValue();
        if (fecha == null) {
            Utilidades.mostrarError("Debe seleccionar la fecha.");
            return;
        }
        listFunciones.setAll(consultaController.funcionesPorFecha(fecha));
        tblListFuncion.refresh();
        if (listFunciones.isEmpty()) {
            Utilidades.mostrarInformacion("No hay funciones programadas para esa fecha.");
        }
    }

    @FXML
    void onVerTodas() {
        dpFecha.setValue(null);
        listFunciones.setAll(consultaController.funcionesProgramadas());
        tblListFuncion.refresh();
    }

    @FXML
    void onVolver() {
        app.openMenuOperador();
    }

    @FXML
    void initialize() {
        consultaController = new ConsultaController(App.cine);

        tbcPCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigoPel()));
        tbcPNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcPGenero.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getGenero())));
        tbcPDuracion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDuracion() + " min"));
        tbcPClasificacion.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getClasificacion())));
        tbcPEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstadoPelicula())));
        tblListPelicula.setItems(listPeliculas);
        listPeliculas.setAll(consultaController.peliculasDisponibles());

        tbcFCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigoFuncion()));
        tbcFPelicula.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getThePelicula().getNombre()));
        tbcFSala.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTheSala().getCodigoSala()));
        tbcFFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaHora().format(Utilidades.FORMATO_FECHA)));
        tbcFHora.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaHora().format(Utilidades.FORMATO_HORA)));
        tbcFValor.setCellValueFactory(c -> new SimpleStringProperty(Utilidades.pesos(c.getValue().getValorBase())));
        tbcFLibres.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().contarAsientosDisponibles())));
        tbcFOcupados.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(
                c.getValue().getListAsientoFuncion().size() - c.getValue().contarAsientosDisponibles())));
        tblListFuncion.setItems(listFunciones);
        listFunciones.setAll(consultaController.funcionesProgramadas());
    }

    public void setApp(App app) {
        this.app = app;
    }
}
