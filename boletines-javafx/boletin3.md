# Prácticas de JavaFX - Sesión 3

En esta última práctica se verán otras cuestiones aún no estudiadas de JavaFX, como el manejo de eventos desde la interfaz gráfica, el uso de controles de tipo lista y tabla, así como cuestiones de personalización gráficas tales como el uso de imágenes en controles y el uso de temas con propiedades visuales basadas en CSS.

## Eventos

Un evento es una acción del usuario o del sistema que ocurre sobre un componente de la interfaz gráfica (ej.: clic en un botón, pulsar una tecla, mover el ratón, etc.).

JavaFX usa el Modelo de Delegación de Eventos, igual que Swing:

* Un objeto origen (**source**) genera el evento.
* Un objeto oyente (**listener** o **handler**) lo recibe y ejecuta una acción.

Todo evento en JavaFX tiene tres elementos:

| Elemento                     | Descripción                     | Ejemplo                                         |
| ---------------------------- | ------------------------------- | ----------------------------------------------- |
| **Origen (source)**          | Control que genera el evento    | Un botón (`Button`)                             |
| **Evento (Event)**           | Objeto que describe qué ocurrió | `ActionEvent`, `MouseEvent`, `KeyEvent`         |
| **Manejador (EventHandler)** | Código que responde al evento   | Lambda o clase que implementa `EventHandler<T>` |

Todos los eventos de JavaFX son tipos de ```javafx.event.Event ```. A continuación se muestra una tabla con el tipo de evento, su clase Java asociada y qué componentes suelen generarlo.

| **Categoría**                                          | **Clase de evento**                                 | **Descripción / Cuándo ocurre**                                                                                                                                                                                    | **Controles típicos**                                                  |
| ------------------------------------------------------ | --------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------- |
| **Eventos base**                                       | `Event`                                             | Clase raíz de todos los eventos.                                                                                                                                                                                   | Todos los nodos                                                        |
| **Eventos de acción**                                  | `ActionEvent`                                       | Acción ejecutada (por clic, ENTER, etc.).                                                                                                                                                                          | `Button`, `MenuItem`, `CheckBox`, `ComboBox`, `Hyperlink`, `TextField` |
| **Eventos de ratón**                                   | `MouseEvent`                                        | Movimiento o clic del ratón. Incluye subtipos: `MOUSE_PRESSED`, `MOUSE_RELEASED`, `MOUSE_CLICKED`, `MOUSE_MOVED`, `MOUSE_DRAGGED`, `MOUSE_ENTERED`, `MOUSE_EXITED`, `MOUSE_ENTERED_TARGET`, `MOUSE_EXITED_TARGET`. | Cualquier `Node` visible                                               |
| **Eventos de teclado**                                 | `KeyEvent`                                          | Pulsación o liberación de teclas. Subtipos: `KEY_PRESSED`, `KEY_RELEASED`, `KEY_TYPED`.                                                                                                                            | Cualquier nodo con foco                                                |
| **Eventos de desplazamiento (scroll)**                 | `ScrollEvent`                                       | Movimiento de la rueda del ratón o gesto táctil de scroll.                                                                                                                                                         | `ScrollPane`, `ListView`, `TableView`                                  |
| **Eventos de zoom**                                    | `ZoomEvent`                                         | Gestos multitáctiles de zoom (pinch).                                                                                                                                                                              | Superficies táctiles                                                   |
| **Eventos de rotación**                                | `RotateEvent`                                       | Gesto táctil de rotación.                                                                                                                                                                                          | Superficies multitáctiles                                              |
| **Eventos de swipe (deslizamiento)**                   | `SwipeEvent`                                        | Gestos de deslizamiento con el dedo. Tipos: `SWIPE_LEFT`, `SWIPE_RIGHT`, `SWIPE_UP`, `SWIPE_DOWN`.                                                                                                                 | Pantallas táctiles                                                     |
| **Eventos de toque (touch)**                           | `TouchEvent`                                        | Eventos multitáctiles genéricos (presionar, mover, soltar).                                                                                                                                                        | Pantallas táctiles                                                     |
| **Eventos de arrastre y soltar**                       | `DragEvent`                                         | Arrastre y suelta de objetos (drag & drop). Tipos: `DRAG_DETECTED`, `DRAG_OVER`, `DRAG_DROPPED`, `DRAG_ENTERED`, `DRAG_EXITED`, `DRAG_DONE`.                                                                       | `ListView`, `ImageView`, `TextArea`, `Pane`                            |
| **Eventos de contexto (menú contextual)**              | `ContextMenuEvent`                                  | Activación del menú contextual (clic derecho o gesto equivalente).                                                                                                                                                 | Cualquier `Node`                                                       |
| **Eventos de entrada de texto**                        | `InputMethodEvent`                                  | Entrada de texto compleja (IME, caracteres multibyte).                                                                                                                                                             | `TextInputControl`, `TextField`, `TextArea`                            |
| **Eventos de foco**                                    | `FocusEvent` *(desde JavaFX 21)*                    | Entrada o salida de foco del teclado.                                                                                                                                                                              | Cualquier `Node`                                                       |
| **Eventos de scroll horizontal / vertical**            | `ScrollToEvent<T>`                                  | Desplazamiento hacia un elemento concreto.                                                                                                                                                                         | `TableView`, `ListView`                                                |
| **Eventos de clasificación / ordenación**              | `SortEvent<T>`                                      | Solicitud de ordenar elementos.                                                                                                                                                                                    | `TableView`, `TreeTableView`                                           |
| **Eventos de celda**                                   | `CellEditEvent<S,T>`                                | Modificación de una celda editable.                                                                                                                                                                                | `TableView`, `TreeTableView`                                           |
| **Eventos de ventana (Stage)**                         | `WindowEvent`                                       | Afectan a la ventana principal. Tipos: `WINDOW_SHOWN`, `WINDOW_HIDDEN`, `WINDOW_CLOSE_REQUEST`, `WINDOW_HIDING`, `WINDOW_SHOWING`.                                                                                 | `Stage`                                                                |
| **Eventos de entrada/salida del ratón (solo destino)** | `MouseDragEvent`                                    | Arrastre del ratón sobre nodos específicos.                                                                                                                                                                        | `Node`                                                                 |
| **Eventos de cambio de selección**                     | `SelectionEvent` *(indirecto)*                      | No hay clase específica, pero se manejan mediante `ChangeListener` sobre `SelectedItemProperty`.                                                                                                                   | `ListView`, `ComboBox`, `TableView`                                    |
| **Eventos de animación**                               | `ActionEvent` (en transiciones)                     | Finalización de una animación o transición.                                                                                                                                                                        | `Timeline`, `Transition`, `Animation`                                  |
| **Eventos de escena**                                  | `SceneEvent` *(subtipo de Event)*                   | Cambios en la escena. (Poco usado directamente).                                                                                                                                                                   | `Scene`                                                                |
| **Eventos de medios (audio/video)**                    | `MediaErrorEvent`, `MediaMarkerEvent`, `MediaEvent` | Cambios o errores en reproducción multimedia.                                                                                                                                                                      | `MediaPlayer`, `MediaView`                                             |
| **Eventos de arranque de aplicación**                  | `Event` (genérico)                                  | Se manejan dentro de `Application.start()` o `init()`.                                                                                                                                                             | `Application`                                                          |
| **Eventos personalizados**                             | Subclases de `Event` definidas por el desarrollador | Permiten crear tus propios tipos de eventos.                                                                                                                                                                       | Cualquier clase                                                        |

