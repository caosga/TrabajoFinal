package uniquindio.edu.co;

public class Pelicula {

    private String codigo;
    private String nombre;
    private Genero genero;
    private int duracion;
    private Clasificacion clasificacion;
    private EstadoPelicula estado;

    public Pelicula(String codigo,String nombre, Genero genero, int duracion, Clasificacion clasificacion,EstadoPelicula estado){
        this.codigo=codigo;
        this.nombre = nombre;
        this.genero= genero;
        this.duracion=duracion;
        this.clasificacion= clasificacion;
        this.estado=EstadoPelicula.DISPONIBLE;

    }
    public void modificar(String nombre, Genero genero, int duracion, Clasificacion clasificacion) {
        this.nombre = nombre;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
    }

    public void deshabilitar() {
        this.estado = EstadoPelicula.NO_DISPONIBLE;
    }

    public boolean estaDisponible() {
        return estado == EstadoPelicula.DISPONIBLE;
    }
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Genero getGenero() {
        return genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public EstadoPelicula getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return nombre + " | " + genero + " | " + duracion + " min | " + clasificacion
                + " | " + estado;
    }
}
