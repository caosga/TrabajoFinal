package co.edu.uniquindio.poo.cinemauqjfx.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase central del sistema CINEMAUQ.
 * Contiene las colecciones de todas las entidades (composicion)
 * y concentra las funcionalidades del operador, del administrador y los reportes.
 */
public class Cine {

    // ------------------------------------------------------------------
    // Atributos
    // ------------------------------------------------------------------
    private String nombre;
    private String nit;
    private String direccion;

    private List<Pelicula> peliculas;
    private List<Sala> salas;
    private List<Funcion> funciones;
    private List<Persona> personas;   // clientes y operarios (Cine ◆ Persona)
    private List<Tarifa> tarifas;
    private List<Venta> ventas;

    // ------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------
    public Cine(String nombre, String nit, String direccion) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.peliculas = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.funciones = new ArrayList<>();
        this.personas = new ArrayList<>();
        this.tarifas = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    // ------------------------------------------------------------------
    // Registro (administrador)
    // ------------------------------------------------------------------
    public void registrarPelicula(Pelicula pelicula) throws CodigoDuplicadoException {
        for (Pelicula p : peliculas) {
            if (p.getCodigo().equals(pelicula.getCodigo())) {
                throw new CodigoDuplicadoException("Ya existe una pelicula con el codigo " + pelicula.getCodigo());
            }
        }
        peliculas.add(pelicula);
    }

    public void registrarSala(Sala sala) throws CodigoDuplicadoException {
        for (Sala s : salas) {
            if (s.getCodigo().equals(sala.getCodigo())) {
                throw new CodigoDuplicadoException("Ya existe una sala con el codigo " + sala.getCodigo());
            }
        }
        salas.add(sala);
    }

    public void registrarCliente(Cliente cliente) {
        personas.add(cliente);
    }

    public void registrarOperario(Operario operario) {
        personas.add(operario);
    }

    public void registrarAdmin(Admin admin) {
        personas.add(admin);
    }

    public void programarFuncion(Funcion funcion) throws SalaNoDisponibleException, CodigoDuplicadoException {
        if (funcion.getSala().getEstado() == EstadoSala.FUERA_DE_SERVICIO) {
            throw new SalaNoDisponibleException("La sala " + funcion.getSala().getCodigo() + " esta fuera de servicio");
        }
        for (Funcion f : funciones) {
            if (f.getCodigo().equals(funcion.getCodigo())) {
                throw new CodigoDuplicadoException("Ya existe una funcion con el codigo " + funcion.getCodigo());
            }
        }
        funciones.add(funcion);
        // Se enlaza en los dos sentidos: la funcion ya conoce su pelicula y su sala,
        // y ahora la pelicula y la sala tambien conocen sus funciones
        funcion.getPelicula().agregarFuncion(funcion);
        funcion.getSala().agregarFuncion(funcion);
    }

    public void definirTarifa(TipoEntrada tipo, double valor) {
        for (Tarifa t : tarifas) {
            if (t.getTipoEntrada() == tipo) {
                t.modificarValor(valor);   // ya existe: se actualiza
                return;
            }
        }
        tarifas.add(new Tarifa(tipo, valor)); // no existe: se crea
    }

    // ------------------------------------------------------------------
    // Modificacion y deshabilitacion (administrador)
    // ------------------------------------------------------------------
    public void modificarPelicula(String codigo, String nombre, Genero genero,
                                  int duracion, Clasificacion clasificacion) {
        Pelicula pelicula = buscarPelicula(codigo);
        pelicula.modificar(nombre, genero, duracion, clasificacion);
    }

    public void deshabilitarPelicula(String codigo) {
        buscarPelicula(codigo).deshabilitar();
    }

    public void modificarSala(String codigo, String nombre) {
        // La capacidad no se modifica: es filas * asientosPorFila y se calcula al crear la sala
        buscarSala(codigo).setNombre(nombre);
    }

    public void deshabilitarSala(String codigo) {
        buscarSala(codigo).deshabilitar();
    }

    public void cancelarFuncion(String codigo) throws FuncionNoEncontradaException {
        buscarFuncion(codigo).cancelar();
    }

    public void finalizarFuncion(String codigo) throws FuncionNoEncontradaException {
        buscarFuncion(codigo).finalizar();
    }

    // ------------------------------------------------------------------
    // Ventas (operador)
    // ------------------------------------------------------------------
    public Venta realizarVenta(String codigoVenta, Cliente cliente, Funcion funcion,
                               Asiento asiento, TipoEntrada tipo)
            throws FuncionNoEncontradaException, SinAsientosDisponiblesException,
                   AsientoOcupadoException {

        // 1. La funcion debe existir
        Funcion funcionReal = buscarFuncion(funcion.getCodigo());

        // 2. Debe haber asientos disponibles
        if (funcionReal.contarDisponibles() == 0) {
            throw new SinAsientosDisponiblesException("La funcion " + funcionReal.getCodigo() + " esta agotada");
        }

        // 3. El asiento seleccionado debe estar disponible
        if (estaOcupado(funcionReal, asiento)) {
            throw new AsientoOcupadoException("El asiento " + asiento.getCodigo() + " ya esta ocupado");
        }

        // 4. Se calcula el valor, se ocupa el asiento y se registra la venta
        Asiento asientoReal = buscarAsiento(funcionReal, asiento.getCodigo());
        double valor = calcularValorEntrada(funcionReal, tipo);
        asientoReal.ocupar();

        // La fecha y hora (LocalDateTime.now()) se asignan dentro del constructor de Venta
        Venta venta = new Venta(codigoVenta, cliente, funcionReal, asientoReal, tipo, valor);
        ventas.add(venta);
        return venta;
    }