Los subtipos del evento indicados en la tabla anterior se representan en JavaFX como constantes estáticas del tipo ```EventType<T>```. 

Por ejemplo, para ```MouseEvent```, tenemos subtipos de ```EventType<MouseEvent>``` definidos en la clase MouseEvent como constantes estáticas, para representar cada subtipo de evento de ratón.

```
public class MouseEvent extends InputEvent {
    // Constantes que representan distintos tipos de eventos de ratón
    public static final EventType<MouseEvent> MOUSE_PRESSED;
    public static final EventType<MouseEvent> MOUSE_RELEASED;
    public static final EventType<MouseEvent> MOUSE_CLICKED;
    public static final EventType<MouseEvent> MOUSE_MOVED;
    public static final EventType<MouseEvent> MOUSE_DRAGGED;
    public static final EventType<MouseEvent> MOUSE_ENTERED;
    public static final EventType<MouseEvent> MOUSE_EXITED;
    // ...
}
``` 

* Cada constante es un ```EventType<T>```, donde T es la clase de evento (```MouseEvent``` en este caso).
* Representa un tipo concreto de evento que puede ocurrir, como “clic del ratón” o “ratón presionado”.
* Permite que los manejadores se registren solo para un tipo específico de evento.

```
btn.addEventHandler(MouseEvent.MOUSE_PRESSED,
    e -> System.out.println("Botón presionado"));

btn.addEventHandler(MouseEvent.MOUSE_CLICKED,
    e -> System.out.println("Botón clicado"));

```

* **MOUSE_PRESSED** se dispara al presionar el botón del ratón (no hace falta soltarlo).
* **MOUSE_CLICKED** se dispara cuando se presiona y suelta en el mismo nodo → es un “clic completo”.

Entre todos los subtipos de un evento siempre existe un subtipo ```XXX_ANY```que representa a todos los eventos de ese tipo. Es decir:

| Clase de evento | Constante `ANY`   | Qué captura                                                                           |
| --------------- | ----------------- | ------------------------------------------------------------------------------------- |
| `MouseEvent`    | `MouseEvent.ANY`  | Todos los eventos de ratón                                                            |
| `KeyEvent`      | `KeyEvent.ANY`    | Todos los eventos de teclado (`KEY_PRESSED`, `KEY_RELEASED`, `KEY_TYPED`)             |
| `TouchEvent`    | `TouchEvent.ANY`  | Todos los eventos táctiles (`TOUCH_PRESSED`, `TOUCH_MOVED`, `TOUCH_RELEASED`)         |
| `ScrollEvent`   | `ScrollEvent.ANY` | Todos los eventos de desplazamiento (`SCROLL`, `SCROLL_STARTED`, `SCROLL_FINISHED`)   |
| `ZoomEvent`     | `ZoomEvent.ANY`   | Todos los eventos de zoom (`ZOOM`, `ZOOM_STARTED`, `ZOOM_FINISHED`)                   |
| `RotateEvent`   | `RotateEvent.ANY` | Todos los eventos de rotación                                                         |
| `SwipeEvent`    | `SwipeEvent.ANY`  | Todos los swipes (`SWIPE_LEFT`, `SWIPE_RIGHT`, `SWIPE_UP`, `SWIPE_DOWN`)              |
| `DragEvent`     | `DragEvent.ANY`   | Todos los eventos de drag & drop (`DRAG_DETECTED`, `DRAG_OVER`, `DRAG_DROPPED`, etc.) |

