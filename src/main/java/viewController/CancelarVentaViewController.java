package viewController;

import app.App;
import controller.VentaController;
import exceptions.VentaNoEncontradaException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Venta;

/**
 * Pantalla para cancelar ventas. La venta no se borra: queda CANCELADA (CinemaUQ.cancelarVenta)
 * y sus asientos vuelven a quedar libres.
 */
public class CancelarVentaViewController {

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
            if (!Utilidades.confirmar("¿Cancelar la venta " + selectedVenta.getCodigoVenta() + "? Los asientos "
                    + VentaViewController.textoAsientos(selectedVenta.getListAsientoVenta()) + " quedarán libres.")) {
                return;
            }
            ventaController.cancelar(selectedVenta);
            refrescarTabla();
            Utilidades.mostrarInformacion("La venta quedó CANCELADA y sus asientos están libres.");
        } catch (VentaNoEncontradaException | IllegalArgumentException | IllegalStateException e) {
            Utilidades.mostrarError(e.getMessage());
        }
    }

    @FXML
    void onVolver() {
        app.openMenuOperador();
    }

    @FXML
    void initialize() {
        ventaController = new VentaController(App.cine);
        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigoVenta()));
        tbcCliente.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTheCliente().getNombre()));
        tbcPelicula.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTheFuncion().getThePelicula().getNombre()));
        tbcFuncion.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getTheFuncion().getFechaHora().format(Utilidades.FORMATO_FECHA_HORA)));
        tbcAsiento.setCellValueFactory(c -> new SimpleStringProperty(
                VentaViewController.textoAsientos(c.getValue().getListAsientoVenta())));
        tbcTipo.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getTheTarifa().getTipoEntrada())));
        tbcValor.setCellValueFactory(c -> new SimpleStringProperty(Utilidades.pesos(c.getValue().calcularValorTotal())));
        tbcEstado.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEstadoVenta())));
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
