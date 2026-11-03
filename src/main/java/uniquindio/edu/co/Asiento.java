package uniquindio.edu.co;

public class Asiento {
    private String codigo;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(String codigo, String fila, int numero) {
        this.codigo = codigo;
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public void ocupar() {
        this.estado = EstadoAsiento.OCUPADO;
    }

    public void liberar() {
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getFila() {
        return fila;
    }

    public int getNumero() {
        return numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return codigo + " " + (estado == EstadoAsiento.DISPONIBLE ? "Disponible" : "Ocupado");
    }
}