Es decir, si queremos capturar cualquier evento de la clase sin especificar subtipos, usaremos XXX_ANY. Cada clase de eventos importante en JavaFX (MouseEvent, KeyEvent, TouchEvent, ScrollEvent, ZoomEvent, RotateEvent, SwipeEvent, DragEvent, etc.) tiene su propia constante ANY.
Esto permite crear manejadores generales que reciban todos los subtipos de ese evento. Por ejemplo, para cualquier evento del teclado que se produzca en una ventana:

```
scene.addEventHandler(KeyEvent.ANY, e -> System.out.println("Tecla presionada o liberada"));
```

Como se ha mencionado antes, todos los eventos heredan de ```javafx.event.Event```. En el siguiente cuadro se muestra la jerarquía de eventos:

```
javafx.event.Event
 ├─ ActionEvent
 ├─ InputEvent
 │   ├─ KeyEvent
 │   ├─ MouseEvent
 │   ├─ TouchEvent
 │   ├─ ScrollEvent
 │   ├─ ZoomEvent
 │   ├─ RotateEvent
 │   └─ SwipeEvent
 ├─ DragEvent
 ├─ ContextMenuEvent
 ├─ InputMethodEvent
 ├─ WindowEvent
 ├─ SortEvent<T>
 ├─ ScrollToEvent<T>
 ├─ CellEditEvent<S,T>
 ├─ MediaEvent (y derivados)
 └─ Cualquier evento personalizado
```
**Nota sobre propiedades observables**

Aunque no son eventos en sentido estricto, en JavaFX también hay eventos reactivos a través de *listeners* sobre *propiedades* estudiadas previamente. Estos no heredan de Event, pero funcionan de forma análoga (respondiendo a cambios de estado). pPor ejemplo:

```
slider.valueProperty().addListener((obs, oldVal, newVal) ->
    System.out.println("Nuevo valor: " + newVal));
```

En la siguiente tabla se muestran los eventos clasificados por el tipo de interacción que los generan:

| Tipo de interacción     | Clase o sistema usado                                                 |
| ----------------------- | --------------------------------------------------------------------- |
| Clics y acciones        | `ActionEvent`, `MouseEvent`                                           |
| Teclado                 | `KeyEvent`                                                            |
| Ratón avanzado / táctil | `TouchEvent`, `ScrollEvent`, `SwipeEvent`, `ZoomEvent`, `RotateEvent` |
| Arrastrar y soltar      | `DragEvent`                                                           |
| Ventanas                | `WindowEvent`                                                         |
| Multimedia              | `MediaEvent`, `MediaErrorEvent`                                       |
| Tablas / listas         | `CellEditEvent`, `ScrollToEvent`, `SortEvent`                         |
| Propiedades reactivas   | `ObservableValue` y `ChangeListener`                                  |

### Flujo de un evento en JavaFX

El sistema de eventos de JavaFX sigue un modelo muy bien estructurado, similar al de DOM en JavaScript, donde un evento viaja a través de tres fases principales dentro de la jerarquía de nodos de la escena.

             ┌──────────────────────────────┐
             │        Stage (ventana)       │
             └──────────────┬───────────────┘
                            │
                            ▼
                   ┌────────────────┐
                   │     Scene      │
                   └──────┬─────────┘
                          │
                          ▼
              ┌────────────────────────┐
              │   Nodo raíz (Root)     │
              └────────┬───────────────┘
                       │
                       │   1️⃣ CAPTURING PHASE
                       ▼
        ┌──────────────────────────────┐
        │ El evento se propaga desde   │
        │ el nodo raíz hacia el nodo   │
        │ objetivo (target).           │
        └──────────────────────────────┘
                       │
                       │   2️⃣ TARGET PHASE
                       ▼
        ┌──────────────────────────────┐
        │ El evento llega al nodo que  │
        │ lo generó (por ejemplo, un   │
        │ Button que fue pulsado).     │
        └──────────────────────────────┘
                       │
                       │   3️⃣ BUBBLING PHASE
                       ▼
        ┌──────────────────────────────┐
        │ El evento "burbujea" hacia   │
        │ arriba: padre → escena →     │
        │ stage, permitiendo que otros │
        │ nodos lo intercepten.        │
        └──────────────────────────────┘

| Fase                    | Nombre     | Dirección                                                       | Qué ocurre                                                                                                                                                  |
| ----------------------- | ---------- | --------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **1. Capturing Phase** | *Captura*  | Desde la raíz (`Stage` → `Scene` → nodos padres → nodo destino) | El evento viaja *descendiendo* por la jerarquía. Los manejadores de captura (`addEventFilter`) pueden interceptarlo **antes** de llegar al objetivo.        |
| **2. Target Phase**    | *Destino*  | En el nodo objetivo                                             | El evento llega al control donde se originó (por ejemplo, un clic en un `Button`). Se ejecutan los manejadores normales (`setOnAction`, `addEventHandler`). |
| **3. Bubbling Phase**  | *Burbujeo* | Desde el nodo destino hacia arriba (padres → escena → stage)    | El evento asciende de nuevo, dando oportunidad a otros nodos de reaccionar (**si no fue consumido**).                                                           |

Es decir, los métodos a usar para la captura/manejo del evento dependen de cada fase:

| Situación                                                               | El método a usar     |
| ----------------------------------------------------------------------- | ------------------- |
| Quieres interceptar el evento antes de que llegue al nodo (prevención)  | `addEventFilter()`  |
| Quieres manejar el evento en el propio nodo que lo genera               | `setOnXXX()`        |
| Quieres reaccionar a eventos que ocurren dentro de hijos (cuando suben) | `addEventHandler()` |


