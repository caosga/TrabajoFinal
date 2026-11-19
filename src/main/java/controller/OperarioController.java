package controller;

import exceptions.ElementoDuplicadoException;
import model.CinemaUQ;
import model.Operario;

import java.util.ArrayList;
import java.util.List;

/** Intermediario entre la pantalla de operarios (menú del cine) y CinemaUQ. */
public class OperarioController {

    private final CinemaUQ cine;

    public OperarioController(CinemaUQ cine) {
        this.cine = cine;
    }

    public String registrar(String identificacion, String nombre, String correo, double salario)
            throws ElementoDuplicadoException {
        for (Operario o : cine.getListCineOperario()) {
            if (o.getIdentificacion().equalsIgnoreCase(identificacion)) {
                throw new ElementoDuplicadoException("Ya existe un operario con la identificación: " + identificacion);
            }
        }
        return cine.agregarOperario(new Operario(identificacion, nombre, correo, salario, new ArrayList<>()));
    }

    public List<Operario> listar() {
        return cine.getListCineOperario();
    }
}
