package model;

import java.util.List;

public class Operario extends Persona implements IVender{
    private double salario;
    /**
     * Relaciones de la clase Operario
     */
    private List<Venta> listVentaOperario;

    /**
     * Metodo constructor de la clase Operario
     * @param identificacion
     * @param nombre
     * @param correo
     * @param salario
     */
    public Operario(String identificacion, String nombre, String correo, double salario,List<Venta> listVentaOperario) {
        super(identificacion, nombre, correo);
        this.salario = salario;
        this.listVentaOperario=listVentaOperario;
    }

    public void registrarVentas(){};
    public void venderEntrada(){};
    public void cancelarVenta(){};

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<Venta> getListVentaOperario() {
        return listVentaOperario;
    }

    public void setListVentaOperario(List<Venta> listVentaOperario) {
        this.listVentaOperario = listVentaOperario;
    }
}