**Ejemplo de propagación**

```
Button btn = new Button("Haz clic");

// Fase de captura
btn.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
    System.out.println("Filtro (captura) detecta clic");
});

// Fase de destino
btn.setOnMouseClicked(e -> {
    System.out.println("Manejador en el botón (target)");
    // e.consume(); // ← Si descomentas, evita que burbujee
});

// Fase de burbujeo (escena)
scene.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
    System.out.println("Manejador en la escena (burbujeo)");
});
```

Una posible salida sería:

```
Filtro (captura) detecta clic
Manejador en el botón (target)
Manejador en la escena (burbujeo)

```

**Importante**

* **consume()**: Detiene la propagación del evento a fases posteriores

Es importante tener claro el orden de ejecución según las fases, y los métodos que capturan el evento:

| Método                                | Fase principal         | Observaciones                                                                     |
| ------------------------------------- | ---------------------- | --------------------------------------------------------------------------------- |
| `addEventFilter(EventType, handler)`  | Captura                | Se ejecuta **antes de llegar al nodo objetivo**, se usa para interceptar.         |
| `setOnXXX(handler)`                   | Destino                | Se ejecuta **solo en el nodo que originó el evento** (target).                    |
| `addEventHandler(EventType, handler)` | Burbujeo **y destino** | Se ejecuta **en el nodo objetivo y en cualquier nodo padre** durante el burbujeo. |

Por lo tanto, si en el nodo destino tenemos programados un método *setOnXXX()* y también *addEventHandler()* entonces primero se ejecutará el método *serOnXXX()*. Es decir,

```
node.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> System.out.println("Filtro"));
node.setOnMouseClicked(e -> System.out.println("setOnXXX"));
node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.out.println("EventHandler"));
```

Genera la salida:

```
Filtro (captura)
setOnXXX (target)
EventHandler (target/bubbling)
```

Si se hubiera agregado el addEventHandler en un nodo padre, entonces se ejecutaría solo durante la fase de burbujeo cuando el evento subiera desde el nodo hijo.

```
Nodo raíz
 └─ Nodo objetivo

Fase Captura → addEventFilter en raíz y objetivo
Fase Target  → setOnXXX en objetivo
              addEventHandler en objetivo
Fase Bubbling → addEventHandler en padres
```

Lo anterior se puede entender con el siguiente ejemplo:

```
VBox (contenedor padre)
 └── Button (hijo)
```

```
VBox root = new VBox();
Button btn = new Button("Púlsame");

root.getChildren().add(btn);

// Manejador en el botón (target phase)
btn.setOnMouseClicked(e -> System.out.println("Botón clicado"));

// Manejador en el VBox (burbujeo)
root.addEventHandler(MouseEvent.MOUSE_CLICKED,
    e -> System.out.println("VBox detecta clic (burbujeo)"));

// Manejador de captura
root.addEventFilter(MouseEvent.MOUSE_CLICKED,
    e -> System.out.println("VBox intercepta (captura)"));
```

El orden de ejecución al hacer clic en el botón sería:

1. Captura → VBox (addEventFilter)
2. Destino → Button (setOnMouseClicked)
3. Burbujeo → VBox (addEventHandler)

```
VBox intercepta (captura)
Botón clicado
VBox detecta clic (burbujeo)
```

### addEventFilter en JavaFX

```addEventFilter``` permite interceptar un evento durante la fase de captura (capturing phase). Se ejecuta antes de que el evento llegue al nodo objetivo (target). Se puede usar para prevenir que el evento continúe su propagación mediante **consume()**.

Es decir, es un **“filtro previo”** que decide si un evento puede llegar al manejador del nodo objetivo (**setOnXXX**) o si debe detenerse.

**Ejemplo**

```
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EjemploEventFilter extends Application {

    @Override
    public void start(Stage stage) {
        Button btn = new Button("Haz clic");
        VBox root = new VBox(10, btn);

        // Filtro: captura antes del target
        btn.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            System.out.println("Filtro: Intercepta el clic antes del target");
            // e.consume(); // Si se descomenta, el setOnMouseClicked no se ejecuta
        });

        // Manejador en target
        btn.setOnMouseClicked(e -> System.out.println("Manejador: clic en el botón"));

        Scene scene = new Scene(root, 250, 150);
        stage.setScene(scene);
        stage.setTitle("Ejemplo addEventFilter");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

Nótese que en el código del manejador ```addEventFilter``` contiene una línea comentada ```e.consume();``` . Si se decomenta solo se verá el mensaje del filtro dado que se detiene la propagación del evento a la fase target y burbujeo (es consumido).

El uso habitual de ```addEventFilter``` es para:

* **Validación previa**: bloquear ciertos eventos antes de que lleguen al nodo. Ejemplo: impedir clic derecho en un botón.
* **Eventos globales**: capturar eventos antes que cualquier nodo los maneje.
* **Registro o logging**: interceptar todos los eventos de un tipo para debug.
* **Transformaciones o adaptaciones**: modificar datos del evento antes de que llegue al target.


## Listas y Tablas

JavaFX proporciona dos componentes fundamentales para mostrar colecciones de datos en la interfaz gráfica:

* ListView\<T\>: Muestra una lista de elementos.
* TableView\<T\>: Muestra datos tabulares (filas y columnas).

Ambos comparten una arquitectura común basada en:
| | |
| -------------------------------------------- | ---------------------------- |
| **modelos observables (ObservableList)**  | Tanto las listas como las tablas usan colecciones observables como modelos donde almacenar la información a mostrar y que notifican automáticamente los cambios al componente visual (p. ej. al agregar o eliminar un elemento). |
| **celdas (cells)**  | Para la visualización de cada dato de la lista/tabla estos componentes delegan en subclases de **_Cell_** que utilizarán distintos componentes de JavaFX (labels, campos de texto, etc.) para mostrar y/o editar los valores de dichas celdas. De ese modo, se pueden adaptar en función del tipo de dato a representar. Por ejemplo, un campo con valor booleano se puede representar con un componente similar a un CheckBox, mientras que uno de texto usando una Label y editar usando un TextField. |

### Modelos de datos

En el caso de las listas, al crear un objeto _ListView\<T\>_ se crea internamente un modelo vacío de tipo _ObservableList\<T\>_ accesible a través de su propiedad _items_:

```java
ListView<String> listaPaises = new ListView<>();
listaPaises.getItems().addAll("Spain","Portugal","Germany");
```
Para el caso de las tablas, el modelo es algo más complejo. Una _TableView\<T\>_ mostrará en sus filas objetos de tipo _T_, y sus columnas generalmente serán atributos de dicho tipo. Para cada atributo a mostrar en la tabla utilizaremos un objeto _TableColumn\<T,S\>_, donde _T_ será el mismo tipo de la tabla y _S_ será el tipo del atributo a mostrar en esa columna. Por ejemplo, para mostrar datos tipo _Usuario_ se podría hacer de la siguiente forma:

```java
public class Usuario {
    private String nombre;
    private String apellidos;
    private String correo;
    private Integer edad;
    private Double saldo;
    .
    .
    .
}
```

```java
// Creamos una tabla para mostrar usuarios
TableView<Usuario> tabla = new TableView<>();

