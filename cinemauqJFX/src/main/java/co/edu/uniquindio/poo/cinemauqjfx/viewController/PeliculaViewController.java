package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import co.edu.uniquindio.poo.cinemauqjfx.controller.PeliculaController;
import co.edu.uniquindio.poo.cinemauqjfx.model.CinemaException;
import co.edu.uniquindio.poo.cinemauqjfx.model.Clasificacion;
import co.edu.uniquindio.poo.cinemauqjfx.model.Genero;
import co.edu.uniquindio.poo.cinemauqjfx.model.Pelicula;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * ViewController de la pantalla de peliculas (equivalente a ClienteViewController de la guia).
 * Lee lo que escribe el usuario, llama al controller y actualiza la tabla.
 * Los errores del dominio (excepciones) se muestran en una ventana Alert y la pantalla sigue abierta.
 */
public class PeliculaViewController {

    private App app;
    private PeliculaController peliculaController;
    private final ObservableList<Pelicula> listPeliculas = FXCollections.observableArrayList();
    private Pelicula selectedPelicula;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private ComboBox<Genero> cmbGenero;

    @FXML
    private TextField txtDuracion;

    @FXML
    private ComboBox<Clasificacion> cmbClasificacion;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnDeshabilitar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Pelicula> tblListPelicula;

    @FXML
    private TableColumn<Pelicula, String> tbcCodigo;

    @FXML
    private TableColumn<Pelicula, String> tbcNombre;

    @FXML
    private TableColumn<Pelicula, String> tbcGenero;

    @FXML
    private TableColumn<Pelicula, String> tbcDuracion;

    @FXML
    private TableColumn<Pelicula, String> tbcClasificacion;

    @FXML
    private TableColumn<Pelicula, String> tbcEstado;

    // ------------------------------------------------------------------
    // Eventos de los botones (los nombres coinciden con onAction del FXML)
    // ------------------------------------------------------------------
    @FXML
    void onRegistrarPelicula() {
        try {
            peliculaController.registrar(
                    leerTexto(txtCodigo, "el código"),
                    leerTexto(txtNombre, "el nombre"),
                    leerSeleccion(cmbGenero, "un género"),
                    leerDuracion(),
                    leerSeleccion(cmbClasificacion, "una clasificación"));
            refrescarTabla();
            limpiarSeleccion();
            mostrarInformacion("Película registrada correctamente.");
        } catch (CinemaException | IllegalArgumentException | IllegalStateException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void onActualizarPelicula() {
        try {
            exigirSeleccion();
            peliculaController.modificar(
                    selectedPelicula.getCodigo(),
                    leerTexto(txtNombre, "el nombre"),
                    leerSeleccion(cmbGenero, "un género"),
                    leerDuracion(),
                    leerSeleccion(cmbClasificacion, "una clasificación"));
            refrescarTabla();
            limpiarSeleccion();
            mostrarInformacion("Película actualizada correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void onDeshabilitarPelicula() {
        try {
            exigirSeleccion();
            peliculaController.deshabilitar(selectedPelicula.getCodigo());
            refrescarTabla();
            limpiarSeleccion();
            mostrarInformacion("La película quedó NO_DISPONIBLE.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void onVolver() {
        app.openMenuAdmin();
    }

    // ------------------------------------------------------------------
    // Inicializacion (JavaFX la llama sola al cargar el FXML)
    // ------------------------------------------------------------------
    @FXML
    void initialize() {
        peliculaController = new PeliculaController(App.cine);
        cmbGenero.setItems(FXCollections.observableArrayList(Genero.values()));
        cmbClasificacion.setItems(FXCollections.observableArrayList(Clasificacion.values()));
        initDataBinding();
        tblListPelicula.setItems(listPeliculas);
        refrescarTabla();
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcGenero.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getGenero())));
        tbcDuracion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuracion() + " min"));
        tbcClasificacion.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getClasificacion())));
        tbcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEstado())));
    }

    private void refrescarTabla() {
        listPeliculas.setAll(peliculaController.listar());
        tblListPelicula.refresh();
    }

    private void listenerSelection() {
        tblListPelicula.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedPelicula = newSelection;
            mostrarInformacionPelicula(selectedPelicula);
        });
    }

    private void mostrarInformacionPelicula(Pelicula pelicula) {
        if (pelicula != null) {
            txtCodigo.setText(pelicula.getCodigo());
            txtCodigo.setEditable(false);          // el código no se modifica
            txtNombre.setText(pelicula.getNombre());
            cmbGenero.setValue(pelicula.getGenero());
            txtDuracion.setText(String.valueOf(pelicula.getDuracion()));
            cmbClasificacion.setValue(pelicula.getClasificacion());
        }
    }

    // ------------------------------------------------------------------
    // Lectura y validacion de los campos
    // ------------------------------------------------------------------
    private String leerTexto(TextField campo, String nombreCampo) {
        String valor = campo.getText() == null ? "" : campo.getText().trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("Debe escribir " + nombreCampo + ".");
        }
        return valor;
    }

    private <T> T leerSeleccion(ComboBox<T> combo, String nombreCampo) {
        if (combo.getValue() == null) {
            throw new IllegalArgumentException("Debe seleccionar " + nombreCampo + ".");
        }
        return combo.getValue();
    }

    private int leerDuracion() {
        String texto = leerTexto(txtDuracion, "la duración");
        int duracion;
        try {
            duracion = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La duración debe ser un número entero de minutos.");
        }
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor que cero.");
        }
        return duracion;
    }

    private void exigirSeleccion() {
        if (selectedPelicula == null) {
            throw new IllegalArgumentException("Seleccione una película de la tabla.");
        }
    }

    // ------------------------------------------------------------------
    // Limpieza y mensajes
    // ------------------------------------------------------------------
    private void limpiarSeleccion() {
        tblListPelicula.getSelectionModel().clearSelection();
        selectedPelicula = null;
        txtCodigo.clear();
        txtCodigo.setEditable(true);
        txtNombre.clear();
        txtDuracion.clear();
        cmbGenero.setValue(null);
        cmbClasificacion.setValue(null);
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarInformacion(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