    public void cancelarVenta(String codigoVenta) throws VentaNoEncontradaException {
        Venta venta = buscarVenta(codigoVenta);
        if (!venta.estaActiva()) {
            throw new IllegalStateException("La venta " + codigoVenta + " ya estaba cancelada");
        }
        venta.getAsiento().liberar();
        venta.cancelar();
    }

    public double calcularValorEntrada(Funcion funcion, TipoEntrada tipo) {
        for (Tarifa t : tarifas) {
            if (t.getTipoEntrada() == tipo) {
                return t.getValor();
            }
        }
        throw new IllegalStateException("No hay una tarifa definida para " + tipo);
    }

    public boolean estaOcupado(Funcion funcion, Asiento asiento) {
        for (Asiento a : funcion.getAsientos()) {
            if (a.getCodigo().equals(asiento.getCodigo())) {
                return a.getEstado() == EstadoAsiento.OCUPADO;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Consultas
    // ------------------------------------------------------------------
    public List<Pelicula> consultarPeliculas() {
        return new ArrayList<>(peliculas);
    }

    public List<Sala> consultarSalas() {
        return new ArrayList<>(salas);
    }

    public List<Funcion> consultarFunciones() {
        return new ArrayList<>(funciones);
    }

    /** Devuelve la funcion; el menu usa contarDisponibles(), contarOcupados() y getAsientos() para mostrarla. */
    public Funcion consultarDisponibilidad(String codigoFuncion) throws FuncionNoEncontradaException {
        return buscarFuncion(codigoFuncion);
    }

    // ------------------------------------------------------------------
    // Reportes
    // ------------------------------------------------------------------
    public int totalEntradasVendidas() {
        int total = 0;
        for (Venta v : ventas) {
            if (v.estaActiva()) {
                total++;
            }
        }
        return total;
    }

    public double totalIngresos() {
        double total = 0;
        for (Venta v : ventas) {
            if (v.estaActiva()) {
                total += v.getValorPagado();
            }
        }
        return total;
    }

    /** Devuelve la pelicula con mas entradas vendidas, o null si todavia no hay ventas. */
    public Pelicula peliculaMasVendida() {
        Map<String, Integer> conteo = new HashMap<>();
        for (Venta v : ventas) {
            if (v.estaActiva()) {
                String codigo = v.getFuncion().getPelicula().getCodigo();
                conteo.merge(codigo, 1, Integer::sum);
            }
        }
        String codigoMayor = null;
        int mayor = 0;
        for (Map.Entry<String, Integer> e : conteo.entrySet()) {
            if (e.getValue() > mayor) {
                mayor = e.getValue();
                codigoMayor = e.getKey();
            }
        }
        return (codigoMayor == null) ? null : buscarPelicula(codigoMayor);
    }

    public double calcularOcupacion(String codigoFuncion) throws FuncionNoEncontradaException {
        return buscarFuncion(codigoFuncion).porcentajeOcupacion();
    }

    public List<Funcion> funcionesPorFecha(LocalDate fecha) {
        List<Funcion> resultado = new ArrayList<>();
        for (Funcion f : funciones) {
            if (f.getFecha().equals(fecha) && f.getEstado() == EstadoFuncion.PROGRAMADA) {
                resultado.add(f);
            }
        }
        return resultado;
    }

    // ------------------------------------------------------------------
    // Auxiliares de busqueda (privados)
    // ------------------------------------------------------------------
    private Pelicula buscarPelicula(String codigo) {
        for (Pelicula p : peliculas) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        throw new IllegalArgumentException("No existe la pelicula " + codigo);
    }

    private Sala buscarSala(String codigo) {
        for (Sala s : salas) {
            if (s.getCodigo().equals(codigo)) {
                return s;
            }
        }
        throw new IllegalArgumentException("No existe la sala " + codigo);
    }

    private Funcion buscarFuncion(String codigo) throws FuncionNoEncontradaException {
        for (Funcion f : funciones) {
            if (f.getCodigo().equals(codigo)) {
                return f;
            }
        }
        throw new FuncionNoEncontradaException("No existe la funcion " + codigo);
    }

    private Venta buscarVenta(String codigo) throws VentaNoEncontradaException {
        for (Venta v : ventas) {
            if (v.getCodigo().equals(codigo)) {
                return v;
            }
        }
        throw new VentaNoEncontradaException("No existe la venta " + codigo);
    }

    private Cliente buscarCliente(String identificacion) {
        for (Persona p : personas) {
            // instanceof: solo interesan las personas que son Cliente
            if (p instanceof Cliente && p.getIdentificacion().equals(identificacion)) {
                return (Cliente) p;
            }
        }
        return null;
    }

    private Asiento buscarAsiento(Funcion funcion, String codigoAsiento) {
        for (Asiento a : funcion.getAsientos()) {
            if (a.getCodigo().equals(codigoAsiento)) {
                return a;
            }
        }
        throw new IllegalArgumentException("El asiento " + codigoAsiento + " no pertenece a la funcion");
    }
}