// Creamos una columna por atributo a mostrar. El parámetro será el título de la columna
TableColumn<Usuario,String>  colNombre    = new TableColumn<>("Nombre");        
TableColumn<Usuario,String>  colApellidos = new TableColumn<>("Apellidos");        
TableColumn<Usuario,String>  colCorreo    = new TableColumn<>("e-mail");        
TableColumn<Usuario,Integer> colEdad      = new TableColumn<>("Edad");
TableColumn<Usuario,Double>  colSaldo     = new TableColumn<>("Saldo");

// Para cada columna es necesario indicar una CellValueFactory desde la que obtendrá el valor de cada celda
colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));

// Configuramos las columnas de la tabla
tabla.getColumns().addAll(colNombre, colApellidos, colCorreo, colEdad, colSaldo);

// Añadimos los items a la tabla (objetos de tipo Usuario)
tabla.getItems().addAll(
        new Usuario("Juan","Sánchez Sánchez","j@c.com",25,100.0),
        new Usuario("Alicia","Martínez García","a@m.com",28,200.50),
        new Usuario("Ramón","Rodriguez Ramirez","r@r.com",38,-50.0)
);
```


### Celdas

#### Factorías de celdas

Las clase utilizadas para presentar y editar los valores de los modelos han de ser subclases de _ListCell_ para las listas o de _TableCell_ para las tablas. Para cada valor a presentar, las listas/tablas crearán un objeto de unos de estos tipos y eso hará la función de celda en la interfaz. Para ello utilizan lo que se denomina factoría de celdas (_CellFactory_), un objeto que, dado un valor del modelo, se encarga de crear el elemento gráfico que lo representará (una Label, un CheckBox, etc.).

Se puede cambiar el tipo de celda utilizada asignando una factoria de celdas distinta a la por defecto, e incluso crear una propia para variar el estilo de las celdas. Por ejemplo, podríamos modificar las celdas de la columna que muestra los saldos de los usuarios para que cambiara el color del texto en función de si el saldo es positivo o negativo de la siguiente manera:

```java
// Cambiamos la factoría de la celda de saldos
colSaldo.setCellFactory(columna -> new TableCell<Usuario, Double>() {
    @Override
    protected void updateItem(Double saldo, boolean empty) {
        super.updateItem(saldo, empty);

        if (empty || saldo == null) {
            setText(null);
            setStyle("");
        } else {
            setText(String.valueOf(saldo));                    
            if (saldo < 0) {
                setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            } else {
                setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            }
        }
    }
});
```
O también podemos usar una de las factorías de celdas adicionales ya implementadas. Por ejemplo, _ProgressBarTableCell_ sirve para mostrar valores entre 0 y 1 utilizando una barra de progreso. Podríamos añadir una visualización alternativa de la edad de la siguiente manera:

```java
// Creamos una nueva columna para mostrar la edad como barra de progreso
TableColumn<Usuario, Double> colEdadBarra = new TableColumn<>("Edad (progress bar)");
// Los valores de la barra vendrán desde el campo edad, pero hay que escalarlo al rango 0-1 (asumimos 100 como edad máxima)
colEdadBarra.setCellValueFactory(valorCelda -> {
    Usuario u = valorCelda.getValue();
    DoubleProperty valorProperty = new SimpleDoubleProperty();
    valorProperty.set(((double)u.edad)/100.0);
    return valorProperty.asObject();
});
// Asignamos como CellFactory al ProgressBarTableCell
colEdadBarra.setCellFactory(ProgressBarTableCell.forTableColumn());

