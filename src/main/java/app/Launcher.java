package app;

/**
 * Clase para arrancar la interfaz desde IntelliJ (clic derecho > Run 'Launcher.main()').
 *
 * Como el proyecto no tiene module-info.java, si se ejecuta App directamente Java muestra
 * "JavaFX runtime components are missing". Arrancando desde esta clase (que NO hereda
 * de Application) ese error no aparece.
 */
public class Launcher {

    public static void main(String[] args) {
        App.main(args);
    }
}
