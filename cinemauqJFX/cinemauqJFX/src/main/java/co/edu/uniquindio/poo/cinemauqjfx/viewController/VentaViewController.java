package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import co.edu.uniquindio.poo.cinemauqjfx.controller.VentaController;
import co.edu.uniquindio.poo.cinemauqjfx.model.Asiento;
import co.edu.uniquindio.poo.cinemauqjfx.model.CinemaException;
import co.edu.uniquindio.poo.cinemauqjfx.model.Cliente;
import co.edu.uniquindio.poo.cinemauqjfx.model.Funcion;
import co.edu.uniquindio.poo.cinemauqjfx.model.TipoEntrada;
import co.edu.uniquindio.poo.cinemauqjfx.model.Venta;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Pantalla de venta del operador. Se elige la funcion, el cliente y el tipo de entrada,
 * y el asiento se escoge haciendo clic en la cuadricula ((X) = ocupado, * = seleccionado).
 * Las reglas de venta las valida el Cine y llegan como excepciones.
 */
public class VentaViewController {

    private App app;
    private VentaController ventaController;
    private Asiento asientoSeleccionado;

    @FXML
    private ComboBox<Funcion> cmbFuncion;

    @FXML
    private ComboBox<Cliente> cmbCliente;

    @FXML
    private ComboBox<TipoEntrada> cmbTipo;

    @FXML
    private Label lblAsiento;

    @FXML
    private Label lblValor;

    @FXML
    private GridPane gridAsientos;

    @FXML
    private Button btnVender;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnVolver;

    // ------------------------------------------------------------------
    // Eventos
    // ------------------------------------------------------------------
    @FXML
    void onFuncionElegida() {
        asientoSeleccionado = null;
        lblAsiento.setText("Ninguno");
        dibujarAsientos();
        actualizarValor();
    }

    @FXML
    void onClienteElegido() {
        Cliente cliente = cmbCliente.getValue();
        if (cliente != null) {
            // Los dos enums tienen los mismos nombres: se propone el tipo de entrada del cliente
            cmbTipo.setValue(TipoEntrada.valueOf(cliente.getTipoCliente().name()));
        }
    }

    @FXML
    void onTipoElegido() {
        actualizarValor();
    }

    @FXML
    void onVender() {
        try {
            Funcion funcion = Utilidades.leerSeleccion(cmbFuncion, "una función");
            Cliente cliente = Utilidades.leerSeleccion(cmbCliente, "un cliente");
            TipoEntrada tipo = Utilidades.leerSeleccion(cmbTipo, "el tipo de entrada");
            if (asientoSeleccionado == null) {
                throw new IllegalArgumentException("Seleccione un asiento en la cuadrícula.");
            }
            Venta venta = ventaController.vender(cliente, funcion, asientoSeleccionado, tipo);
            asientoSeleccionado = null;
            lblAsiento.setText("Ninguno");
            dibujarAsientos();
            Utilidades.mostrarInformacion(venta.generarEntrada());
        } catch (CinemaException | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
            dibujarAsientos();   // por si el asiento lo ocupo otra venta
        }
    }

    @FXML
    void onLimpiar() {
        cmbFuncion.setValue(null);
        cmbCliente.setValue(null);
        cmbTipo.setValue(null);
        onFuncionElegida();
    }

    @FXML
    void onVolver() {
        app.openMenuOperador();
    }

    // ------------------------------------------------------------------
    // Inicializacion y apoyo
    // ------------------------------------------------------------------
    @FXML
    void initialize() {
        ventaController = new VentaController(App.cine, App.operario);
        cmbFuncion.setItems(FXCollections.observableArrayList(ventaController.funcionesProgramadas()));
        cmbCliente.setItems(FXCollections.observableArrayList(ventaController.listarClientes()));
        cmbTipo.setItems(FXCollections.observableArrayList(TipoEntrada.values()));
        Utilidades.configurarCombo(cmbFuncion, "Seleccione", Utilidades::textoFuncion);
        Utilidades.configurarCombo(cmbCliente, "Seleccione", c -> c.getNombre() + " (" + c.getIdentificacion() + ")");
        Utilidades.configurarCombo(cmbTipo, "Seleccione", TipoEntrada::toString);
        lblAsiento.setText("Ninguno");
        lblValor.setText("-");
    }

    private void elegirAsiento(Asiento asiento) {
        asientoSeleccionado = asiento;
        lblAsiento.setText(asiento.getCodigo());
        dibujarAsientos();
    }

    private void dibujarAsientos() {
        CuadriculaAsientos.dibujar(gridAsientos, cmbFuncion.getValue(), asientoSeleccionado, this::elegirAsiento);
    }

    private void actualizarValor() {
        Funcion funcion = cmbFuncion.getValue();
        TipoEntrada tipo = cmbTipo.getValue();
        if (funcion == null || tipo == null) {
            lblValor.setText("-");
            return;
        }
        try {
            lblValor.setText(String.format("$%,.0f", ventaController.valorEntrada(funcion, tipo)));
        } catch (IllegalStateException e) {
            lblValor.setText("Sin tarifa");
        }
    }

    public void setApp(App app) {
        this.app = app;
    }
}