// Añadimos la nueva columna a la tabla
tabla.getColumns().add(colEdadBarra);
```

#### Celdas editables

Por defecto, las celdas de las listas y las tablas no son editables, es decir, no podemos cambiar su valor. Para hacerlo, tenemos que hacer un mínimo de dos cosas:

1. Indicar a la vista que que tiene que permitir la edición, asignando _true_ a la propiedad _editable_.
2. Usar una CellFactory que permita la edición de las celdas.

En nuestro ejemplo, si queremos poder editar la edad de los usuarios, los anteriores pasos se harían con el siguiente código:

```java
// Hacemos editable la tabla
tabla.setEditable(true);

// Asignamos a la columna una CellFactory editable
 // Como la columa es de tipo Integer y la factoría es para Strings, hay que añadir un conversor
colEdad.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
```

Si, como ocurre en nuestro caso, los datos de la tabla se obtienen de otra estructura y se convierten para su representación (en nuestro caso, los valores de Usuario los transformamos en propiedades con _PropertyValueFactory_), será necesario agregar un listener para capturar el evento de modificación de los datos y actualizar el modelo de origen. 

```java
// Asignamos a las columnas que queramos una CellFactory editable
colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
colApellidos.setCellFactory(TextFieldTableCell.forTableColumn());
        
// Programamos los evento que actualizarán el modelo
colEdad.setOnEditCommit(event -> {
    Usuario usuario = event.getRowValue();
    usuario.setEdad(event.getNewValue());
    // Fuerzo el refresco de la tabla para que se actualice la columna con el progress bar
    tabla.refresh();
});
colNombre.setOnEditCommit(event -> {event.getRowValue().setNombre(event.getNewValue());});
colApellidos.setOnEditCommit(event -> {event.getRowValue().setApellidos(event.getNewValue());});
```

### Ejemplo de tablas y listas completo

Aquí se muestra un ejemplo completo de todo lo visto anteriormente.

```java
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class EjemploListaTabla extends Application {

	/*
	 Clase interna con la descripción de un usuario. Se incluye aquí por conveniencia, 
	 aunque debería ir en un fichero aparte
	*/
	public class Usuario {
	    private String nombre;
	    private String apellidos;
	    private String correo;
	    private Integer edad;
	    private Double saldo;
	    
	    public Usuario(String nombre, String apellidos, String correo, Integer edad, Double saldo) {
	        this.nombre = nombre;
	        this.apellidos = apellidos;
	        this.correo = correo;
	        this.edad = edad;
	        this.saldo = saldo;
	    }

	    public String  getNombre() { return nombre; }
	    public void    setNombre(String nombre) { this.nombre = nombre; }
	    public String  getApellidos() { return apellidos; }
	    public void    setApellidos(String apellidos) { this.apellidos = apellidos; }
	    public String  getCorreo() { return correo; }
	    public void    setCorreo(String correo) { this.correo = correo; }
	    public Integer getEdad() { return edad; }
	    public void    setEdad(Integer edad) { this.edad = edad; }
	    public Double  getSaldo() { return saldo; }
	    public void    setSaldo(Double saldo) { this.saldo = saldo; }   	    
	}
	
	/** -- Atributos de la vista. -- **/
	/* Tabla de usuarios */
	TableView<Usuario> tabla; 
	
	/* Lista con la concatenación de nombre y apellidos */
	ListView<String> lista;
	
	/* Datos de los usuarios que nos servirán de ejemplo. */
	List<Usuario> datosUsuarios =  Arrays.asList(				
			new Usuario("Juan","Sánchez Sánchez","j@c.com",25,100.0),
    		new Usuario("Alicia","Martínez García","a@m.com",28,200.50),
    		new Usuario("Ramón","Rodriguez Ramirez","r@r.com",38,-50.0)
    );
	
	@Override
    public void start(Stage stage) {
		
        // --- ListView ---
        Label labelLista = new Label("Usuarios registrados:");
        lista = new ListView<>();
        // Actualizamos el modelo de la lista desde un método para poder refrescarlo manualmente 
        actualizarModeloLista();
        lista.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lista.setPrefHeight(300);
        lista.itemsProperty();
        

        // Creamos una tabla para mostrar usuarios
        tabla = new TableView<Usuario>();

        // Creamos una columna por atributo a mostrar. El parámetro será el título de la columna
        TableColumn<Usuario,String> colNombre    = new TableColumn<>("Nombre");        
        TableColumn<Usuario,String> colApellidos = new TableColumn<>("Apellidos");        
        TableColumn<Usuario,String> colCorreo    = new TableColumn<>("e-mail");        
        TableColumn<Usuario,Integer> colEdad     = new TableColumn<>("Edad");
        TableColumn<Usuario,Double> colSaldo     = new TableColumn<>("Saldo");

        
        // Para cada columna es necesario indicar una CellValueFactory desde la que obtendrá el valor de cada celda
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));

        // Añadimos las columnas a la tabla 
        tabla.getColumns().addAll(colNombre, colApellidos, colCorreo, colEdad, colSaldo);
       
        // Añadimos los items a la tabla (objetos de tipo Usuario)
        tabla.setItems( FXCollections.observableArrayList(datosUsuarios));
        
        // Cambiamos la factoría de la celda de saldos
        colSaldo.setCellFactory(columna -> new TableCell<Usuario, Double>() {
        	@Override
            protected void updateItem(Double saldo, boolean empty) {
                super.updateItem(saldo, empty);

                if (empty || saldo == null) {
                    setText(null);
                    setStyle(""); // limpiar estilo
                } else {
                    setText(String.valueOf(saldo));                    
                    if (saldo < 0) {
                    	setStyle("-fx-text-fill: red; -fx-font-weight: bold;");                    	
                    } else {
                    	setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    }
                }
            }
        });
        
        // Creamos una nueva columna para mostrar la edad como barra de progreso
        TableColumn<Usuario, Double> colEdadBarra = new TableColumn<>("Edad (progress bar)");
        // Los valores de la barra vendrán desde el campo edad, pero hay que escalarlo al rango 0-1 (asumimos 100 como edad máxima)
        colEdadBarra.setCellValueFactory(valorCelda -> {
            Usuario u = valorCelda.getValue();
            DoubleProperty valorProperty = new SimpleDoubleProperty();
            valorProperty.set(((double)u.edad)/100.0);
            return valorProperty.asObject();
        });
        // Asignamos como CellFactory al ProgressBarTableCell
        colEdadBarra.setCellFactory(ProgressBarTableCell.forTableColumn());

        // Añadimos la nueva columna a la tabla
        tabla.getColumns().add(colEdadBarra);

       // Hacemos editable la tabla
        tabla.setEditable(true);

        // Asignamos a las columnas que queramos una CellFactory editable
        colEdad.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter())); // Como la columa es de tipo Integer y la factoría es para Strings, hay que añadir un conversor
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colApellidos.setCellFactory(TextFieldTableCell.forTableColumn());
        
        // Programamos los evento que actualizarán el modelo
        colEdad.setOnEditCommit(event -> {
        	Usuario usuario = event.getRowValue();
            usuario.setEdad(event.getNewValue());
            // Fuerzo el refresco de la tabla para que se actualice la columna con el progress bar
            tabla.refresh();
        });
        colNombre.setOnEditCommit(event -> {
        	event.getRowValue().setNombre(event.getNewValue());
        	// El modelo de la lista depende de este dato, por lo que la actualizamos
        	actualizarModeloLista();
        });
        colApellidos.setOnEditCommit(event -> {
        	event.getRowValue().setApellidos(event.getNewValue());
        	// El modelo de la lista depende de este dato, por lo que la actualizamos
        	actualizarModeloLista();
        });
        
        // --- Layout principal ---
        VBox root = new VBox(10,
                labelLista, lista, tabla
        );
        root.setPadding(new Insets(15));
        root.setStyle("-fx-font-family: 'Segoe UI';");

        // --- Escena ---
        Scene scene = new Scene(root, 550, 400);
        stage.setTitle("Ejemplo de listas y tablas en JavaFX");
        stage.setScene(scene);
        stage.show();
    }

	
	private void actualizarModeloLista() {
        lista.setItems(FXCollections.observableArrayList((datosUsuarios.stream().map(usuario -> {return (usuario.getApellidos()+", "+usuario.getNombre());}).toList())));

	}
	
    public static void main(String[] args) {
        launch(args);
    }
	
}

