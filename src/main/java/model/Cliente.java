package model;

import java.util.List;

public class Cliente extends Persona{
    private TipoCliente tipoCliente;
    /**
     * Relaciones de la clase Cliente
     */
    private List<Venta>listVentaCliente;

    /**
     * Metodo constructor de la clase Cliente
     * @param identificacion
     * @param nombre
     * @param correo
     * @param tipoCliente
     * @param listVentaCliente
     */
    public Cliente(String identificacion, String nombre, String correo, TipoCliente tipoCliente, List<Venta> listVentaCliente) {
        super(identificacion, nombre, correo);
        this.tipoCliente = tipoCliente;
        this.listVentaCliente = listVentaCliente;
    }


    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public List<Venta> getListVentaCliente() {
        return listVentaCliente;
    }

    public void setListVentaCliente(List<Venta> listVentaCliente) {
        this.listVentaCliente = listVentaCliente;
    }
}
