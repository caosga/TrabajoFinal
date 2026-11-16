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
| `controller` | Un controller por tema (`Pelicula`, `Sala`, `Tarifa`, `Funcion`, `Cliente`, `Venta`, `Consulta`, `Reporte`): pasan las órdenes de la vista al `Cine` |
| `viewController` | Un ViewController por pantalla, enlazado a su FXML con `@FXML`; `Utilidades` (mensajes `Alert`, lectura de campos, combos) y `CuadriculaAsientos` (dibuja los asientos) |
| `resources/.../*.fxml` | `primary`, `menuAdmin`, `menuOperador`, `crudPelicula`, `crudSala`, `crudTarifa`, `crudFuncion`, `crudCliente`, `venta`, `cancelarVenta`, `consultas`, `disponibilidad`, `reportes` |
| `App.java` | Arranque; tiene el `Cine` compartido (`App.cine`), el operario (`App.operario`) y carga datos de ejemplo |

## Estado

- Administrador: películas, salas, tarifas, programar funciones y reportes (entradas vendidas,
  ingresos, película más vendida y ocupación por función).
- Operador: registrar clientes, vender entrada (cuadrícula de asientos), cancelar venta,
  consultar películas y funciones (con filtro por fecha) y disponibilidad de asientos.
- Reglas que valida el `Cine` y que se muestran en una ventana `Alert`: código repetido, sala fuera de
  servicio, película NO_DISPONIBLE, choque de horario en la misma sala, asiento ocupado, función
  agotada, función cancelada o finalizada, venta ya cancelada y tarifa no definida.

## Datos de ejemplo (App.inicializarData)

Tarifas, 2 películas (P1, P2), 2 salas (S1 3x5, S2 4x6), 2 funciones (mañana), 1 administrador,
1 operario, 2 clientes y 2 ventas de ejemplo en la función F1 (asientos A1 y A2).
