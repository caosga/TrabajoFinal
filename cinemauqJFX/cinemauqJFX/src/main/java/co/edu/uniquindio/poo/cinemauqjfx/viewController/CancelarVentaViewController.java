package co.edu.uniquindio.poo.cinemauqjfx.viewController;

import co.edu.uniquindio.poo.cinemauqjfx.App;
import co.edu.uniquindio.poo.cinemauqjfx.controller.VentaController;
import co.edu.uniquindio.poo.cinemauqjfx.model.CinemaException;
import co.edu.uniquindio.poo.cinemauqjfx.model.Venta;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;

/**
 * Pantalla para cancelar ventas. La venta no se borra: queda CANCELADA para conservar
 * el historial, y su asiento vuelve a quedar libre.
 */
public class CancelarVentaViewController {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private App app;
    private VentaController ventaController;
    private final ObservableList<Venta> listVentas = FXCollections.observableArrayList();
    private Venta selectedVenta;

    @FXML
    private Button btnCancelarVenta;

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Venta> tblListVenta;

    @FXML
    private TableColumn<Venta, String> tbcCodigo;

    @FXML
    private TableColumn<Venta, String> tbcCliente;

    @FXML
    private TableColumn<Venta, String> tbcPelicula;

    @FXML
    private TableColumn<Venta, String> tbcFuncion;

    @FXML
    private TableColumn<Venta, String> tbcAsiento;

    @FXML
    private TableColumn<Venta, String> tbcTipo;

    @FXML
    private TableColumn<Venta, String> tbcValor;

    @FXML
    private TableColumn<Venta, String> tbcEstado;

    @FXML
    void onCancelarVenta() {
        try {
            if (selectedVenta == null) {
                throw new IllegalArgumentException("Seleccione una venta de la tabla.");
            }
            if (!Utilidades.confirmar("¿Cancelar la venta " + selectedVenta.getCodigo() + "? El asiento "
                    + selectedVenta.getAsiento().getCodigo() + " quedará libre.")) {
                return;
            }
            ventaController.cancelar(selectedVenta.getCodigo());
            refrescarTabla();
            Utilidades.mostrarInformacion("La venta quedó CANCELADA.");
        } catch (CinemaException | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onVolver() {
        app.openMenuOperador();
    }

    @FXML
    void initialize() {
        ventaController = new VentaController(App.cine, App.operario);
        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        tbcCliente.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCliente().getNombre()));
        tbcPelicula.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFuncion().getPelicula().getNombre()));
        tbcFuncion.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getFuncion().getFechaHora().format(FORMATO)));
        tbcAsiento.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAsiento().getCodigo()));
        tbcTipo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getTipoEntrada())));
        tbcValor.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.0f", c.getValue().getValorPagado())));
        tbcEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstado())));
        tblListVenta.setItems(listVentas);
        refrescarTabla();
        tblListVenta.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, nueva) -> selectedVenta = nueva);
    }

    private void refrescarTabla() {
        listVentas.setAll(ventaController.listarVentas());
        tblListVenta.refresh();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
