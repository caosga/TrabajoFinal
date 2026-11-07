package model;

public class Administrador extends Persona{

    private double salario;
    /**
     * Relaciones de la clase Administrador
     */

    /**
     * Metodo constructor de la clase Persona
     * @param identificacion
     * @param nombre
     * @param correo
     * @param salario
     */
    public Administrador(String identificacion, String nombre, String correo, double salario) {
        super(identificacion, nombre, correo);
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
}
