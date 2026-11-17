import exceptions.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CinemaUQTest {

    private CinemaUQ cine;
    private Pelicula peliculaActiva;
    private Sala salaDisponible;
    private Cliente cliente;
    private Operario operario;
    private Administrador administrador;
    private Tarifa tarifaGeneral;


    @BeforeEach
    public void setUp() {
        // Instanciamos CinemaUQ con su constructor
        cine = new CinemaUQ("CinemaUQ", "123456789", "Armenia, Quindío",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        // Película (ajusta Genero.values()[0] o Clasificacion.values()[0] por tus constantes exactas si prefieres)
        peliculaActiva = new Pelicula("P01", "Interestelar", 169,
                EstadoPelicula.DISPONIBLE,
                Genero.values()[0],
                Clasificacion.values()[0],
                new ArrayList<>()
        );

        /* Salas y generacion de asientos */
        List<Asiento> asientosS01 = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            asientosS01.add(new Asiento("S01-A" + i, "A", i, EstadoAsiento.DISPONIBLE, null, new ArrayList<>()));
        }
        salaDisponible = new Sala("S01", "Sala 1", 3, EstadoSala.DISPONIBLE, asientosS01, new ArrayList<>());

        List<Asiento> asientosS02 = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            asientosS02.add(new Asiento("S02-A" + i, "A", i, EstadoAsiento.DISPONIBLE, null, new ArrayList<>()));
        }
        Sala salaFueraServicio = new Sala("S02", "Sala 2", 5, EstadoSala.FUERA_DE_SERVICIO, asientosS02, new ArrayList<>());

        // Clientec operario y administrador
        cliente = new Cliente("1001", "Laura Gómez", "laura@uq.edu.co", TipoCliente.values()[0], new ArrayList<>());
        operario = new Operario("OP01", "Carlos Ruiz", "carlos@uq.edu.co", 1500000.0, new ArrayList<>());
        administrador = new Administrador("AD01", "Ana Morales", "ana@uq.edu.co", 2500000.0);
        cine.getListCineAdministrador().add(administrador);

        /* Tarifas*/
        tarifaGeneral = new Tarifa(12000.0, TipoEntrada.GENERAL, new ArrayList<>());

        Tarifa tarifaEstudiante = new Tarifa(8000.0, TipoEntrada.ESTUDIANTE, new ArrayList<>());

        // Agregamos directamente a las listas del cine para preparar el escenario de pruebas
        cine.getListCinePelicula().add(peliculaActiva);
        cine.getListCineSala().add(salaDisponible);
        cine.getListCineSala().add(salaFueraServicio);
        cine.getListCineCliente().add(cliente);
        cine.getListCineOperario().add(operario);
        cine.getListCineTarifa().add(tarifaGeneral);
        cine.getListCineTarifa().add(tarifaEstudiante);
    }

    // PROGRAMAR FUNCIÓN EXITOSA Y CLONACIÓN DE ASIENTOS
    @Test
    public void testProgramarFuncionExitosa() throws Exception {
        String mensaje = cine.programarFuncion("F01", LocalDateTime.now().plusDays(1), 12000.0,
                "P01", "S01" );

        assertEquals("Función programada exitosamente.", mensaje);
        Funcion funcionCreada = cine.getListCineFuncion().get(0);
        assertEquals(3, funcionCreada.contarAsientosDisponibles());
    }

    // EXCEPCIÓN: CÓDIGO DE FUNCIÓN DUPLICADO
    @Test
    public void testProgramarFuncionCodigoDuplicado() throws Exception {
        cine.programarFuncion("F01", LocalDateTime.now().plusDays(1), 12000.0, "P01", "S01");

        assertThrows(ElementoDuplicadoException.class, () -> {
            cine.programarFuncion("F01", LocalDateTime.now().plusDays(2), 12000.0, "P01", "S01");
        });
    }

    // EXCEPCIÓN: SALA FUERA DE SERVICIO
    @Test
    public void testProgramarFuncionSalaFueraDeServicio() {
        assertThrows(SalaNoDisponibleException.class, () -> {
            cine.programarFuncion("F02", LocalDateTime.now().plusDays(1), 12000.0, "P01", "S02");
        });
    }

    // REALIZAR VENTA EXITOSA, OCUPACIÓN DE ASIENTOS Y CÁLCULO DE TOTAL
    @Test
    public void testRealizarVentaExitosaYOcupacionAsientos() throws Exception {
        cine.programarFuncion("F01", LocalDateTime.now().plusDays(1), 12000.0, "P01", "S01");
        Funcion funcion = cine.getListCineFuncion().get(0);

        Asiento asiento1 = funcion.getListAsientoFuncion().get(0);
        Asiento asiento2 = funcion.getListAsientoFuncion().get(1);

        // Record Boleto(Asiento asiento, Tarifa tarifa, Funcion theFuncion)
        List<Boleto> boletos = List.of(
                new Boleto(asiento1, tarifaGeneral, funcion),
                new Boleto(asiento2, tarifaGeneral, funcion)
        );

        cine.realizarVenta("V01", cliente, operario, funcion, boletos);

        assertEquals(EstadoAsiento.OCUPADO, asiento1.getEstadoAsiento());
        assertEquals(EstadoAsiento.OCUPADO, asiento2.getEstadoAsiento());
        assertEquals(1, funcion.contarAsientosDisponibles());

        Venta ventaRegistrada = cine.getListCineVenta().get(0);
        assertEquals(24000.0, ventaRegistrada.calcularValorTotal(), 0.01);
    }

    // EXCEPCIÓN: INTENTAR COMPRAR UN ASIENTO YA OCUPADO
    @Test
    public void testRealizarVentaAsientoOcupado() throws Exception {
        cine.programarFuncion("F01", LocalDateTime.now().plusDays(1), 12000.0, "P01", "S01");
        Funcion funcion = cine.getListCineFuncion().get(0);
        Asiento asiento1 = funcion.getListAsientoFuncion().get(0);

        cine.realizarVenta("V01", cliente, operario, funcion, List.of(new Boleto(asiento1, tarifaGeneral, funcion)));

        assertThrows(AsientoOcupadoException.class, () -> {
            cine.realizarVenta("V02", cliente, operario, funcion, List.of(new Boleto(asiento1, tarifaGeneral, funcion)));
        });
    }

    // EXCEPCIÓN: SOLICITAR MÁS BOLETOS QUE ASIENTOS DISPONIBLES
    @Test
    public void testRealizarVentaSinSuficientesAsientos() throws Exception {
        cine.programarFuncion("F01", LocalDateTime.now().plusDays(1), 12000.0, "P01", "S01");
        Funcion funcion = cine.getListCineFuncion().get(0);

        Asiento a1 = funcion.getListAsientoFuncion().get(0);
        Asiento a2 = funcion.getListAsientoFuncion().get(1);
        Asiento a3 = funcion.getListAsientoFuncion().get(2);

        List<Boleto> cuatroBoletos = List.of(
                new Boleto(a1, tarifaGeneral, funcion),
                new Boleto(a2, tarifaGeneral, funcion),
                new Boleto(a3, tarifaGeneral, funcion),
                new Boleto(a1, tarifaGeneral, funcion)
        );

        assertThrows(SalaNoDisponibleException.class, () -> {
            cine.realizarVenta("V01", cliente, operario, funcion, cuatroBoletos);
        });
    }

    // EXCEPCIÓN: FUNCIÓN INEXISTENTE AL REALIZAR VENTA
    @Test
    public void testRealizarVentaFuncionNoEncontrada() {
        Funcion funcionNoRegistrada = new Funcion(
                "F99", LocalDateTime.now(), 12000.0, peliculaActiva,
                EstadoFuncion.PROGRAMADA, salaDisponible, new ArrayList<>(), new ArrayList<>()
        );

        assertThrows(FuncionNoEncontradaException.class, () -> {
            cine.realizarVenta("V01", cliente, operario, funcionNoRegistrada, new ArrayList<>());
        });
    }

    // CANCELAR VENTA Y LIBERACIÓN DE ASIENTOS
    @Test
    public void testCancelarVentaYLiberaAsientos() throws Exception {
        cine.programarFuncion("F01", LocalDateTime.now().plusDays(1), 12000.0, "P01", "S01");
        Funcion funcion = cine.getListCineFuncion().get(0);
        Asiento asiento1 = funcion.getListAsientoFuncion().get(0);

        cine.realizarVenta("V01", cliente, operario, funcion, List.of(new Boleto(asiento1, tarifaGeneral, funcion)));
        assertEquals(EstadoAsiento.OCUPADO, asiento1.getEstadoAsiento());
        assertEquals(2, funcion.contarAsientosDisponibles());

        cine.cancelarVenta("V01");

        assertEquals(EstadoVenta.CANCELADA, cine.getListCineVenta().get(0).getEstadoVenta());
        assertEquals(EstadoAsiento.DISPONIBLE, asiento1.getEstadoAsiento());
        assertEquals(3, funcion.contarAsientosDisponibles());
    }

    // EXCEPCIÓN: CANCELAR UNA VENTA QUE NO EXISTE
    @Test
    public void testCancelarVentaNoEncontrada() {
        assertThrows(VentaNoEncontradaException.class, () -> {
            cine.cancelarVenta("V999");
        });
    }
    @Test
    public void testAdministradorRegistrado() {
        assertEquals(1, cine.getListCineAdministrador().size());
        assertEquals("AD01", cine.getListCineAdministrador().get(0).getIdentificacion());
        assertEquals(2500000.0, cine.getListCineAdministrador().get(0).getSalario(), 0.01);
    }
}