package model;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class CinemaUQ {
    private String nombreCine, nit, direccion;
    /**
     * Relaciones de la clase CinemaUQ
     */
    private List<Sala>listCineSala;
    private List<Pelicula>listCinePelicula;
    private List<Funcion>listCineFuncion;
    private List<Tarifa>listCineTarifa;
    private List<Venta>listCineVenta;
    private List<Cliente>listCineCliente;
    private List<Operario>listCineOperario;
    private List<Administrador>listCineAdministrador;

    /**
     * Metodo constructor de la clase CinemaUQ
     * @param nombreCine
     * @param nit
     * @param direccion
     * @param listCinePelicula
     * @param listCineSala
     * @param listCineFuncion
     * @param listCineTarifa
     * @param listCineVenta
     * @param listCineCliente
     * @param listCineOperario
     * @param listCineAdministrador
     */
    public CinemaUQ(String nombreCine, String nit, String direccion, List<Pelicula> listCinePelicula,
                    List<Sala> listCineSala, List<Funcion> listCineFuncion, List<Tarifa> listCineTarifa,
                    List<Venta> listCineVenta, List<Cliente> listCineCliente, List<Operario> listCineOperario,
                    List<Administrador> listCineAdministrador) {
        this.nombreCine = nombreCine;
        this.nit = nit;
        this.direccion = direccion;
        this.listCinePelicula = listCinePelicula;
        this.listCineSala = listCineSala;
        this.listCineFuncion = listCineFuncion;
        this.listCineTarifa = listCineTarifa;
        this.listCineVenta = listCineVenta;
        this.listCineCliente = listCineCliente;
        this.listCineOperario = listCineOperario;
        this.listCineAdministrador = listCineAdministrador;
    }

    /**
     * Metodo para buscar una pelicula por su codigo
     */
    public Pelicula buscarPelicula(String codigoPel) {
        for (Pelicula p : this.listCinePelicula) {
            if (p.getCodigoPel().equalsIgnoreCase(codigoPel)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Metodo para registrar una pelicula verificando duplicados
     * @param codigoPel
     * @param nombre
     * @param duracion
     * @param genero
     * @param clasificacion
     * @return
     */
    public String registrarPelicula(String codigoPel, String nombre, int duracion, Genero genero, Clasificacion clasificacion) {
        if (buscarPelicula(codigoPel) != null) {
            throw new IllegalArgumentException("Ya existe una película registrada con el código: " + codigoPel);
        }

        Pelicula nuevaPelicula = new Pelicula( codigoPel, nombre, duracion, EstadoPelicula.DISPONIBLE, genero, clasificacion, new ArrayList<>() );
        this.listCinePelicula.add(nuevaPelicula);

        return "Película registrada exitosamente.";
    }


    /**
     * Metodo para buscar una sala por su codigo
     */
    public Sala buscarSala(String codigoSala) {
        for (Sala s : this.listCineSala) {
            if (s.getCodigoSala().equalsIgnoreCase(codigoSala)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Metodo para registrar una sala y sus asientos
     */
    public String registrarSala(String codigoSala, String nombre, int capacidad) {
        if (buscarSala(codigoSala) != null) {
            throw new IllegalArgumentException("Ya existe una sala registrada con el código: " + codigoSala);
        }

        List<Asiento> asientosSala = getAsientos(codigoSala, capacidad);

        Sala nuevaSala = new Sala( codigoSala, nombre, capacidad, EstadoSala.DISPONIBLE, asientosSala, new ArrayList<>()
        );

        this.listCineSala.add(nuevaSala);

        return "Sala registrada exitosamente con " + capacidad + " asientos.";
    }

    /**
     * Metodo para la creacion de los asientos, según capacidad, para el metodo registrarSala
     * @param codigoSala
     * @param capacidad
     * @return
     */
    private static List<Asiento> getAsientos(String codigoSala, int capacidad) {
        List<Asiento> asientosSala = new ArrayList<>();

        for (int i = 1; i <= capacidad; i++) {
            String codigoAsiento = codigoSala + "-A" + i;

            Asiento nuevoAsiento = new Asiento( codigoAsiento,  "A",  i,  EstadoAsiento.DISPONIBLE, false,  null, new ArrayList<>()
            );
            asientosSala.add(nuevoAsiento);
        }
        return asientosSala;
    }

    /**
     * Metodo para consultar las salas registradas
     */
    public List<Sala> consultarSalas() {
        return this.listCineSala;
    }

    /**
     * Metodo para modificar
     */
    public String modificarSala(String codigoSala, String nuevoNumero, int nuevaCapacidad, EstadoSala estadoSala) {
        Sala salaAModificar = buscarSala(codigoSala);

        if (salaAModificar == null) {
            throw new IllegalArgumentException("No se puede modificar: La sala con código '" + codigoSala + "' no existe.");
        }

        salaAModificar.setCodigoSala(codigoSala);
        salaAModificar.setNombre(nuevoNumero);
        salaAModificar.setCapcacidad(nuevaCapacidad);
        salaAModificar.setEstado(estadoSala);

        return "Información de la sala actualizada exitosamente.";
    }

    /**
     * Metodo para cambiar el estado de una sala por codigo
     */
    public String deshabilitarSala(String codigoSala) {
        Sala salaADeshabilitar = buscarSala(codigoSala);

        if (salaADeshabilitar == null) {
            throw new IllegalArgumentException("No se puede deshabilitar: La sala con código '" + codigoSala + "' no existe.");
        }

        if (salaADeshabilitar.getEstado() == EstadoSala.FUERA_DE_SERVICIO) {
            throw new IllegalStateException("La sala ya se encuentra fuera de servicio.");
        }

        salaADeshabilitar.setEstado(EstadoSala.FUERA_DE_SERVICIO);

        return "Sala deshabilitada (fuera de servicio) exitosamente.";
    }

    /**
     * Metodo para cambiar estado, habilitar una sala inhabilitada
     */
    public String habilitarSala(String codigoSala) {
        Sala salaAHabilitar = buscarSala(codigoSala);

        if (salaAHabilitar == null) {
            throw new IllegalArgumentException("No se puede habilitar: La sala con código '" + codigoSala + "' no existe.");
        }

        if (salaAHabilitar.getEstado() == EstadoSala.DISPONIBLE) {
            throw new IllegalStateException("La sala ya se encuentra disponible.");
        }
        salaAHabilitar.setEstado(EstadoSala.DISPONIBLE);

        return "Sala habilitada exitosamente.";
    }

    /**
     * Metodo para programar una funcion validando la sala
     */
    public String programarFuncion(String codigoFuncion, LocalDateTime fechaHora, double valorBase,
                                   String codigoPelicula, String codigoSala) {
        /**
         * Busca la pelicula por su codigo
         */
        Pelicula pelicula = buscarPelicula(codigoPelicula);
        if (pelicula == null) {
            throw new IllegalArgumentException("No se puede programar: La película con código '" + codigoPelicula + "' no existe.");
        }
        if (pelicula.getEstadoPelicula() == EstadoPelicula.NO_DISPONIBLE) {
            throw new IllegalStateException("No se puede programar con una película deshabilitada.");
        }
        /**
         * Busca la sala por su codigo
         */
        Sala sala = buscarSala(codigoSala);
        if (sala == null) {
            throw new IllegalArgumentException("No se puede programar: La sala con código '" + codigoSala + "' no existe.");
        }

        if (sala.getEstado() == EstadoSala.FUERA_DE_SERVICIO) {
            throw new IllegalStateException("Operación denegada: La sala seleccionada se encuentra fuera de servicio.");
        }
        /**
         * Copia la lista de asientos para esta funcion
         */
        List<Asiento> asientosFuncion = new ArrayList<>();
        for (Asiento aOriginal : sala.getListAsiento()) {
            Asiento asientoFuncion = new Asiento( aOriginal.getCodigoAsiento(), aOriginal.getFila(), aOriginal.getNumero(),
                    EstadoAsiento.DISPONIBLE,  false,  null,  new ArrayList<>()
            );
            asientosFuncion.add(asientoFuncion);
        }
        /**
        * Crea la funcion
        */
        Funcion nuevaFuncion = new Funcion(codigoFuncion, fechaHora, valorBase, pelicula, EstadoFuncion.PROGRAMADA,
                sala, asientosFuncion, new ArrayList<>()
        );
        this.listCineFuncion.add(nuevaFuncion);

        /**
         * Registra la funcion en la lista de sala y pelicula
         */
        sala.getListFuncionSala().add(nuevaFuncion);
        pelicula.getListFuncionPelicula().add(nuevaFuncion);

        return "Función programada exitosamente.";
    }

    /**
     * Metodo para registrar una tarifa diferenciando el tipo de entrada
     */
    public String registrarTarifa(double valor, TipoEntrada tipoEntrada) {
        /**
         * Validacion de tarifa existente
         */
        if (buscarTarifa(tipoEntrada) != null) {
            throw new IllegalArgumentException("Ya existe una tarifa registrada para el tipo de entrada: " + tipoEntrada);
        }
        /**
         * Crea la tarifa
         */
        Tarifa nuevaTarifa = new Tarifa(valor, tipoEntrada, new ArrayList<>()
        );
        this.listCineTarifa.add(nuevaTarifa);
        return "Tarifa registrada exitosamente para " + tipoEntrada + ".";
    }

    /**
     * Metodo para consultar las tarifas
     */
    public List<Tarifa> consultarTarifas() {
        return this.listCineTarifa;
    }

    /**
     * Metodo para modificar una tarifa
     */
    public String modificarTarifa(TipoEntrada tipoEntrada, double nuevoValor) {
        Tarifa tarifaAModificar = buscarTarifa(tipoEntrada);
        /**
         *Busca que si exista la tarifa a modificar
         */
        if (tarifaAModificar == null) {
            throw new IllegalArgumentException("No se encontró una tarifa registrada para el tipo de entrada especificado.");
        }
        /**
         * Modifica el valor de la tarifa
         */
        tarifaAModificar.setValor(nuevoValor);

        return "Valor de la tarifa actualizado exitosamente.";
    }

    /**
     * Metodo para buscar una tarifa según su tipo de entrada
     */
    public Tarifa buscarTarifa(TipoEntrada tipoEntrada) {
        for (Tarifa t : this.listCineTarifa) {
            if (t.getTipoEntrada() == tipoEntrada) { // Al ser un Enum, se puede comparar de forma segura con ==
                return t;
            }
        }
        return null;
    }

    public String agregarCliente(Cliente agregarCliente){
        this.listCineCliente.add(agregarCliente);
        return "Cliente registrado exitosamente";
    }

    public  String agregarOperario(Operario agregarOperario){
        this.listCineOperario.add(agregarOperario);
        return "Operario registrado";
    }

    public String agregarAdministrador(Administrador agregarAdministrador){
        this.listCineAdministrador.add(agregarAdministrador);
        return "Administrador registrado";
    }


    /**
     * Metodo para la realizacion de la venta verificando la disponibilidad de asientos en la funcion
     * @param codigoVenta
     * @param nuevoCliente
     * @param operario
     * @param funcion
     * @param boletoComprado
     */
    public void realizarVenta(String codigoVenta, Cliente nuevoCliente, Operario operario, Funcion funcion, List<Boleto>boletoComprado){

        List<Boleto>boletosVendidos=funcion.verificaVenta(boletoComprado);

        List<Asiento> asientoVedido = new ArrayList<>();

        for(Boleto b:boletoComprado){
        asientoVedido.add(b.asiento());
        }

        Venta ventaNueva= new Venta(codigoVenta, LocalDateTime.now(), EstadoVenta.ACTIVA, boletosVendidos.get(0).tarifa(),
                funcion, asientoVedido, nuevoCliente, operario, boletosVendidos);
        this.listCineVenta.add(ventaNueva);
        funcion.registraVenta(ventaNueva);
    }

    /**
     * Metodo para cancelar la venta de una entrada
     * @param codigoVenta
     * @return
     */
    public boolean cancelarVenta(String codigoVenta) {
        Venta ventaACancelar = buscarVenta(codigoVenta);
        if (ventaACancelar == null) {
            throw new IllegalArgumentException("No existe una venta registrada con el código: " + codigoVenta);
        }
        if (ventaACancelar.getEstadoVenta() == EstadoVenta.CANCELADA) {
            throw new IllegalStateException("La venta ya se encuentra cancelada.");
        }

        for (Boleto b : ventaACancelar.getListBoleto()) {
            b.asiento().setOcupado(false);
        }
        ventaACancelar.setEstadoVenta(EstadoVenta.CANCELADA);

        return true;
    }

    public Venta buscarVenta(String codigoVenta) {
        for (Venta v: this.listCineVenta) {
            if (v.getCodigoVenta().equalsIgnoreCase(codigoVenta)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Metodo para consultar peliculas disponibles
     * @return
     */
    public List<Pelicula> consultarPeliculas() {
        List<Pelicula> peliculasDisponibles = new ArrayList<>();

        for (Pelicula p : this.listCinePelicula) {
            if (p.getEstadoPelicula() == EstadoPelicula.DISPONIBLE) {
            peliculasDisponibles.add(p);
             }
        }
        return peliculasDisponibles;
    }

    /**
     * Metodo para consultar las funciones programadas
     * @return
     */
    public List<Funcion> consultarFunciones() {
        return this.listCineFuncion;
    }


    /**
     * Metodo para generar el reporte genera de entradas vendidas
     */
    public int calcularTotalEntradasVendidas() {
        int totalEntradas = 0;
        for (Venta v : this.listCineVenta) {
            if (v.getEstadoVenta() != EstadoVenta.CANCELADA) { // Nota: ajustado a getEstadoVenta() según tu atributo
                totalEntradas += v.getListBoleto().size(); // Usando listBoleto
            }
        }
        return totalEntradas;
    }

    /**
     * Metodo para generar reporte de los ingresos por entradas vendidas
     */
    public double calcularTotalIngresos() {
        double totalIngresos = 0.0;
        for (Venta v : this.listCineVenta) {
            if (v.getEstadoVenta() != EstadoVenta.CANCELADA) {
                // Si la venta calcula el total multiplicando la tarifa por los boletos:
                double valorTarifa = v.getTheTarifa().getValor();
                int cantidadBoletos = v.getListBoleto().size();
                totalIngresos += (valorTarifa * cantidadBoletos);
            }
        }
        return totalIngresos;
    }

    /**
     * Metodo para obtener la pelicula más vendida
     * @return
     */
    public Pelicula obtenerPeliculaMasVendida() {
        if (this.listCinePelicula.isEmpty()) {
            return null;
        }

        Pelicula peliculaMasVendida = null;
        int maxEntradas = -1;

        for (Pelicula p : this.listCinePelicula) {
            int entradasPelicula= 0;

            for (Venta v : this.listCineVenta) {
                if (v.getEstadoVenta() != EstadoVenta.CANCELADA) {

                    if (v.getTheFuncion() != null && v.getTheFuncion().getThePelicula().equals(p)) {

                        entradasPelicula += v.getListBoleto().size();
                    }
                }
            }

            if (entradasPelicula > maxEntradas) {
                maxEntradas = entradasPelicula;
                peliculaMasVendida = p;
            }
        }
        return peliculaMasVendida;
    }

    /**
     * Metodo para generar un reporte de porcentaje de ocupacion de una funcion
     */
    public ReporteOcupacionFuncion generarReporteOcupacion(Funcion funcion) {
        if (funcion == null) {
            throw new IllegalArgumentException("La función especificada no existe.");
        }

        int totalAsientos = funcion.getListAsientoFuncion().size();
        int asientosDisponibles = funcion.contarAsientosDisponibles();
        int asientosOcupados = totalAsientos - asientosDisponibles;

        double porcentajeOcupacion = 0.0;
        if (totalAsientos > 0) {
            porcentajeOcupacion = (double) asientosOcupados / totalAsientos * 100.0;
        }

        return new ReporteOcupacionFuncion(asientosDisponibles, asientosOcupados, porcentajeOcupacion);
    }


    public String getNombreCine() {
        return nombreCine;
    }

    public void setNombreCine(String nombreCine) {
        this.nombreCine = nombreCine;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Sala> getListCineSala() {
        return listCineSala;
    }

    public void setListCineSala(List<Sala> listCineSala) {
        this.listCineSala = listCineSala;
    }

    public List<Pelicula> getListCinePelicula() {
        return listCinePelicula;
    }

    public void setListCinePelicula(List<Pelicula> listCinePelicula) {
        this.listCinePelicula = listCinePelicula;
    }

    public List<Funcion> getListCineFuncion() {
        return listCineFuncion;
    }

    public void setListCineFuncion(List<Funcion> listCineFuncion) {
        this.listCineFuncion = listCineFuncion;
    }

    public List<Tarifa> getListCineTarifa() {
        return listCineTarifa;
    }

    public void setListCineTarifa(List<Tarifa> listCineTarifa) {
        this.listCineTarifa = listCineTarifa;
    }

    public List<Venta> getListCineVenta() {
        return listCineVenta;
    }

    public void setListCineVenta(List<Venta> listCineVenta) {
        this.listCineVenta = listCineVenta;
    }

    public List<Cliente> getListCineCliente() {
        return listCineCliente;
    }

    public void setListCineCliente(List<Cliente> listCineCliente) {
        this.listCineCliente = listCineCliente;
    }

    public List<Operario> getListCineOperario() {
        return listCineOperario;
    }

    public void setListCineOperario(List<Operario> listCineOperario) {
        this.listCineOperario = listCineOperario;
    }

    public List<Administrador> getListCineAdministrador() {
        return listCineAdministrador;
    }

    public void setListCineAdministrador(List<Administrador> listCineAdministrador) {
        this.listCineAdministrador = listCineAdministrador;
    }
}
