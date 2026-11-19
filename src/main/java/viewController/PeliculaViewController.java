package viewController;

import app.App;
import controller.PeliculaController;
import exceptions.ElementoDuplicadoException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Clasificacion;
import model.Genero;
import model.Pelicula;

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
                    Utilidades.leerTexto(txtCodigo, "el código"),
                    Utilidades.leerTexto(txtNombre, "el nombre"),
                    leerDuracion(),
                    Utilidades.leerSeleccion(cmbGenero, "un género"),
                    Utilidades.leerSeleccion(cmbClasificacion, "una clasificación"));
            refrescarTabla();
            limpiarSeleccion();
            Utilidades.mostrarInformacion("Película registrada correctamente.");
        } catch (ElementoDuplicadoException | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onActualizarPelicula() {
        try {
            exigirSeleccion();
            String mensaje = peliculaController.modificar(
                    selectedPelicula.getCodigoPel(),
                    Utilidades.leerTexto(txtNombre, "el nombre"),
                    leerDuracion(),
                    Utilidades.leerSeleccion(cmbGenero, "un género"),
                    Utilidades.leerSeleccion(cmbClasificacion, "una clasificación"));
            refrescarTabla();
            limpiarSeleccion();
            Utilidades.mostrarInformacion(mensaje);
        } catch (IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onDeshabilitarPelicula() {
        try {
            exigirSeleccion();
            String mensaje = peliculaController.deshabilitar(selectedPelicula.getCodigoPel());
            refrescarTabla();
            limpiarSeleccion();
            Utilidades.mostrarInformacion(mensaje);
        } catch (IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
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
        Utilidades.configurarCombo(cmbGenero, "Seleccione", Genero::toString);
        Utilidades.configurarCombo(cmbClasificacion, "Seleccione", Clasificacion::toString);

        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigoPel()));
        tbcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcGenero.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getGenero())));
        tbcDuracion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDuracion() + " min"));
        tbcClasificacion.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getClasificacion())));
        tbcEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstadoPelicula())));

        tblListPelicula.setItems(listPeliculas);
        refrescarTabla();

        // Al hacer clic en una fila, sus datos pasan al formulario
        tblListPelicula.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nueva) -> {
            selectedPelicula = nueva;
            mostrarInformacionPelicula(nueva);
        });
    }

    private void refrescarTabla() {
        listPeliculas.setAll(peliculaController.listarTodas());
        tblListPelicula.refresh();
    }

    private void mostrarInformacionPelicula(Pelicula pelicula) {
        if (pelicula != null) {
            txtCodigo.setText(pelicula.getCodigoPel());
            txtCodigo.setEditable(false);          // el código no se modifica
            txtNombre.setText(pelicula.getNombre());
            cmbGenero.setValue(pelicula.getGenero());
            txtDuracion.setText(String.valueOf(pelicula.getDuracion()));
            cmbClasificacion.setValue(pelicula.getClasificacion());
        }
    }

    private int leerDuracion() {
        int duracion = Utilidades.leerEntero(txtDuracion, "la duración (minutos)");
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

    public void setApp(App app) {
        this.app = app;
    }
}
