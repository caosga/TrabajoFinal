package model;

public class Operario extends Persona implements IVender{
    private double salario;
    /**
     * Relaciones de la clase Operario
     */

    /**
     * Metodo constructor de la clase Operario
     * @param identificacion
     * @param nombre
     * @param correo
     * @param salario
     */
    public Operario(String identificacion, String nombre, String correo, double salario) {
        super(identificacion, nombre, correo);
        this.salario = salario;
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
}
