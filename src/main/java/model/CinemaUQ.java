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


    public String agregarPelicula(Pelicula agregaPelicula){
        this.listCinePelicula.add(agregaPelicula);
     return"Pelicula agregada con exito.";
    }

    public String agregarSala(Sala agregarSala) {
        this.listCineSala.add(agregarSala);
     return "Sala agregada exitosamente.";
    }

    public String agregarFuncion(Funcion agregarFuncion){
        this.listCineFuncion.add(agregarFuncion);
      return "Funcion registrada";
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
    public String agregarTarifa(Tarifa agregarTarifa){
        this.listCineTarifa.add(agregarTarifa);
        return "Tarifa agregada";
    }

    /**
     * Metodo para la realizacion de la venta verificando la disponibilidad de asientos en la funcion
     * @param codigoVenta
     * @param nuevoCliente
     * @param operario
     * @param funcion
     * @param boletoComprado
     */
    public void realizarVenta(String codigoVenta, Cliente nuevoCliente, Operario operario,
                               Funcion funcion, List<Boleto>boletoComprado){

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
        // 2. Liberar los asientos ocupados por los boletos de esta venta
        for (Boleto b : ventaACancelar.getListBoleto()) {
            b.asiento().setOcupado(false);
        }
        // 3. Cambiar el estado de la venta
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
