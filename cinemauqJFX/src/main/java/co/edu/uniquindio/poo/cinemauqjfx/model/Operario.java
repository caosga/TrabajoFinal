package co.edu.uniquindio.poo.cinemauqjfx.model;

/** El operario es quien vende y cancela entradas (implementa IVender). */
public class Operario extends Persona implements IVender {

    private double salario;

    public Operario(String identificacion, String nombre, String correo, double salario) {
        super(identificacion, nombre, correo);
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    @Override
    public String obtenerRol() {
        return "Operario";
    }

    @Override
    public Venta venderEntrada(Cine cine, String codigoVenta, Cliente cliente, Funcion funcion,
                               Asiento asiento, TipoEntrada tipo)
            throws FuncionNoEncontradaException, SinAsientosDisponiblesException,
                   AsientoOcupadoException {
        return cine.realizarVenta(codigoVenta, cliente, funcion, asiento, tipo);
    }

    @Override
    public void cancelarVenta(Cine cine, String codigoVenta) throws VentaNoEncontradaException {
        cine.cancelarVenta(codigoVenta);
    }
}
