# CinemaUQ – Proyecto final Programación I

Un solo proyecto Maven con la lógica (paquetes `model` y `exceptions`), las pruebas JUnit
(`CinemaUQTest`) y la interfaz gráfica en JavaFX.

## Cómo ejecutar la interfaz

**Desde IntelliJ**

1. `File > Open` y elegir el `pom.xml` de la raíz del repositorio (Open as Project).
2. Esperar a que Maven descargue JavaFX (si no lo hace solo: panel Maven > botón *Reload*).
3. Abrir `src/main/java/app/Launcher.java` y dar clic derecho > **Run 'Launcher.main()'**.

> Hay que ejecutar `Launcher`, no `App`. Como el proyecto no tiene `module-info.java`,
> ejecutar `App` directamente da el error *"JavaFX runtime components are missing"*.

**Desde la terminal:** `mvn javafx:run`

## Estructura

| Paquete / carpeta | Contenido |
|---|---|
| `model` | `CinemaUQ` y las entidades (Pelicula, Sala, Asiento, Funcion, Venta, Boleto, Tarifa, Cliente, Operario, Administrador…) |
| `exceptions` | Excepciones propias (ElementoDuplicado, SalaNoDisponible, AsientoOcupado, FuncionNoEncontrada, VentaNoEncontrada) |
| `controller` | Un controller por tema: recibe la orden de la pantalla y llama a `CinemaUQ` |
| `viewController` | Un ViewController por pantalla, enlazado a su FXML con `@FXML` |
| `app` | `App` (arranque, navegación, cine compartido y datos de ejemplo) y `Launcher` |
| `resources/app/*.fxml` | Las 17 pantallas |

## Pantallas

- **Cine:** registrar y consultar administradores y operarios, y consultar clientes (buscar por identificación).
- **Administrador:** películas (registrar, buscar, consultar, modificar, deshabilitar, habilitar), salas (registrar,
  buscar, modificar nombre, deshabilitar, habilitar), funciones (programar, buscar), tarifas
  (registrar, consultar, modificar) y reportes (entradas vendidas, ingresos, película más vendida,
  ocupación por función y funciones programadas para una fecha).
- **Operador:** registrar clientes, vender entradas (eligiendo el operario que vende y uno o varios asientos),
  cancelar venta, consultar películas y funciones (filtro por fecha) y disponibilidad de una función.

Los errores que lanza `CinemaUQ` (código repetido, sala fuera de servicio, asiento ocupado,
asientos insuficientes, venta inexistente…) se muestran en una ventana `Alert`.

## Datos de ejemplo (`App.inicializarData`)

4 tarifas, 2 películas (P01, P02), 2 salas (S01 4x8, S02 5x8), 2 funciones para mañana (F01, F02),
1 administrador, 1 operario, 2 clientes (1001, 1002) y 1 venta de 2 boletos en F01.
