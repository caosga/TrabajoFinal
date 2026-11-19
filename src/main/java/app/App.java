package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.SalaController;
import model.*;
import viewController.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal de la interfaz JavaFX.
 * Tiene UN solo CinemaUQ compartido por todas las pantallas y abre cada vista con FXMLLoader.
 */
public class App extends Application {

    private Stage primaryStage;

    /** El cine que usan todas las pantallas (las mismas clases del paquete model). */
    public static CinemaUQ cine = new CinemaUQ("CinemaUQ", "900123456-7", "Universidad del Quindío",
            new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
            new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        inicializarData();
        openViewPrincipal();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // ------------------------------------------------------------------
    // Navegacion entre pantallas
    // ------------------------------------------------------------------
    public void openViewPrincipal() {
        PrimaryViewController controlador = cargarVista("primary.fxml", "CinemaUQ");
        controlador.setApp(this);
    }

    public void openMenuAdmin() {
        MenuAdminViewController controlador = cargarVista("menuAdmin.fxml", "CinemaUQ - Administrador");
        controlador.setApp(this);
    }

    public void openMenuOperador() {
        MenuOperadorViewController controlador = cargarVista("menuOperador.fxml", "CinemaUQ - Operador");
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
        FuncionViewController controlador = cargarVista("crudFuncion.fxml", "Gestión de funciones");
        controlador.setApp(this);
    }

    public void openCrudTarifa() {
        TarifaViewController controlador = cargarVista("crudTarifa.fxml", "Gestión de tarifas");
        controlador.setApp(this);
    }

    public void openMenuCine() {
        MenuCineViewController controlador = cargarVista("menuCine.fxml", "CinemaUQ - Cine");
        controlador.setApp(this);
    }

    public void openCrudAdministrador() {
        AdministradorViewController controlador = cargarVista("crudAdministrador.fxml", "Gestión de administradores");
        controlador.setApp(this);
    }

    public void openConsultaClientes() {
        ConsultaClientesViewController controlador = cargarVista("consultaClientes.fxml", "Consultar clientes");
        controlador.setApp(this);
    }

    public void openCrudOperario() {
        OperarioViewController controlador = cargarVista("crudOperario.fxml", "Gestión de operarios");
        controlador.setApp(this);
    }

    public void openCrudCliente() {
        ClienteViewController controlador = cargarVista("crudCliente.fxml", "Registro de clientes");
        controlador.setApp(this);
    }

    public void openVenta() {
        VentaViewController controlador = cargarVista("venta.fxml", "Vender entradas");
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
        DisponibilidadViewController controlador = cargarVista("disponibilidad.fxml", "Disponibilidad de una función");
        controlador.setApp(this);
    }

    public void openReportes() {
        ReporteViewController controlador = cargarVista("reportes.fxml", "Reportes");
        controlador.setApp(this);
    }

    /** Carga un FXML (que está en resources/app), lo muestra en la ventana y devuelve su ViewController. */
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
            cine.agregarAdministrador(new Administrador("AD01", "Carlos Pérez", "admin@cinemauq.com", 4000000));
            Operario operario = new Operario("OP01", "Laura Gómez", "laura@cinemauq.com", 2000000, new ArrayList<>());
            cine.agregarOperario(operario);

            cine.registrarTarifa(18000, TipoEntrada.GENERAL);
            cine.registrarTarifa(14000, TipoEntrada.ESTUDIANTE);
            cine.registrarTarifa(12000, TipoEntrada.NINO);
            cine.registrarTarifa(13000, TipoEntrada.ADULTO_MAYOR);

            cine.registrarPelicula(new Pelicula("P01", "Interestelar", 169, EstadoPelicula.DISPONIBLE,
                    Genero.DRAMA, Clasificacion.ADOLESCENTES, new ArrayList<>()));
            cine.registrarPelicula(new Pelicula("P02", "Dune: Parte Dos", 166, EstadoPelicula.DISPONIBLE,
                    Genero.ACCION, Clasificacion.ADOLESCENTES, new ArrayList<>()));

            cine.registrarSala(SalaController.crearSala("S01", "Sala 1 - 2D", 4, 8));
            cine.registrarSala(SalaController.crearSala("S02", "Sala 2 - 3D", 5, 8));

            LocalDate manana = LocalDate.now().plusDays(1);
            cine.programarFuncion("F01", LocalDateTime.of(manana, LocalTime.of(15, 0)), 18000, "P01", "S01");
            cine.programarFuncion("F02", LocalDateTime.of(manana, LocalTime.of(19, 30)), 18000, "P02", "S02");

            Cliente ana = new Cliente("1001", "Ana Morales", "ana@uq.edu.co", TipoCliente.ESTUDIANTE, new ArrayList<>());
            cine.agregarCliente(ana);
            cine.agregarCliente(new Cliente("1002", "Luis Ocampo", "luis@uq.edu.co", TipoCliente.GENERAL, new ArrayList<>()));

            // Una venta de ejemplo (2 boletos) para que los reportes no arranquen vacios
            Funcion f01 = cine.buscarFuncion("F01");
            Tarifa tarifa = cine.buscarTarifa(TipoEntrada.ESTUDIANTE);
            List<Boleto> boletos = new ArrayList<>();
            boletos.add(new Boleto(f01.getListAsientoFuncion().get(0), tarifa, f01));
            boletos.add(new Boleto(f01.getListAsientoFuncion().get(1), tarifa, f01));
            cine.realizarVenta("V1", ana, operario, f01, boletos);
        } catch (Exception e) {
            System.out.println("Error cargando datos de ejemplo: " + e.getMessage());
        }
    }
}
