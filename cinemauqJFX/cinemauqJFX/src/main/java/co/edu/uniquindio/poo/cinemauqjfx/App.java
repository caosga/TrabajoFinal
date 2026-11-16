package co.edu.uniquindio.poo.cinemauqjfx;

import co.edu.uniquindio.poo.cinemauqjfx.model.Admin;
import co.edu.uniquindio.poo.cinemauqjfx.model.Cine;
import co.edu.uniquindio.poo.cinemauqjfx.model.Clasificacion;
import co.edu.uniquindio.poo.cinemauqjfx.model.CinemaException;
import co.edu.uniquindio.poo.cinemauqjfx.model.Cliente;
import co.edu.uniquindio.poo.cinemauqjfx.model.Funcion;
import co.edu.uniquindio.poo.cinemauqjfx.model.Genero;
import co.edu.uniquindio.poo.cinemauqjfx.model.Operario;
import co.edu.uniquindio.poo.cinemauqjfx.model.Pelicula;
import co.edu.uniquindio.poo.cinemauqjfx.model.Sala;
import co.edu.uniquindio.poo.cinemauqjfx.model.TipoCliente;
import co.edu.uniquindio.poo.cinemauqjfx.model.TipoEntrada;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.CancelarVentaViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.ClienteViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.ConsultaViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.DisponibilidadViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.FuncionViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.MenuAdminViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.MenuOperadorViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.PeliculaViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.PrimaryViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.ReporteViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.SalaViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.TarifaViewController;
import co.edu.uniquindio.poo.cinemauqjfx.viewController.VentaViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * JavaFX App. Crea UN solo Cine compartido por todas las pantallas (igual que
 * "public static Empresa empresa" en la guia) y abre cada vista con FXMLLoader.
 */
public class App extends Application {

    private Stage primaryStage;
    public static Cine cine = new Cine("CINEMAUQ", "900123456-7", "Universidad del Quindío");
    /** El operario que registra las ventas (implementa IVender). */
    public static Operario operario;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        inicializarData();
        openViewPrincipal();
    }

    public static void main(String[] args) {
        launch();
    }

    // ------------------------------------------------------------------
    // Navegacion entre pantallas
    // ------------------------------------------------------------------
    public void openViewPrincipal() {
        PrimaryViewController controlador = cargarVista("primary.fxml", "CINEMAUQ");
        controlador.setApp(this);
    }

    public void openMenuAdmin() {
        MenuAdminViewController controlador = cargarVista("menuAdmin.fxml", "CINEMAUQ - Administrador");
        controlador.setApp(this);
    }

    public void openMenuOperador() {
        MenuOperadorViewController controlador = cargarVista("menuOperador.fxml", "CINEMAUQ - Operador");
        controlador.setApp(this);
    }

    public void openCrudPelicula() {
        PeliculaViewController controlador = cargarVista("crudPelicula.fxml", "Gestión de películas");
        controlador.setApp(this);
    }

    public void openCrudSala() {
        SalaViewController controlador = cargarVista("crudSala.fxml", "Gestión de salas");
        controlador.setApp(this);
    }

    public void openCrudFuncion() {
        FuncionViewController controlador = cargarVista("crudFuncion.fxml", "Programar funciones");
        controlador.setApp(this);
    }

    public void openCrudTarifa() {
        TarifaViewController controlador = cargarVista("crudTarifa.fxml", "Tarifas");
        controlador.setApp(this);
    }

    public void openCrudCliente() {
        ClienteViewController controlador = cargarVista("crudCliente.fxml", "Registro de clientes");
        controlador.setApp(this);
    }

    public void openVenta() {
        VentaViewController controlador = cargarVista("venta.fxml", "Vender entrada");
        controlador.setApp(this);
    }

    public void openCancelarVenta() {
        CancelarVentaViewController controlador = cargarVista("cancelarVenta.fxml", "Cancelar venta");
        controlador.setApp(this);
    }

    public void openConsultas() {
        ConsultaViewController controlador = cargarVista("consultas.fxml", "Consultar películas y funciones");
        controlador.setApp(this);
    }

    public void openDisponibilidad() {
        DisponibilidadViewController controlador = cargarVista("disponibilidad.fxml", "Disponibilidad de asientos");
        controlador.setApp(this);
    }

    public void openReportes() {
        ReporteViewController controlador = cargarVista("reportes.fxml", "Reportes");
        controlador.setApp(this);
    }

    /** Carga un FXML, lo muestra en la ventana y devuelve su ViewController. */
    private <T> T cargarVista(String archivoFxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(archivoFxml));
            Parent root = loader.load();
            primaryStage.setTitle(titulo);
            primaryStage.setScene(new Scene(root));
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
            primaryStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la vista " + archivoFxml, e);
        }
    }

    // ------------------------------------------------------------------
    // Datos de ejemplo para que el programa no arranque vacio
    // ------------------------------------------------------------------
    private void inicializarData() {
        try {
            cine.definirTarifa(TipoEntrada.GENERAL, 15000);
            cine.definirTarifa(TipoEntrada.ESTUDIANTE, 12000);
            cine.definirTarifa(TipoEntrada.NINO, 10000);
            cine.definirTarifa(TipoEntrada.ADULTO_MAYOR, 11000);

            Pelicula p1 = new Pelicula("P1", "El Último Algoritmo", Genero.ACCION, 120, Clasificacion.MAYORES_12);
            Pelicula p2 = new Pelicula("P2", "Interestelar", Genero.DRAMA, 169, Clasificacion.MAYORES_7);
            cine.registrarPelicula(p1);
            cine.registrarPelicula(p2);

            Sala s1 = new Sala("S1", "1", 3, 5);
            Sala s2 = new Sala("S2", "2", 4, 6);
            cine.registrarSala(s1);
            cine.registrarSala(s2);

            LocalDate manana = LocalDate.now().plusDays(1);
            Funcion f1 = new Funcion("F1", p1, s2, LocalDateTime.of(manana, LocalTime.of(18, 30)), 15000);
            Funcion f2 = new Funcion("F2", p2, s1, LocalDateTime.of(manana, LocalTime.of(14, 0)), 15000);
            cine.programarFuncion(f1);
            cine.programarFuncion(f2);

            cine.registrarAdmin(new Admin("AD1", "Carlos", "carlos@cinemauq.co", 3000000));
            operario = new Operario("OP1", "Laura", "laura@cinemauq.co", 2000000);
            cine.registrarOperario(operario);
            Cliente ana = new Cliente("C1", "Ana", "ana@mail.com", TipoCliente.ESTUDIANTE);
            cine.registrarCliente(ana);
            cine.registrarCliente(new Cliente("C2", "Luis", "luis@mail.com", TipoCliente.GENERAL));

            // Dos ventas de ejemplo para que los reportes y la cuadricula no arranquen vacios
            operario.venderEntrada(cine, "V1", ana, f1, f1.buscarAsiento("A", 1), TipoEntrada.ESTUDIANTE);
            operario.venderEntrada(cine, "V2", ana, f1, f1.buscarAsiento("A", 2), TipoEntrada.GENERAL);
        } catch (CinemaException e) {
            System.out.println("Error cargando datos de ejemplo: " + e.getMessage());
        }
    }
}
