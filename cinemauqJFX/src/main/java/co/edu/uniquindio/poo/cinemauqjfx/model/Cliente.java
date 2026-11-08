package co.edu.uniquindio.poo.cinemauqjfx.model;

public class Cliente extends Persona {

    private TipoCliente tipoCliente;

    public Cliente(String identificacion, String nombre, String correo, TipoCliente tipoCliente) {
        super(identificacion, nombre, correo);
        this.tipoCliente = tipoCliente;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    @Override
    public String obtenerRol() {
        return "Cliente";
    }
}
