package co.edu.uniquindio.poo.cinemauqjfx.model;

public class Admin extends Persona {

    private double salario;

    public Admin(String identificacion, String nombre, String correo, double salario) {
        super(identificacion, nombre, correo);
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    @Override
    public String obtenerRol() {
        return "Administrador";
    }
}
