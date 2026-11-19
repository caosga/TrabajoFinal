package viewController;

import app.App;
import controller.VentaController;
import exceptions.AsientoOcupadoException;
import exceptions.ElementoDuplicadoException;
import exceptions.FuncionNoEncontradaException;
import exceptions.SalaNoDisponibleException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Pantalla del operador para vender entradas.
 * Se elige funcion, cliente y tipo de entrada, y uno o varios asientos en la cuadricula.
 * Cada asiento se convierte en un Boleto y CinemaUQ.realizarVenta valida todo
 * (funcion existente, asiento libre, asientos suficientes) lanzando sus excepciones.
 */
public class VentaViewController {

    private App app;
    private VentaController ventaController;
    private final List<Asiento> asientosElegidos = new ArrayList<>();

    @FXML
    private ComboBox<Funcion> cmbFuncion;

    @FXML
    private ComboBox<Operario> cmbOperario;

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
        asientosElegidos.clear();
        actualizarPantalla();
    }

    @FXML
    void onClienteElegido() {
        Cliente cliente = cmbCliente.getValue();
        if (cliente != null) {
            // TipoCliente y TipoEntrada tienen los mismos nombres (GENERAL, ESTUDIANTE, NINO):
            // se propone el tipo de entrada segun el cliente
            cmbTipo.setValue(TipoEntrada.valueOf(cliente.getTipoCliente().name()));
            actualizarValor();
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
            Operario operario = Utilidades.leerSeleccion(cmbOperario, "el operario que vende");
            Cliente cliente = Utilidades.leerSeleccion(cmbCliente, "un cliente");
            TipoEntrada tipo = Utilidades.leerSeleccion(cmbTipo, "el tipo de entrada");
            if (asientosElegidos.isEmpty()) {
                throw new IllegalArgumentException("Seleccione al menos un asiento en la cuadrícula.");
            }
            Venta venta = ventaController.vender(operario, cliente, funcion, new ArrayList<>(asientosElegidos), tipo);
            asientosElegidos.clear();
            actualizarPantalla();
            Utilidades.mostrarInformacion(resumenVenta(venta));
        } catch (FuncionNoEncontradaException | SalaNoDisponibleException | AsientoOcupadoException
                 | ElementoDuplicadoException | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
            actualizarPantalla();
        }
    }

    @FXML
    void onLimpiar() {
        cmbFuncion.setValue(null);
        cmbCliente.setValue(null);
        cmbTipo.setValue(null);
        asientosElegidos.clear();
        actualizarPantalla();
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
        ventaController = new VentaController(App.cine);
        cmbFuncion.setItems(FXCollections.observableArrayList(ventaController.funcionesProgramadas()));
        cmbOperario.setItems(FXCollections.observableArrayList(ventaController.listarOperarios()));
        cmbCliente.setItems(FXCollections.observableArrayList(ventaController.listarClientes()));
        cmbTipo.setItems(FXCollections.observableArrayList(TipoEntrada.values()));
        Utilidades.configurarCombo(cmbFuncion, "Seleccione", Utilidades::textoFuncion);
        Utilidades.configurarCombo(cmbOperario, "Seleccione", o -> o.getNombre() + " (" + o.getIdentificacion() + ")");
        Utilidades.configurarCombo(cmbCliente, "Seleccione", c -> c.getNombre() + " (" + c.getIdentificacion() + ")");
        Utilidades.configurarCombo(cmbTipo, "Seleccione", TipoEntrada::toString);
        actualizarPantalla();
    }

    /** Clic en un asiento: si no estaba elegido se agrega, si ya estaba se quita. */
    private void alternarAsiento(Asiento asiento) {
        if (asientosElegidos.contains(asiento)) {
            asientosElegidos.remove(asiento);
        } else {
            asientosElegidos.add(asiento);
        }
        actualizarPantalla();
    }

    private void actualizarPantalla() {
        CuadriculaAsientos.dibujar(gridAsientos, cmbFuncion.getValue(), asientosElegidos, this::alternarAsiento);
        if (asientosElegidos.isEmpty()) {
            lblAsiento.setText("Ninguno");
        } else {
            lblAsiento.setText(textoAsientos(asientosElegidos));
        }
        actualizarValor();
    }

    /** Valor total = tarifa del tipo de entrada x cantidad de asientos (igual que Venta.calcularValorTotal). */
    private void actualizarValor() {
        TipoEntrada tipo = cmbTipo.getValue();
        if (tipo == null || asientosElegidos.isEmpty()) {
            lblValor.setText("-");
            return;
        }
        Tarifa tarifa = App.cine.buscarTarifa(tipo);
        if (tarifa == null) {
            lblValor.setText("Sin tarifa para " + tipo);
            return;
        }
        lblValor.setText(Utilidades.pesos(tarifa.getValor() * asientosElegidos.size())
                + "  (" + asientosElegidos.size() + " x " + Utilidades.pesos(tarifa.getValor()) + ")");
    }

    private String resumenVenta(Venta venta) {
        return "Venta " + venta.getCodigoVenta() + " registrada.\n\n"
                + "Operario: " + venta.getTheOperario().getNombre() + "\n"
                + "Cliente: " + venta.getTheCliente().getNombre() + "\n"
                + "Película: " + venta.getTheFuncion().getThePelicula().getNombre() + "\n"
                + "Función: " + Utilidades.textoFuncion(venta.getTheFuncion()) + "\n"
                + "Asientos: " + textoAsientos(venta.getListAsientoVenta()) + "\n"
                + "Tipo de entrada: " + venta.getTheTarifa().getTipoEntrada() + "\n"
                + "Total pagado: " + Utilidades.pesos(venta.calcularValorTotal());
    }

    static String textoAsientos(List<Asiento> asientos) {
        StringBuilder texto = new StringBuilder();
        for (Asiento a : asientos) {
            if (texto.length() > 0) {
                texto.append(", ");
            }
            texto.append(a.getFila()).append(a.getNumero());
        }
        return texto.toString();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
