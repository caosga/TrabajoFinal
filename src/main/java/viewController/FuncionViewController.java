package viewController;

import app.App;
import controller.FuncionController;
import exceptions.ElementoDuplicadoException;
import exceptions.SalaNoDisponibleException;
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
import model.Funcion;
import model.Pelicula;
import model.Sala;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Pantalla de gestion de funciones (administrador): programar y buscar funciones.
 * Si se elige una sala FUERA_DE_SERVICIO, CinemaUQ lanza SalaNoDisponibleException
 * y se muestra el mensaje.
 */
public class FuncionViewController {

    private App app;
    private FuncionController funcionController;
    private final ObservableList<Funcion> listFunciones = FXCollections.observableArrayList();

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
    private TextField txtBuscar;

    @FXML
    private Button btnProgramar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnBuscar;

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
            String mensaje = funcionController.programar(codigo, fechaHora, valorBase, pelicula, sala);
            refrescarTabla();
            limpiar();
            Utilidades.mostrarInformacion(mensaje);
        } catch (SalaNoDisponibleException | ElementoDuplicadoException
                 | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onBuscar() {
        try {
            String codigo = Utilidades.leerTexto(txtBuscar, "el código a buscar");
            Funcion funcion = funcionController.buscar(codigo);
            if (funcion == null) {
                Utilidades.mostrarError("No existe una función con el código " + codigo + ".");
                return;
            }
            tblListFuncion.getSelectionModel().select(funcion);
            Utilidades.mostrarInformacion(funcion.consultarDisponibilidadFuncion(funcion));
        } catch (IllegalArgumentException e) {
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
        Utilidades.configurarCombo(cmbPelicula, "Seleccione", p -> p.getCodigoPel() + " - " + p.getNombre());
        Utilidades.configurarCombo(cmbSala, "Seleccione", s -> s.getCodigoSala() + " - " + s.getNombre()
                + " (" + s.getEstado() + ")");
        cargarOpciones();

        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigoFuncion()));
        tbcPelicula.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getThePelicula().getNombre()));
        tbcSala.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTheSala().getCodigoSala()));
        tbcFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaHora().format(Utilidades.FORMATO_FECHA)));
        tbcHora.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaHora().format(Utilidades.FORMATO_HORA)));
        tbcValorBase.setCellValueFactory(c -> new SimpleStringProperty(Utilidades.pesos(c.getValue().getValorBase())));
        tbcOcupacion.setCellValueFactory(c -> {
            Funcion f = c.getValue();
            int total = f.getListAsientoFuncion().size();
            return new SimpleStringProperty((total - f.contarAsientosDisponibles()) + " / " + total);
        });
        tbcEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstadoFuncion())));

        tblListFuncion.setItems(listFunciones);
        refrescarTabla();
    }

    private void cargarOpciones() {
        cmbPelicula.setItems(FXCollections.observableArrayList(funcionController.peliculasDisponibles()));
        cmbSala.setItems(FXCollections.observableArrayList(funcionController.salas()));
    }

    private void refrescarTabla() {
        listFunciones.setAll(funcionController.listar());
        tblListFuncion.refresh();
    }

    /** Une la fecha del DatePicker con la hora escrita (HH:mm). */
    private LocalDateTime leerFechaHora() {
        LocalDate fecha = dpFecha.getValue();
        if (fecha == null) {
            throw new IllegalArgumentException("Debe seleccionar la fecha.");
        }
        String texto = Utilidades.leerTexto(txtHora, "la hora");
        LocalTime hora;
        try {
            hora = LocalTime.parse(texto, Utilidades.FORMATO_HORA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La hora debe tener el formato HH:mm, por ejemplo 18:30.");
        }
        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La función debe programarse en una fecha y hora futuras.");
        }
        return fechaHora;
    }

    private void limpiar() {
        tblListFuncion.getSelectionModel().clearSelection();
        txtCodigo.clear();
        cmbPelicula.setValue(null);
        cmbSala.setValue(null);
        dpFecha.setValue(null);
        txtHora.clear();
        txtValorBase.clear();
        txtBuscar.clear();
        cargarOpciones();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