```


## Personalización

### Usando imagenes en controles

En JavaFX, las imágenes se representan con la clase *Image*, y para mostrarlas en pantalla se usa *ImageView*.

```
Image imagen = new Image("file:src/main/resources/imagenes/icono.png");
ImageView vista = new ImageView(imagen);
```

Para colocar una imagen dentro de un control, como por ejemplo un botón, hay que tener en cuenta que casi todos los controles de JavaFX pueden contener nodos gráficos, a través de la propiedad *setGraphic(Node)*.

```
Button boton = new Button("Guardar");
Image imagen = new Image(getClass().getResource("guardar.png").toExternalForm());
ImageView vista = new ImageView(imagen);

// Ajustamos tamaño si es necesario
vista.setFitWidth(16);
vista.setFitHeight(16);
vista.setPreserveRatio(true);

// Asignamos la imagen al botón
boton.setGraphic(vista);
```

Por defecto, la imagen aparece a la izquierda del texto. Se puede cambiar la posición con *setContentDisplay()*:

```
boton.setContentDisplay(ContentDisplay.TOP);    // Imagen arriba, texto abajo
boton.setContentDisplay(ContentDisplay.RIGHT);  // Imagen a la derecha
boton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // Solo imagen, sin texto
```

Se pueden cargar imágenes de diferentes formas.

| Situación                     | Ejemplo                                                          |
| ----------------------------- | ---------------------------------------------------------------- |
| Desde **classpath (recurso)** | `new Image(getClass().getResource("icon.png").toExternalForm())` |
| Desde **archivo local**       | `new Image("file:/Users/javier/icon.png")`                       |
| Desde **URL remota (web)**    | `new Image("https://example.com/icon.png")`                      |

No obstante se **recomienda** guardar las imágenes en la carpeta resources del proyecto(por ejemplo, *src/main/resources/imagenes/* en un proyecto Maven)

También se pueden usar imágenes en otros controles en JavaFX, tales como *Label*, *MenuItem*, ...

| Control                   | Método         | Ejemplo                                      |
| ------------------------- | -------------- | -------------------------------------------- |
| `Label`                   | `setGraphic()` | `label.setGraphic(new ImageView(icono));`    |
| `MenuItem`                | `setGraphic()` | `menuItem.setGraphic(new ImageView(icono));` |
| `CheckBox`, `RadioButton` | `setGraphic()` | `radio.setGraphic(new ImageView(icono));`    |


### Usando temas CSS 
En JavaFX se pueden aplicar “temas” o “skins” mediante CSS (opcionalmente, modificaciones en el Scene Graph o estilos personalizados). La aplicación de los estilo cambia solo la apariencia visual (color, bordes, tipografía, etc.).

```
scene.getStylesheets().add("estilo.css")
```
Por ejemplo, dado un tema ``` dark-theme.css ```

``` css
.root {
    -fx-base: #2b2b2b;
    -fx-background-color: #2b2b2b;
    -fx-text-fill: white;
}

