# CINEMAUQ – JavaFX (MVC)

Proyecto Maven con JavaFX 21, siguiendo la guía de la práctica (paquetes `model`, `controller`,
`viewController`, FXML en `resources`).

## Cómo abrirlo en IntelliJ

1. `File > Open` y seleccionar el archivo `pom.xml` de esta carpeta (Open as Project).
2. Configurar el JDK 21 (`File > Project Structure > Project`).
3. Esperar a que Maven descargue las dependencias (JavaFX 21.0.5).
4. Ejecutar la clase `App` (clic derecho > Run 'App.main()'), o desde la terminal: `mvn javafx:run`.

## Estructura

| Paquete / carpeta | Contenido |
|---|---|
| `model` | `Cine` (equivale a `Empresa` de la guía), entidades, enums y excepciones del dominio |
| `controller` | `PeliculaController` (pasa las órdenes de la vista al `Cine`) |
| `viewController` | Un ViewController por pantalla, enlazado a su FXML con `@FXML` |
| `resources/.../*.fxml` | `primary`, `menuAdmin`, `menuOperador`, `crudPelicula` |
| `App.java` | Arranque; tiene el `Cine` compartido (`App.cine`) y carga datos de ejemplo |

## Estado

- Listo: pantalla de inicio (rol), menú del administrador, menú del operador y gestión de películas
  (registrar, actualizar, deshabilitar, tabla, mensajes de error con `Alert`).
- Pendiente: salas, funciones, tarifas, venta con selección de asientos, cancelación, consultas y reportes.

## Datos de ejemplo (App.inicializarData)

Tarifas, 2 películas (P1, P2), 2 salas (S1 3x5, S2 4x6), 2 funciones (mañana), 1 administrador,
1 operario y 1 cliente.
