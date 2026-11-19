package controller;

import exceptions.ElementoDuplicadoException;
import model.CinemaUQ;
import model.Administrador;

import java.util.List;

/** Intermediario entre la pantalla de administradores (menú del cine) y CinemaUQ. */
public class AdministradorController {

    private final CinemaUQ cine;

    public AdministradorController(CinemaUQ cine) {
        this.cine = cine;
    }

    public String registrar(String identificacion, String nombre, String correo, double salario)
            throws ElementoDuplicadoException {
        for (Administrador a : cine.getListCineAdministrador()) {
            if (a.getIdentificacion().equalsIgnoreCase(identificacion)) {
                throw new ElementoDuplicadoException("Ya existe un administrador con la identificación: " + identificacion);
            }
        }
        return cine.agregarAdministrador(new Administrador(identificacion, nombre, correo, salario));
    }

    public List<Administrador> listar() {
        return cine.getListCineAdministrador();
    }
}