.button {
    -fx-background-color: #444;
    -fx-text-fill: #eee;
}

.text-field {
    -fx-background-color: #3a3a3a;
    -fx-text-fill: #fff;
    -fx-prompt-text-fill: #999;
}
```
[JavaFX 21 Css reference](https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/doc-files/cssref.html#button)

Y en el código haríamos:

```
Scene scene = new Scene(root);
scene.getStylesheets().add(getClass().getResource("dark-theme.css").toExternalForm());
stage.setScene(scene);
```

Cuando se hace ```getClass().getResource("dark-theme.css") ``` primero, se busca el archivo ```dark-theme.css``` dentro del *classpath* (por ejemplo, dentro del mismo paquete o en resources del proyecto). Esto devuelve entonces un objeto de tipo URL que apunta al recurso encontrado.

```
URL url = getClass().getResource("dark-theme.css");
```
...devolvería algo como:

```
file:/Users/javier/miapp/bin/com/ejemplo/dark-theme.css
```
si se trata de un fichero del proyecto o bien:

```
jar:file:/Users/javier/miapp/miapp.jar!/com/ejemplo/dark-theme.css
```
si el tema está dentro de un JAR.

Entonces ``` toExternalForm()``` convierte la URL en una cadena (String) con una URL absoluta válida para la web (formato externo). JavaFX requiere una cadena URL válida (no una URL relativa ni una ruta relativa) para cargar hojas de estilo.

A continuación un ejemplo completo que usa iconos gratuitos de [FlatIcon](https://www.flaticon.es/):

```
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaConImagenesOnline extends Application {

    @Override
    public void start(Stage stage) {
        // === URLs de iconos (Flaticon o similares) ===
        String urlAbrir   = "https://cdn-icons-png.flaticon.com/512/1828/1828490.png"; // icono de abrir carpeta
        String urlSalir   = "https://cdn-icons-png.flaticon.com/512/1828/1828479.png"; // icono de salir
        String urlInfo    = "https://cdn-icons-png.flaticon.com/512/1828/1828817.png"; // icono de información
        String urlGuardar = "https://cdn-icons-png.flaticon.com/512/1828/1828911.png"; // icono de guardar
        String urlEliminar= "https://cdn-icons-png.flaticon.com/512/1214/1214428.png"; // icono de eliminar
        String urlConfig  = "https://cdn-icons-png.flaticon.com/512/3524/3524636.png"; // icono de configuración

        // === Menú superior ===
        Menu menuArchivo = new Menu("Archivo");

        MenuItem itemAbrir = new MenuItem("Abrir", new ImageView(new Image(urlAbrir, 16, 16, true, true)));
        MenuItem itemSalir = new MenuItem("Salir", new ImageView(new Image(urlSalir, 16, 16, true, true)));
        itemSalir.setOnAction(e -> stage.close());

        menuArchivo.getItems().addAll(itemAbrir, itemSalir);
        MenuBar menuBar = new MenuBar(menuArchivo);

        // === Etiqueta central con imagen ===
        Label label = new Label("Bienvenido a JavaFX");
        ImageView imgInfo = new ImageView(new Image(urlInfo, 48, 48, true, true));
        label.setGraphic(imgInfo);
        label.setContentDisplay(ContentDisplay.TOP);

        // === Botones inferiores ===
        Button btnGuardar = new Button("Guardar", new ImageView(new Image(urlGuardar, 20, 20, true, true)));
        Button btnEliminar = new Button("Eliminar", new ImageView(new Image(urlEliminar, 20, 20, true, true)));

        HBox contBotones = new HBox(10, btnGuardar, btnEliminar);
        contBotones.setStyle("-fx-padding: 10; -fx-alignment: center;");

        // === CheckBox lateral con imagen ===
        CheckBox chkOpcion = new CheckBox("Activar modo avanzado");
        chkOpcion.setGraphic(new ImageView(new Image(urlConfig, 18, 18, true, true)));

        VBox contLateral = new VBox(10, chkOpcion);
        contLateral.setStyle("-fx-padding: 10;");

        // === Estructura principal ===
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(label);
        root.setBottom(contBotones);
        root.setRight(contLateral);

        // === Escena ===
        Scene scene = new Scene(root, 420, 260);
        stage.setTitle("Ejemplo con iconos online (Flaticon)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

### Usando librerías de temas
Hay proyectos y frameworks que te facilitan la aplicación de temas en las aplicaciones JavaFX:

* **Modena** → es el tema por defecto de JavaFX 8+ (reemplaza a Caspian de JavaFX 2).
* **JMetro** → ofrece temas Light y Dark con estilo Fluent / Material Design.
[https://github.com/JFXtras/jfxtras-styles](https://github.com/JFXtras/jfxtras-styles)
* **Atlantafx** → colección moderna de temas (Bootstrap, Nord, Cupertino, etc.)
[https://github.com/mkpaz/atlantafx](https://github.com/mkpaz/atlantafx)

Ejemplo, con **JMetro**:

``` java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class EjemploTema extends Application {

    @Override
    public void start(Stage stage) {
        // Crear interfaz sencilla
        Button boton = new Button("Hola JavaFX con JMetro");
        StackPane root = new StackPane(boton);
        Scene scene = new Scene(root, 300, 200);

        // Aplicar tema oscuro (también puedes usar Style.LIGHT)
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);

        // Configurar y mostrar el Stage
        stage.setTitle("Ejemplo de tema JMetro");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

