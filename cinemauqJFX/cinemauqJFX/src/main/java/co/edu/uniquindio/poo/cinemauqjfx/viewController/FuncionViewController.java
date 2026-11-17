package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import co.edu.uniquindio.poo.cinemauqjfx.controller.FuncionController;
import co.edu.uniquindio.poo.cinemauqjfx.model.CinemaException;
import co.edu.uniquindio.poo.cinemauqjfx.model.EstadoFuncion;
import co.edu.uniquindio.poo.cinemauqjfx.model.Funcion;
import co.edu.uniquindio.poo.cinemauqjfx.model.Pelicula;
import co.edu.uniquindio.poo.cinemauqjfx.model.Sala;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * ViewController de la pantalla de funciones.
 * Una funcion une una pelicula y una sala en una fecha y hora. Las reglas
 * (sala fuera de servicio, pelicula no disponible, choque de horario, codigo repetido)
 * las valida el Cine y llegan aqui como excepciones que se muestran en un Alert.
 */
public class FuncionViewController {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private App app;
    private FuncionController funcionController;
    private final ObservableList<Funcion> listFunciones = FXCollections.observableArrayList();
    private Funcion selectedFuncion;

    @FXML
    private TextField txtCodigo;

    @FXML
    private ComboBox<Pelicula> cmbPelicula;

    @FXML
    private ComboBox<Sala> cmbSala;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TextField txtHora;

    @FXML
    private TextField txtValorBase;

    @FXML
    private Button btnProgramar;

    @FXML
    private Button btnCancelarFuncion;

    @FXML
    private Button btnFinalizar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Funcion> tblListFuncion;

    @FXML
    private TableColumn<Funcion, String> tbcCodigo;

    @FXML
    private TableColumn<Funcion, String> tbcPelicula;

    @FXML
    private TableColumn<Funcion, String> tbcSala;

    @FXML
    private TableColumn<Funcion, String> tbcFecha;

    @FXML
    private TableColumn<Funcion, String> tbcHora;

    @FXML
    private TableColumn<Funcion, String> tbcValorBase;

    @FXML
    private TableColumn<Funcion, String> tbcOcupacion;

    @FXML
    private TableColumn<Funcion, String> tbcEstado;

    // ------------------------------------------------------------------
    // Eventos de los botones
    // ------------------------------------------------------------------
    @FXML
    void onProgramarFuncion() {
        try {
            String codigo = Utilidades.leerTexto(txtCodigo, "el código");
            Pelicula pelicula = Utilidades.leerSeleccion(cmbPelicula, "una película");
            Sala sala = Utilidades.leerSeleccion(cmbSala, "una sala");
            LocalDateTime fechaHora = leerFechaHora();
            double valorBase = Utilidades.leerDecimal(txtValorBase, "el valor base");
            if (valorBase <= 0) {
                throw new IllegalArgumentException("El valor base debe ser mayor que cero.");
            }
            funcionController.programar(codigo, pelicula, sala, fechaHora, valorBase);
            refrescarTabla();
            limpiar();
            Utilidades.mostrarInformacion("Función programada correctamente.");
        } catch (CinemaException | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onCancelarFuncion() {
        try {
            exigirFuncionProgramada();
            funcionController.cancelar(selectedFuncion.getCodigo());
            refrescarTabla();
            limpiar();
            Utilidades.mostrarInformacion("La función quedó CANCELADA.");
        } catch (CinemaException | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onFinalizarFuncion() {
        try {
            exigirFuncionProgramada();
            funcionController.finalizar(selectedFuncion.getCodigo());
            refrescarTabla();
            limpiar();
            Utilidades.mostrarInformacion("La función quedó FINALIZADA.");
        } catch (CinemaException | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onLimpiar() {
        limpiar();
    }

    @FXML
    void onVolver() {
        app.openMenuAdmin();
    }

    // ------------------------------------------------------------------
    // Inicializacion
    // ------------------------------------------------------------------
    @FXML
    void initialize() {
        funcionController = new FuncionController(App.cine);
        Utilidades.configurarCombo(cmbPelicula, "Seleccione", Pelicula::getNombre);
        Utilidades.configurarCombo(cmbSala, "Seleccione", s -> s.getCodigo() + " - " + s.getNombre());
        cargarOpciones();
        initDataBinding();
        tblListFuncion.setItems(listFunciones);
        refrescarTabla();
        listenerSelection();
    }

    private void cargarOpciones() {
        cmbPelicula.setItems(FXCollections.observableArrayList(funcionController.peliculasDisponibles()));
        cmbSala.setItems(FXCollections.observableArrayList(funcionController.salasDisponibles()));
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcPelicula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPelicula().getNombre()));
        tbcSala.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSala().getNombre()));
        tbcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().format(FORMATO_FECHA)));
        tbcHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora().format(FORMATO_HORA)));
        tbcValorBase.setCellValueFactory(cellData -> new SimpleStringProperty(String.format("$%,.0f", cellData.getValue().getValorBase())));
        tbcOcupacion.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().contarOcupados() + " / " + cellData.getValue().getAsientos().size()));
        tbcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEstado())));
    }

    private void refrescarTabla() {
        listFunciones.setAll(funcionController.listar());
        tblListFuncion.refresh();
    }

    private void listenerSelection() {
        tblListFuncion.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedFuncion = newSelection;
        });
    }

    // ------------------------------------------------------------------
    // Lectura y validacion de los campos
    // ------------------------------------------------------------------
    private LocalDateTime leerFechaHora() {
        LocalDate fecha = dpFecha.getValue();
        if (fecha == null) {
            throw new IllegalArgumentException("Debe seleccionar la fecha.");
        }
        String texto = Utilidades.leerTexto(txtHora, "la hora");
        LocalTime hora;
        try {
            hora = LocalTime.parse(texto, FORMATO_HORA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La hora debe tener el formato HH:mm, por ejemplo 18:30.");
        }
        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La función debe programarse en una fecha y hora futuras.");
        }
        return fechaHora;
    }

    private void exigirFuncionProgramada() {
        if (selectedFuncion == null) {
            throw new IllegalArgumentException("Seleccione una función de la tabla.");
        }
        if (selectedFuncion.getEstado() != EstadoFuncion.PROGRAMADA) {
            throw new IllegalStateException("La función ya está " + selectedFuncion.getEstado() + ".");
        }
    }

    private void limpiar() {
        tblListFuncion.getSelectionModel().clearSelection();
        selectedFuncion = null;
        txtCodigo.clear();
        cmbPelicula.setValue(null);
        cmbSala.setValue(null);
        dpFecha.setValue(null);
        txtHora.clear();
        txtValorBase.clear();
        cargarOpciones();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
