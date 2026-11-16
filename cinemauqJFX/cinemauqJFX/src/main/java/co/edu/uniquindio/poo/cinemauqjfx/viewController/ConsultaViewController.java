package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import co.edu.uniquindio.poo.cinemauqjfx.controller.ConsultaController;
import co.edu.uniquindio.poo.cinemauqjfx.controller.PeliculaController;
import co.edu.uniquindio.poo.cinemauqjfx.model.Funcion;
import co.edu.uniquindio.poo.cinemauqjfx.model.Pelicula;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Consulta de peliculas y de funciones programadas (con filtro por fecha). */
public class ConsultaViewController {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private App app;
    private PeliculaController peliculaController;
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
        peliculaController = new PeliculaController(App.cine);
        consultaController = new ConsultaController(App.cine);

        tbcPCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        tbcPNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcPGenero.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getGenero())));
        tbcPDuracion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDuracion() + " min"));
        tbcPClasificacion.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getClasificacion())));
        tbcPEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstado())));
        tblListPelicula.setItems(listPeliculas);
        listPeliculas.setAll(peliculaController.listar());

        tbcFCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        tbcFPelicula.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPelicula().getNombre()));
        tbcFSala.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSala().getNombre()));
        tbcFFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha().format(FORMATO_FECHA)));
        tbcFHora.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getHora().format(FORMATO_HORA)));
        tbcFLibres.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().contarDisponibles())));
        tbcFOcupados.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().contarOcupados())));
        tblListFuncion.setItems(listFunciones);
        listFunciones.setAll(consultaController.funcionesProgramadas());
    }

    public void setApp(App app) {
        this.app = app;
    }
}
