

import exceptions.*;
import model.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        CinemaUQ cine = inicializarDatosPrueba();

        String[] opciones = {
                "1. Ver Cartelera / Funciones",
                "2. Programar Nueva Función",
                "3. Realizar Venta de Boletos",
                "4. Cancelar Venta",
                "5. Reporte General de Ventas",
                "6. Reporte Ocupación por Función",
                "7. Reporte Película Más Vendida",
                "8. Reporte Funciones por Fecha",
                "0. Salir"
        };

        boolean continuar = true;
        while (continuar) {
            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Bienvenido a " + cine.getNombreCine() + "\nSeleccione una operación:",
                    "Sistema CinemaUQ - Pruebas",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            if (seleccion == null || seleccion.startsWith("0.")) {
                continuar = false;
                continue;
            }

            try {
                if (seleccion.startsWith("1.")) {
                    mostrarFunciones(cine);
                } else if (seleccion.startsWith("2.")) {
                    programarFuncionUI(cine);
                } else if (seleccion.startsWith("3.")) {
                    realizarVentaUI(cine);
                } else if (seleccion.startsWith("4.")) {
                    cancelarVentaUI(cine);
                } else if (seleccion.startsWith("5.")) {
                    mostrarReporteGeneral(cine);
                } else if (seleccion.startsWith("6.")) {
                    mostrarReporteOcupacion(cine);
                } else if (seleccion.startsWith("7.")) {
                    mostrarPeliculaMasVendida(cine);
                } else if (seleccion.startsWith("8.")) {
                    mostrarFuncionesPorFecha(cine);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Aviso del Sistema", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- 1. PRECARGA DE DATOS (Esto mismo te servirá como Seed/DataLoader en JavaFX) ---
    private static CinemaUQ inicializarDatosPrueba() {
        CinemaUQ cine = new CinemaUQ(
                "CinemaUQ Armenia",
                "900123456-7",
                "Centro Comercial Portal del Quindío",
                new ArrayList<>(), // listCinePelicula
                new ArrayList<>(), // listCineSala
                new ArrayList<>(), // listCineFuncion
                new ArrayList<>(), // listCineTarifa
                new ArrayList<>(), // listCineVenta
                new ArrayList<>(), // listCineCliente
                new ArrayList<>(), // listCineOperario
                new ArrayList<>()  // listCineAdministrador
        );

        // Personal
        Administrador admin = new Administrador("Carlos Ospina", "AD01", "admin@cinemauq.com", 4000000);
        Operario operario = new Operario("Tiffany López", "OP01", "teffy@cinemauq.com", 2000000, new ArrayList<>());
        cine.getListCineAdministrador().add(admin);
        cine.getListCineOperario().add(operario);

        // Salas
        Sala sala1 = crearSalaConAsientos("S01", "Sala 1 - 2D", 40);
        Sala sala2 = crearSalaConAsientos("S02", "Sala 2 - 3D", 48);
        cine.getListCineSala().add(sala1);
        cine.getListCineSala().add(sala2);

        // Películas
        Pelicula p1 = new Pelicula("P01", "Interestelar", 169, EstadoPelicula.DISPONIBLE,
                Genero.ANIMACION, Clasificacion.ADOLESCENTES, new ArrayList<>()
        );
        Pelicula p2 = new Pelicula("P02", "Dune: Parte Dos", 169, EstadoPelicula.DISPONIBLE,
                Genero.ANIMACION, Clasificacion.ADOLESCENTES, new ArrayList<>());
        cine.getListCinePelicula().add(p1);
        cine.getListCinePelicula().add(p2);

        // Tarifas
        Tarifa tGeneral = new Tarifa(18000, TipoEntrada.GENERAL, new ArrayList<>());
        Tarifa tEstudiante = new Tarifa(14000, TipoEntrada.ESTUDIANTE, new ArrayList<>());
        cine.getListCineTarifa().add(tGeneral);
        cine.getListCineTarifa().add(tEstudiante);

        // Funciones iniciales
        try {
            cine.programarFuncion("F01",LocalDateTime.now().plusHours(2), tGeneral.getValor(), p1.getCodigoPel(), sala1.getCodigoSala() );
            cine.programarFuncion("F02",LocalDateTime.now().plusHours(2), tEstudiante.getValor(), p2.getCodigoPel(), sala2.getCodigoSala());
        } catch (Exception e) {
            System.out.println("Error en precarga: " + e.getMessage());
        }

        return cine;
    }

    private static Sala crearSalaConAsientos(String codigo, String nombre, int capacidad) {
        Sala sala = new Sala(codigo, nombre, capacidad, EstadoSala.DISPONIBLE, new ArrayList<>(), new ArrayList<>());

        int asientosPorFila = 8;
        int filas = (int) Math.ceil((double) capacidad / asientosPorFila);

        char letraFila = 'A';
        int contadorAsientos = 0;

        for (int i = 0; i < filas; i++) {
            for (int j = 1; j <= asientosPorFila && contadorAsientos < capacidad; j++) {
                sala.getListAsiento().add(new Asiento(codigo + "-" + letraFila + j,
                        String.valueOf(letraFila), j, EstadoAsiento.DISPONIBLE, null, new ArrayList<>() ));
                contadorAsientos++;
            }
            letraFila++;
        }
        return sala;
    }

    // --- 2. OPERACIONES DEL MENÚ ---

    private static void mostrarFunciones(CinemaUQ cine) {
        StringBuilder sb = new StringBuilder("=== FUNCIONES PROGRAMADAS ===\n\n");
        for (Funcion f : cine.getListCineFuncion()) {
            sb.append("Código: ").append(f.getCodigoFuncion())
                    .append(" | Película: ").append(f.getThePelicula().getNombre())
                    .append(" | Sala: ").append(f.getTheSala().getNombre())
                    .append(" | Fecha/Hora: ").append(f.getFechaHora().toLocalDate()).append(" ").append(f.getFechaHora().toLocalTime().withSecond(0).withNano(0))
                    .append(" | Libres: ").append(f.contarAsientosDisponibles())
                    .append(" | Estado: ").append(f.getEstadoFuncion())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void programarFuncionUI(CinemaUQ cine) throws ElementoDuplicadoException, SalaNoDisponibleException {
        String codigo = JOptionPane.showInputDialog("Código de la nueva función (ej: F03):");
        if (codigo == null || codigo.isBlank()) return;

        Pelicula pelicula = (Pelicula) JOptionPane.showInputDialog(
                null, "Seleccione la película:", "Películas",
                JOptionPane.QUESTION_MESSAGE, null,
                cine.getListCinePelicula().toArray(), cine.getListCinePelicula().get(0));
        if (pelicula == null) return;

        Sala sala = (Sala) JOptionPane.showInputDialog(
                null, "Seleccione la sala:", "Salas",
                JOptionPane.QUESTION_MESSAGE, null,
                cine.getListCineSala().toArray(), cine.getListCineSala().get(0));
        if (sala == null) return;

        String horasStr = JOptionPane.showInputDialog("¿En cuántas horas desde ahora inicia la función? (ej: 6):", "6");
        if (horasStr == null) return;
        int horas = Integer.parseInt(horasStr.trim());

        cine.programarFuncion(codigo.trim(), LocalDateTime.now().plusHours(horas),18000, pelicula.getCodigoPel(), sala.getCodigoSala());
        JOptionPane.showMessageDialog(null, "¡Función " + codigo + " programada con éxito!");
    }

    private static void realizarVentaUI(CinemaUQ cine) throws Exception {
        if (cine.getListCineFuncion().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay funciones disponibles.");
            return;
        }

        Funcion funcion = (Funcion) JOptionPane.showInputDialog(
                null, "Seleccione la función:", "Venta de Boletos",
                JOptionPane.QUESTION_MESSAGE, null,
                cine.getListCineFuncion().toArray(), cine.getListCineFuncion().get(0));
        if (funcion == null) return;

        // Mostrar mapa rápido de asientos
        StringBuilder mapa = new StringBuilder("Asientos de la función " + funcion.getCodigoFuncion() + ":\n");
        for (Asiento a : funcion.getListAsientoFuncion()) {
            mapa.append(a.getFila()).append(a.getNumero())
                    .append(a.getEstadoAsiento() == EstadoAsiento.DISPONIBLE ? " [LIBRE]  " : " [OCUPADO]  ");
        }

        String cantStr = JOptionPane.showInputDialog(mapa + "\n\n¿Cuántos boletos desea comprar?");
        if (cantStr == null) return;
        int cantidad = Integer.parseInt(cantStr.trim());

        List<Boleto> asientosSeleccionados = new ArrayList<>();
        List<Tarifa> tarifasSeleccionadas = new ArrayList<>();

        List<Boleto> boletosSeleccionados = new ArrayList<>();

        for (int i = 1; i <= cantidad; i++) {
            String fila = JOptionPane.showInputDialog("Boleto #" + i + " - Ingrese la FILA (ej: A o B):");
            String numStr = JOptionPane.showInputDialog("Boleto #" + i + " - Ingrese el NÚMERO (ej: 1 a 4):");
            if (fila == null || numStr == null) return;

            int num = Integer.parseInt(numStr.trim());
            Asiento asientoElegido = null;
            for (Asiento a : funcion.getListAsientoFuncion()) {
                if (a.getFila().equalsIgnoreCase(fila.trim()) && a.getNumero() == num) {
                    asientoElegido = a;
                    break;
                }
            }

            if (asientoElegido == null) {
                JOptionPane.showMessageDialog(null, "El asiento " + fila + num + " no existe en esta sala.");
                return;
            }

            Tarifa tarifa = (Tarifa) JOptionPane.showInputDialog(
                    null, "Seleccione la tarifa para el asiento " + fila.toUpperCase() + num + ":",
                    "Tarifas", JOptionPane.QUESTION_MESSAGE, null,
                    cine.getListCineTarifa().toArray(), cine.getListCineTarifa().get(0));
            if (tarifa == null) return;

            // Se crea el boleto combinando el asiento y su tarifa (ajusta el orden si tu record Boleto es distinto)
            Boleto boleto = new Boleto(asientoElegido, tarifa, funcion);
            boletosSeleccionados.add(boleto);
        }

        String codVenta = JOptionPane.showInputDialog("Código para esta venta (ej: V01):");
        String nombreCliente = JOptionPane.showInputDialog("Nombre del cliente:", "Juan Valdez");
        String docCliente = JOptionPane.showInputDialog("Documento del cliente:", "1094000111");
        if (codVenta == null || nombreCliente == null || docCliente == null) return;

        Cliente cliente = new Cliente(nombreCliente, docCliente, "cliente@correo.com", TipoCliente.GENERAL, new ArrayList<>());
        Operario operario = cine.getListCineOperario().get(0);

        Venta venta = cine.realizarVenta( codVenta.trim(), cliente,  operario, funcion, asientosSeleccionados);

        JOptionPane.showMessageDialog(null,
                "¡Venta Exitosa!\n" +
                        "Código: " + venta.getCodigoVenta() + "\n" +
                        "Boletos vendidos: " + venta.getListBoleto().size() + "\n" +
                        "Total pagado: $" + venta.calcularValorTotal());
    }

    private static void cancelarVentaUI(CinemaUQ cine) throws VentaNoEncontradaException {
        String codVenta = JOptionPane.showInputDialog("Ingrese el código de la venta a cancelar (ej: V01):");
        if (codVenta == null || codVenta.isBlank()) return;

        cine.cancelarVenta(codVenta.trim());
        JOptionPane.showMessageDialog(null, "Venta " + codVenta + " cancelada y asientos liberados correctamente.");
    }

    // --- 3. REPORTES ---

    private static void mostrarReporteGeneral(CinemaUQ cine) {
        ReporteGeneralVentas rep = cine.generarReporteGeneralVentas();
        JOptionPane.showMessageDialog(null,
                "=== REPORTE GENERAL DE VENTAS ===\n\n" +
                        "Total de entradas vendidas: " + rep.totalEntradas() + "\n" +
                        "Total ingresos recaudados: $" + rep.totalIngresos());
    }

    private static void mostrarReporteOcupacion(CinemaUQ cine) {
        Funcion funcion = (Funcion) JOptionPane.showInputDialog(
                null, "Seleccione la función a consultar:", "Reporte de Ocupación",
                JOptionPane.QUESTION_MESSAGE, null,
                cine.getListCineFuncion().toArray(), cine.getListCineFuncion().get(0));
        if (funcion == null) return;

        ReporteOcupacionFuncion rep = cine.generarReporteOcupacion(funcion);
        JOptionPane.showMessageDialog(null,
                "=== OCUPACIÓN FUNCIÓN " + funcion.getCodigoFuncion() + " ===\n\n" +
                        "Asientos disponibles: " + rep.asientosDisponibles() + "\n" +
                        "Asientos ocupados: " + rep.asientosOcupados() + "\n" +
                        "Porcentaje de ocupación: " + String.format("%.2f", rep.porcentajeOcupacion()) + "%");
    }

    private static void mostrarPeliculaMasVendida(CinemaUQ cine) {
        ReportePeliculaMasVendida rep = cine.obtenerPeliculaMasVendida();
        if (rep == null || rep.pelicula() == null) {
            JOptionPane.showMessageDialog(null, "No hay películas registradas.");
            return;
        }
        JOptionPane.showMessageDialog(null,
                "=== PELÍCULA MÁS VENDIDA ===\n\n" +
                        "Título: " + rep.pelicula().getNombre() + "\n" +
                        "Entradas vendidas: " + rep.totalEntradasVendidas());
    }

    private static void mostrarFuncionesPorFecha(CinemaUQ cine) {
        String fechaStr = JOptionPane.showInputDialog("Ingrese la fecha (AAAA-MM-DD):", LocalDate.now().toString());
        if (fechaStr == null || fechaStr.isBlank()) return;

        try {
            LocalDate fecha = LocalDate.parse(fechaStr.trim());
            ReporteFuncionesFecha rep = cine.consultarFuncionesPorFecha(fecha);
            StringBuilder sb = new StringBuilder("=== FUNCIONES PARA EL " + rep.fechaBuscada() + " ===\n\n");
            if (rep.funcionesProgramadas().isEmpty()) {
                sb.append("No hay funciones programadas para esta fecha.");
            } else {
                for (Funcion f : rep.funcionesProgramadas()) {
                    sb.append("- ").append(f.getCodigoFuncion())
                            .append(": ").append(f.getThePelicula().getNombre())
                            .append(" (").append(f.getFechaHora().toLocalTime().withSecond(0).withNano(0)).append(")\n");
                }
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use AAAA-MM-DD (ej: 2026-11-16).");
        }
    }
}