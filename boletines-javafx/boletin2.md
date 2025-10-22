# Prácticas de JavaFX - Sesión 2

En esta práctica se continuará profundizando en la enseñanza de JavaFX. Se verá el diseño de ventanas con **FXML** en más detalle, aplicando el uso de la herraienta **SceneBuilder**. 
Se estudiarán diferentes **layouts** disponibles mediante los contenedores de JavaFX. De igual modo se ampliará el estudio de **otros componentes** prácticos para la construcción de ventanas comunes en las aplicaciones de escritorio.
Se introducirá la noción de la **arquitectura** de una GUI en JavaFX (Controller, bindings, etc.). Y por último se presentarán las **Property bindings**, las cuales permiten controlar los cambios que se preoducen en los valores de las propiedades de controles en JavaFX.

*NOTA*: se ha usado el tutorial [jenkov.com javafx](https://jenkov.com/tutorials/javafx/) en la generación de los siguientes contenidos.

## El fichero FXML

FXML de JavaFX es un formato XML que permite crear interfaces gráficas de usuario (GUI) de JavaFX de forma similar a como se crean interfaces gráficas web en HTML. FXML permite separar el código de diseño de JavaFX del resto del código de la aplicación. 

Puede utilizarse para crear el diseño de una interfaz gráfica de usuario completa o solo de una parte de ella (por ejemplo, de un formulario, una pestaña, un cuadro de diálogo, etc.). A continuación un ejemplo:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>

<VBox>
    <children>
        <Label text="Hello world FXML"/>
    </children>
</VBox>
```

Este ejemplo define un *VBox* que contiene una sola etiqueta como elemento secundario. El componente *VBox* es un componente de diseño JavaFX. La etiqueta *Label* simplemente muestra texto en la interfaz gráfica.

La primera línea del documento FXML es la primera línea estándar de los documentos XML. 

```xml
<?xml version="1.0" encoding="UTF-8"?>
```

Las dos líneas siguientes son sentencias de **importación**. En FXML, se deben importar las clases a utilizar.

```xml
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>
```

Después de las sentencias de importación, se muestra la composición real de la GUI. Se declara un componente *VBox* y, dentro de su propiedad *children*, se declara un único componente *Label*. Como resultado, la instancia *Label* se añadirá a la propiedad *children* de la instancia *VBox*.

```xml
<VBox>
    <children>
        <Label text="Hello world FXML"/>
    </children>
</VBox>
```

### Propiedades
Los controles en JavaFX tienen propiedades. Se pueden establecer los valores de las propiedades de dos maneras:

* La primera es usar un **atributo** XML para establecer el valor de la propiedad.
* La segunda es usar un **elemento** XML **anidado** para establecer el valor de la propiedad.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>

<VBox spacing="20">
    <children>
        <Label text="Line 1"/>
        <Label text="Line 2"/>
    </children>       
</VBox>
```

El anterior ejemplo muestra las dos formas de definir propiedades en los controles. 

**Uso de atributos**: El atributo de espaciado del elemento *VBox*. El valor establecido en el atributo de espaciado se pasa como parámetro al método *setSpacing()* del objeto *VBox* creado a partir de dicho elemento.

**Uso de elementos anidados**: El elemento *children* anidado dentro del elemento *VBox*. Este elemento corresponde al método *getChildren()* de la clase *VBox*. Los elementos anidados dentro del elemento *children* se convertirán en componentes JavaFX que se añadirán a la colección obtenida mediante el método *getChildren()* del objeto *VBox* representado por el elemento VBox padre.

También se puede ver cómo se establecen los atributos de texto de los dos elementos *Label* anidados dentro de *children*. Los valores de los atributos de texto se pasarán como parámetros a la propiedad *setText()* de los objetos *Label*.

En FXML se tratan las "propiedades" como variables miembro a las que se accede mediante métodos accesores (getter y setter). Por ejemplo, la propiedad "text" se accede con *getText()* y *setText()*. Como se ve en el ejemplo anterior, los nombres de las propiedades de las clases JavaFX se corresponden con los nombres de los atributos (por ejemplo, *spacing*) y elementos anidados (por ejemplo, *children*).

### Propiedad por defecto

Un componente JavaFX puede tener una propiedad predeterminada. Esto significa que si un elemento FXML *contiene elementos secundarios que no están anidados dentro de un elemento de propiedad*, se asume que *pertenecen a la propiedad predeterminada*.

Veamos un ejemplo. La clase *VBox* tiene la propiedad **children** como **predeterminada**. Esto implica que podemos omitir el elemento *children*. El ejemplo anterior podría ser escrito así:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>

<VBox spacing="20">
    <Label text="Line 1"/>
    <Label text="Line 2"/>
</VBox>
```

### Espacios de Nombres en FXML

FXML cuenta con un espacio de nombres que se configura en el elemento raíz de los archivos FXML. El espacio de nombres es necesario para algunos atributos FXML, como el atributo fx:id. y se declara mediante la declaración de atributo *xmlns:fx="http://javafx.com/fxml"*.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns:fx="http://javafx.com/fxml">
</VBox>
```

### Identificadores en elementos FXML

Se pueden asignar ID en elementos FXML. Estos ID pueden usarse para hacer referencia a los elementos FXML en otras partes del archivo FXML. 

La especificación de un ID se realiza mediante el atributo *id* del espacio de nombres FXML. En el siguiente ejemplo se declara el atributo *fx:id="label1"* en el elemento *Label*. Esto permite que se puede hacer referencia a este elemento *Label* mediante el ID *"label1"* en cualquier parte del documento FXML.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Label?>

<VBox xmlns:fx="http://javafx.com/fxml">
    <Label fx:id="label1" text="Line 1"/>
</VBox>
```

### Propiedades visuales / de estilo

Es posible aplicar estilos a los componentes JavaFX mediante declaraciones en un archivo FXML. Esto se puede hacer anidando un elemento de estilo *style* dentro del elemento FXML.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Button?>

<VBox xmlns:fx="http://javafx.com/fxml">
    <Button text="Click me!"/ onAction="reactToClick()">
        <style>
            -fx-padding: 10;
            -fx-border-width: 3;
        </style>
    </Button>
</VBox>
```

En el ejemplo se establece la propiedad CSS *-fx-padding* en *10* y la propiedad *-fx-border-width* en *3*. Dado que el elemento *style* (que representa a la propiedad *style* del elemento, similar a la propiedad *children* vista anteriormente) está anidado dentro del elemento *Button*, estos estilos se aplicarán a dicho elemento botón.

Se puede **aplicar el CSS directamente en el FXML**

Si tu archivo FXML tiene un nodo raíz (por ejemplo AnchorPane, VBox, etc.), puedes añadir el atributo *stylesheets* directamente al root:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.control.Label?>

<AnchorPane xmlns="http://javafx.com/javafx/21"
            xmlns:fx="http://javafx.com/fxml/1"
            fx:controller="com.ejemplo.MiControlador"
            stylesheets="@estilos.css">

    <children>
        <Label text="Hola Mundo!" layoutX="50" layoutY="50" />
    </children>
</AnchorPane>
```

El valor de stylesheets usa ```@estilos.css```, lo cual indica que el archivo CSS se encuentra en el **mismo directorio que el FXML**. Si está en otro paquete, usas una ruta relativa al classpath, por ejemplo: ```stylesheets="@/com/ejemplo/recursos/estilos.css" ```

También veremos más adelante que se hacer desde el controlador o desde donde se cargue el FXML.

## Contenedores y Layouts

Un layout (o contenedor) es un nodo especial que organiza automáticamente los elementos hijos (botones, etiquetas, etc.) dentro de la interfaz. Todos heredan de la clase base ```javafx.scene.layout.Pane```.

Entre los contenedores más comunes en JavFX encontramos:

| Layout         | Organización                              | Descripción breve                                           | Uso típico                                         |
| -------------- | ----------------------------------------- | ----------------------------------------------------------- | -------------------------------------------------- |
| **Pane**       | Libre                                     | Posicionamiento absoluto (sin reglas de disposición)        | Escenas simples, posiciones exactas                |
| **HBox**       | Horizontal                                | Coloca los nodos en una fila, de izquierda a derecha        | Barras de herramientas, menús horizontales         |
| **VBox**       | Vertical                                  | Coloca los nodos en una columna, de arriba a abajo          | Formularios, paneles apilados                      |
| **BorderPane** | 5 zonas: Top, Bottom, Left, Right, Center | Disposición clásica tipo marco                              | Aplicaciones con cabecera, contenido central y pie |
| **GridPane**   | En cuadrícula (filas y columnas)          | Coloca nodos en celdas específicas                          | Formularios complejos, paneles tabulares           |
| **StackPane**  | Superposición                             | Apila los nodos uno encima de otro                          | Fondos, overlays, animaciones                      |
| **FlowPane**   | Flujo (horizontal o vertical)             | Coloca nodos en fila y salta a la siguiente cuando se llena | Galerías, etiquetas dinámicas                      |
| **TilePane**   | Rejilla uniforme                          | Similar a `FlowPane` pero con celdas de tamaño igual        | Tableros, miniaturas, iconos                       |
| **AnchorPane** | Anclaje                                   | Permite fijar nodos a bordes específicos                    | Diseños redimensionables, pantallas adaptativas    |


### Ejemplos de Contenedores/Layouts

* **HBox**. Distribuye los botones horizontalmente con separación uniforme.

``` 
HBox hbox = new HBox(10); // 10 px de espacio
hbox.getChildren().addAll(new Button("A"), new Button("B"), new Button("C"));
```
* **VBox**. Muy usado en formularios o paneles apilados. 

``` 
VBox vbox = new VBox(5);
vbox.getChildren().addAll(new Label("Nombre:"), new TextField(), new Button("Aceptar"));
```

* **BorderPane**. Ideal para interfaces estructuradas (como una ventana con menú arriba y contenido central).

```
BorderPane root = new BorderPane();
root.setTop(new Label("Cabecera"));
root.setCenter(new TextArea());
root.setBottom(new Button("Salir"));
```

* **GridPane**. Permite alineaciones precisas tipo formulario.

```
GridPane grid = new GridPane();
grid.setHgap(10);
grid.setVgap(10);

grid.add(new Label("Usuario:"), 0, 0);
grid.add(new TextField(), 1, 0);
grid.add(new Label("Contraseña:"), 0, 1);
grid.add(new PasswordField(), 1, 1);
```

* **StackPane**. Útil para superponer elementos o crear fondos dinámicos.

```
StackPane stack = new StackPane();
stack.getChildren().addAll(new Rectangle(200, 100, Color.LIGHTGRAY), new Label("Encima"));
```

* **FlowPane**. Los botones fluyen automáticamente y saltan de línea si no caben.

```
FlowPane flow = new FlowPane();
flow.getChildren().addAll(new Button("Uno"), new Button("Dos"), new Button("Tres"));
```

* **TilePane**. Similar a FlowPane pero con celdas cuadradas uniformes.

```
TilePane tile = new TilePane();
tile.getChildren().addAll(
    new Button("1"), new Button("2"), new Button("3"), new Button("4")
);
```

* **AnchorPane**. Permite fijar componentes a márgenes relativos, muy usado para layouts redimensionables.

```
AnchorPane anchor = new AnchorPane();
Button boton = new Button("Guardar");
AnchorPane.setTopAnchor(boton, 10.0);
AnchorPane.setRightAnchor(boton, 20.0);
anchor.getChildren().add(boton);
```
Es común anidar varios layouts. Por ejemplo:

```
BorderPane root = new BorderPane();
root.setTop(new HBox(new Label("Título")));
root.setCenter(new GridPane());
root.setBottom(new HBox(new Button("Aceptar"), new Button("Cancelar")));
```

Otros contenedores útiles pueden ser:

| Contenedor     | Descripción                                                    |
| -------------- | -------------------------------------------------------------- |
| **ScrollPane** | Permite añadir barras de desplazamiento a cualquier contenido. |
| **TabPane**    | Interfaz con pestañas (como un navegador).                     |
| **SplitPane**  | Divide el espacio en áreas redimensionables.                   |


## Controles en JavaFX

### Label

Es un control de solo lectura que muestra texto o información al usuario.

* No admite edición directa.
* Puede mostrar texto estático o estar enlazado (*binding*) a una propiedad observable.
* Soporta estilos con CSS y puede incluir saltos de línea *(setWrapText(true))*

``` Label label = new Label("Nombre de usuario:"); ```

### TextField

Es un campo de texto de una sola línea, usado para introducir texto breve (por ejemplo, nombre o correo).

* Se accede a su contenido con *getText()* o se puede enlazar con una propiedad.
* Permite texto de sugerencia mediante *setPromptText("Ejemplo...")*.

``` 
TextField nombreField = new TextField();
nombreField.setPromptText("Introduce tu nombre"); 
```

### TextArea

Es un campo de texto multilínea, ideal para descripciones o comentarios largos.

* Permite ajustar texto automáticamente *(setWrapText(true))*.
* Su texto también puede enlazarse a propiedades observables.

``` 
TextArea comentariosArea = new TextArea();
comentariosArea.setPromptText("Escribe tus comentarios aquí...");
comentariosArea.setWrapText(true);
```

### Button

Es el control más básico para ejecutar una acción.

* Se usa para iniciar eventos cuando el usuario hace clic (o presiona Enter cuando tiene el foco).
* Se asocia a un manejador de eventos con *setOnAction()* o, en FXML, con *onAction="#metodo"*
* También puede tener íconos, estilos CSS, o desactivarse (*setDisable(true)*).

``` 
Button btnGuardar = new Button("Guardar");
btnGuardar.setOnAction(e -> System.out.println("Guardando datos..."));
```

### Menu, MenuItem y MenuBar

Estos controles trabajan juntos para crear barras de menú como las de las aplicaciones de escritorio.

* **MenuBar** : Es la barra horizontal que agrupa varios menús desplegables (Archivo, Edición, Ayuda, etc.).
* **Menu** : Representa un menú desplegable dentro de la barra. Contiene elementos del tipo *MenuItem* (y otros submenús si se desea).
* **MenuItem** : Es un elemento seleccionable dentro de un menú. Lanza una acción cuando el usuario lo selecciona, igual que un botón (*setOnAction*).

```
Menu menuArchivo = new Menu("Archivo");
MenuItem nuevo = new MenuItem("Nuevo");
MenuItem salir = new MenuItem("Salir");
menuArchivo.getItems().addAll(nuevo, salir);

MenuBar barraMenu = new MenuBar(menuArchivo);
```

### Ejemplo 1

``` java
package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControlesAccionApp extends Application {

    @Override
    public void start(Stage stage) {
        // --- Barra de menú ---
        Menu menuArchivo = new Menu("Archivo");
        MenuItem nuevo = new MenuItem("Nuevo");
        MenuItem salir = new MenuItem("Salir");

        Menu menuAyuda = new Menu("Ayuda");
        MenuItem acercaDe = new MenuItem("Acerca de");

        menuArchivo.getItems().addAll(nuevo, salir);
        menuAyuda.getItems().add(acercaDe);

        MenuBar barraMenu = new MenuBar(menuArchivo, menuAyuda);

        // --- Zona central ---
        TextArea salida = new TextArea();
        salida.setPromptText("Aquí aparecerán los mensajes...");
        salida.setWrapText(true);

        Button botonAccion = new Button("Haz clic aquí");
        botonAccion.setOnAction(e -> salida.appendText("Botón pulsado.\n"));

        // --- Asignar acciones a los menús ---
        nuevo.setOnAction(e -> salida.appendText("Nuevo archivo creado.\n"));
        salir.setOnAction(e -> stage.close());
        acercaDe.setOnAction(e ->
                mostrarAlerta("Aplicación de ejemplo", "Versión 1.0\nCreada con JavaFX.")
        );

        // --- Layout principal ---
        BorderPane root = new BorderPane();
        root.setTop(barraMenu);
        root.setCenter(salida);
        root.setBottom(botonAccion);
        BorderPane.setAlignment(botonAccion, javafx.geometry.Pos.CENTER);
        root.setStyle("-fx-padding: 10;");

        // --- Escena ---
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Ejemplo de controles de acción");
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

### ScrollPane (Barras de desplazamiento)
ScrollPane es un contenedor especial que añade barras de desplazamiento horizontales y/o verticales a su contenido cuando este excede el tamaño visible. Ideal para mostrar textos largos, imágenes grandes, tablas extensas, o formularios de gran tamaño.

Se usa envolviendo el nodo que deseas hacer desplazable. Las barras de desplazamiento aparecen automáticamente según el tamaño del contenido.

``` 
ScrollPane scrollPane = new ScrollPane(contenido);
```

Algunos ajustes comunes:

```
scrollPane.setFitToWidth(true);   // Hace que el contenido ocupe todo el ancho
scrollPane.setFitToHeight(false); // Mantiene el alto libre
scrollPane.setPannable(true);     // Permite arrastrar el contenido con el ratón
```

JavaFX no tiene un control específico para bordes, sino que los **bordes** se aplican como decoración sobre cualquier nodo o contenedor mediante:

* Estilos CSS (forma más habitual)

```
Label etiqueta = new Label("Etiqueta con borde");
etiqueta.setStyle("-fx-border-color: #336699; -fx-border-width: 2; -fx-padding: 5;");
```
O bien: 

```
<Label text="Etiqueta con borde" style="-fx-border-color: red; -fx-border-width: 2;" />
```


* Contenedores como Pane, HBox, VBox, StackPane, etc., que permiten crear efectos visuales que simulan marcos o separaciones.

Es decir, para crear un “efecto de borde” hay que envolver el nodo dentro de otro contenedor, como un *StackPane* o *VBox*, y aplicar al contenedor un color o borde diferente.

```
StackPane marco = new StackPane(new Label("Contenido"));
marco.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10;");

```

### ListView
Muestra una lista vertical de elementos.

* Permite seleccionar uno o varios ítems (según el modo configurado).
* Puede contener objetos de cualquier tipo (*ListView*), no solo texto.
* La selección se gestiona a través del modelo *SelectionModel*.

```
listView.getItems().addAll("Opción 1", "Opción 2", "Opción 3");
listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
listView.getSelectionModel().getSelectedItems(); // devuelve lista observable
```

### CheckBox
Representa una opción independiente que puede estar marcada (true) o desmarcada (false).

* Se usa cuando el usuario puede seleccionar múltiples opciones simultáneamente.
* Su estado se obtiene con *isSelected()* o mediante *selectedProperty()*.

```
CheckBox aceptar = new CheckBox("Acepto los términos");
if (aceptar.isSelected()) { ... }
```

### RadioButton

* Permite una única selección dentro de un grupo de opciones.
* Solo uno de los botones del grupo puede estar activo.
* Los botones de opción deben agruparse mediante un ToggleGroup.

```
RadioButton hombre = new RadioButton("Hombre");
RadioButton mujer = new RadioButton("Mujer");

ToggleGroup grupo = new ToggleGroup();
hombre.setToggleGroup(grupo);
mujer.setToggleGroup(grupo);
```

### Ejemplo 2

```
package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ControlesSeleccionApp extends Application {

    @Override
    public void start(Stage stage) {
        // --- ListView ---
        Label labelLista = new Label("Lenguajes preferidos:");
        ListView<String> listaLenguajes = new ListView<>();
        listaLenguajes.getItems().addAll("Java", "Python", "Kotlin", "C#", "C++");
        listaLenguajes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listaLenguajes.setPrefHeight(100);

        // --- ComboBox ---
        Label labelCombo = new Label("País de residencia:");
        ComboBox<String> comboPais = new ComboBox<>();
        comboPais.getItems().addAll("España", "Francia", "Italia", "Alemania");
        comboPais.setPromptText("Selecciona un país");

        // --- CheckBox ---
        CheckBox checkSuscripcion = new CheckBox("Suscribirme a las noticias");

        // --- RadioButtons ---
        Label labelGenero = new Label("Género:");
        RadioButton hombre = new RadioButton("Hombre");
        RadioButton mujer = new RadioButton("Mujer");
        RadioButton otro = new RadioButton("Otro");

        ToggleGroup grupoGenero = new ToggleGroup();
        hombre.setToggleGroup(grupoGenero);
        mujer.setToggleGroup(grupoGenero);
        otro.setToggleGroup(grupoGenero);

        HBox boxGenero = new HBox(10, hombre, mujer, otro);

        // --- Botón de acción ---
        Button botonMostrar = new Button("Mostrar selección");
        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setWrapText(true);

        botonMostrar.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();

            sb.append("Lenguajes seleccionados: ")
              .append(listaLenguajes.getSelectionModel().getSelectedItems())
              .append("\n");

            sb.append("País: ")
              .append(comboPais.getValue() != null ? comboPais.getValue() : "Ninguno")
              .append("\n");

            sb.append("Suscripción: ")
              .append(checkSuscripcion.isSelected() ? "Sí" : "No")
              .append("\n");

            Toggle seleccionado = grupoGenero.getSelectedToggle();
            sb.append("Género: ")
              .append(seleccionado != null ? ((RadioButton) seleccionado).getText() : "Sin especificar");

            resultado.setText(sb.toString());
        });

        // --- Layout principal ---
        VBox root = new VBox(10,
                labelLista, listaLenguajes,
                labelCombo, comboPais,
                labelGenero, boxGenero,
                checkSuscripcion,
                botonMostrar, resultado
        );
        root.setPadding(new Insets(15));
        root.setStyle("-fx-font-family: 'Segoe UI';");

        // --- Escena ---
        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Ejemplo de controles de selección en JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

```
	
## Arquitectura de una GUI en JavaFX

JavaFX promueve una estructura muy parecida a **MVC (Modelo–Vista–Controlador)**, aunque en la práctica se aproxima más a **MVVM** (Modelo–Vista–ViewModel) por el uso de **property bindings**.

* **Modelo (Model)**: contiene los **datos** y la **lógica de negocio** (por ejemplo, clases Persona, Producto, etc.).

* **Vista (View)**: es la interfaz gráfica, generalmente definida en un archivo **FXML**.

* **Controlador (Controller)**: maneja la lógica de interacción entre la vista y el modelo. Está vinculado al FXML.

### Controlador en ficheros FXML

Se puede configurar una **clase controladora** para un documento FXML. Una clase Controlador FXML permite enlazar los componentes de la interfaz gráfica declarados en el FXML, haciendo que la clase Controlador actúe como mediador donde delegar cuando se producen eventos en la interfaz.

Hay dos maneras de configurar un controlador para un FXML:

* La primera forma es especificarlo **dentro del archivo** FXML. 
La clase del controlador se especifica en el elemento raíz del archivo FXML utilizando el atributo *fx:controller*.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.control.Button?>

<VBox xmlns:fx="http://javafx.com/fxml" fx:controller="umu.tds.MiControlador" >
    <Button text="Click!"/ onAction="hacerClick()">
    </Button>
</VBox>
```
El atributo contiene el nombre de la clase controladora. Se crea una instancia de esta clase al cargar el archivo FXML. Para que esto funcione, la clase controladora debe tener un *constructor sin argumentos*.


* La segunda forma es configurar una instancia de la clase controladora en la **instancia del FXMLLoader** utilizada para cargar el documento FXML. 
Primero se debe crear una instancia de la clase controlador y luego configurar esa instancia en FXMLLoader.

```xml
MiControlador controller = new MiControlador();
FXMLLoader loader = new FXMLLoader();
loader.setController(controller);
```

### Vinculación de componentes JavaFX a campos del controlador

Se pueden vincular los componentes JavaFX del archivo FXML a campos de la clase controladora. Para vincular un componente JavaFX a un campo de la clase controladora, debe asignar al elemento FXML del componente JavaFX un atributo *fx:id* que contenga como valor el nombre del campo controlador al que se vinculará. 

```java
public class MiControlador {
    public Label label1 = null;
}
```
El valor del atributo *fx:id* tiene el valor *label1* que es el mismo que el nombre del campo en la clase del controlador al que debe estar vinculado.

```xml
<VBox  xmlns:fx="http://javafx.com/fxml" >
    <Label fx:id="label1" text="Line 1"/>
</VBox>
```

### Referenciar métodos en el controlador
Es posible referenciar métodos en la instancia del controlador desde FXML. 

Por ejemplo, se pueden vincular los eventos de un componente de la interfaz gráfica a métodos del controlador. A continuación se vincula el evento *onAction* del botón al método *botonClicked* en la clase del controlador. 

```xml
<VBox xmlns:fx="http://javafx.com/fxml" fx:controller="umu.tds.MiControlador" spacing="20">
<children>
    <Label fx:id="label1" text="Line 1"/>
    <Label fx:id="label2" text="Line 2"/>
    <Button fx:id="button1" text="Click!" onAction="#botonClicked"/>
</children>
</VBox>
```

```java
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MiControlador {

    @FXML
    public void botonClicked(Event e){
        System.out.println("Botón clicked");
    }

}
```

La anotación **@FXML** sobre el método *botonClicked* marca el método como destino para la vinculación con FXML y que el nombre del método *botonClicked* se referencia en el archivo FXML.

Por tanto, y resumiendo:

* **fx:controller** vincula este FXML con su clase controladora.

* **fx:id** identifica los nodos para poder referenciarlos en el controlador.

* **onAction="#nombreDeMetodo"** enlaza eventos a métodos del controlador.

* **@FXML** permite que JavaFX inyecte automáticamente los elementos definidos en el FXML.

* Los **métodos manejadores** (como *botonClicked()*) se enlazan a los eventos declarados en el FXML.

### Obtención de la instancia del controlador desde FXMLLoader

Una vez que la instancia de *FXMLLoader* haya cargado el documento FXML, se puede obtener una referencia a la instancia del controlador mediante el método *getController()* del FXMLLoader (objeto *loader*).

```java
MiControlador controllerRef = loader.getController();
```

## Property Binding en JavaFX

JavaFX define propiedades específicas para los tipos más usados, todas en el paquete *javafx.beans.property*.

| Tipo de valor | Clase Property      | Clase Simple (instanciable) |
| ------------- | ------------------- | --------------------------- |
| `boolean`     | `BooleanProperty`   | `SimpleBooleanProperty`     |
| `int`         | `IntegerProperty`   | `SimpleIntegerProperty`     |
| `long`        | `LongProperty`      | `SimpleLongProperty`        |
| `float`       | `FloatProperty`     | `SimpleFloatProperty`       |
| `double`      | `DoubleProperty`    | `SimpleDoubleProperty`      |
| `String`      | `StringProperty`    | `SimpleStringProperty`      |
| `Object<T>`   | `ObjectProperty<T>` | `SimpleObjectProperty<T>`   |
| `List<T>`     | `ListProperty<T>`   | `SimpleListProperty<T>`     |
| `Map<K,V>`    | `MapProperty<K,V>`  | `SimpleMapProperty<K,V>`    |

**SimpleXxxProperty** es la implementación más usada para inicializar propiedades.

En JavaFX, una *Property* es un objeto observable que encapsula un valor y permite:

* **Observar** sus cambios (listeners).

* **Vincularse** con otras propiedades (bindings).

* **Notificar** automáticamente a la vista cuando su valor cambia.

Para definir en JavaFX una propiedad, en lugar de hacer ```private String nombre;``` se hace:

``` private final StringProperty nombre = new SimpleStringProperty(); ```

Por ejemplo:

```java
public class Persona {
    private final StringProperty nombre = new SimpleStringProperty(this, "nombre", "Sin nombre");

    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty nombreProperty() { return nombre; }
}
```


Esto sigue un patrón estándar:

* **getXxx()** → obtiene el valor actual.
* **setXxx()** → cambia el valor.
* **xxxProperty()** → devuelve el objeto Property observable.

### Binding de Propiedades

Un **binding** (enlace) crea una relación automática entre propiedades:

* Si cambia una propiedad, las enlazadas se actualizan automáticamente.
* Evita tener que escribir código de sincronización manual.

```
Label label = new Label();
TextField textField = new TextField();

label.textProperty().bind(textField.textProperty());
```

El método **bind()** de las propiedades permite crear un binding (enlace) unidireccional. 

En el ejemplo anterior se crea un binding unidireccional de la propiedad *'text' (textProperty)* del elemento *textField* a la propiedad *text* del elemento *label*. Cada vez que el usuario escribe algo en el TextField, el Label se actualiza automáticamente.

Existen dos tipos de binding:

* **Unidireccional** (*bind()*) : La propiedad destino sigue los cambios de la fuente, pero no al revés.
``` label.textProperty().bind(modelo.nombreProperty());```

* **Bidireccional** (*bindBidirectional()*) : Ambas propiedades se mantienen sincronizadas.
``` textField.textProperty().bindBidirectional(modelo.nombreProperty()); ```

Las propiedades de controles en JavaFX son variables miembro de *tipo especial*. 
Se suelen usar para contener propiedades del control, como por ejemplo, la posición X e Y, el ancho y alto, el texto, los elementos secundarios y otras propiedades esenciales de los controles JavaFX. 

En el siguiente ejemplo se muestra una interfaz gráfica que ilustra cómo acceder a las propiedades *widthProperty* y *prefWidthProperty* de un panel de JavaFX.

```java
ReadOnlyDoubleProperty widthProperty = pane.widthProperty();
...
DoubleProperty prefWidthProperty = pane.prefWidthProperty();
...
```
Se pueden asociar observadores de cambios (**addListener**) a las propiedades JavaFX para que otros componentes reciban notificaciones cuando el valor de la propiedad cambie, y se pueden vincular propiedades (**bind**) entre sí para que, cuando el valor de una cambie, también cambie la otra. 
 
En el siguiente ejmeplo, uno de los observadores de cambios se implementa como una **clase Java anónima** y el otro como una **expresión Lambda** de Java. 

```java
widthProperty.addListener( new ChangeListener<Number> (){
      @Override
      public void changed(
        ObservableValue<? extends Number> observableValue,
        Number oldVal, Number newVal) {
          System.out.println("widthProperty changed from "
            + oldVal.doubleValue() + " to " + newVal.doubleValue());
      }
    }); 
...
prefWidthProperty.addListener(
      (ObservableValue<? extends Number> prop,
        Number oldVal, Number newVal) -> {

        System.out.println("prefWidthProperty changed from "
          + oldVal.doubleValue() + " to " + newVal.doubleValue());
    });
```
Se hace uso del patrón Observer implementado con las clases de soporte de Java (*ChangeListener*, *ObservableValue*). 

+ Si se llama a la instrucción *prefWidthProperty.set(123);*, se llamará al detector de cambios *prefWidthProperty*. 
+ Además, cada vez que se redimensiona la interfaz de usuario, también se redimensiona el panel, y se llamará al detector de cambios *widthProperty*. 

Vemos el ejemplo completo a continuación:

```java
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PropertyExample extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) {

    Pane pane = new Pane();

    ReadOnlyDoubleProperty widthProperty = pane.widthProperty();
    widthProperty.addListener( new ChangeListener<Number> (){
      @Override
      public void changed(
        ObservableValue<? extends Number> observableValue,
        Number oldVal, Number newVal) {
          System.out.println("widthProperty changed from "
            + oldVal.doubleValue() + " to " + newVal.doubleValue());
      }
    });


    DoubleProperty prefWidthProperty = pane.prefWidthProperty();
    prefWidthProperty.addListener(
      (ObservableValue<? extends Number> prop,
        Number oldVal, Number newVal) -> {

        System.out.println("prefWidthProperty changed from "
          + oldVal.doubleValue() + " to " + newVal.doubleValue());
    });

    prefWidthProperty.set(123);

    Scene scene = new Scene(pane, 1024, 800, true);
    primaryStage.setScene(scene);
    primaryStage.setTitle("2D Example");

    primaryStage.show();
  }
}
```

## Ejemplo completo en JavaFX
A continuación se proporciona un ejemplo visual completo en JavaFX que combina varios layouts (*BorderPane*, *VBox*, *GridPane*) dentro de un único FXML, con su controlador y clase principal.

El resultado es una ventana con un menú superior, un formulario central y un pie de página con botones de acción. Un encabezado azul con el título “Gestor de Usuarios”. En el centro hay un formulario alineado con tres campos (nombre, correo, edad). Y en el pie, dos botones (“Guardar” y “Limpiar”) alineados a la derecha. El formulario se adapta al redimensionar la ventana gracias a los layouts (VBox y GridPane).

La estructura del proyecto de ejemplo es la siguiente:

```
src/
 └─ com/example/app/
     ├─ MainApp.java
     ├─ MainView.fxml
     ├─ MainController.java
     └─ Usuario.java
```

**Usuario.java** 

El modelo (con propiedades observables). Este modelo usa JavaFX Properties, lo que permite enlazar sus valores a los controles de la vista de forma reactiva (sin necesidad de actualizarlos manualmente).

```
package com.example.app;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Usuario {
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty correo = new SimpleStringProperty();
    private final IntegerProperty edad = new SimpleIntegerProperty();

    // --- Getters y Setters
    public String getNombre() { return nombre.get(); }
    public void setNombre(String value) { nombre.set(value); }
    public StringProperty nombreProperty() { return nombre; }

    public String getCorreo() { return correo.get(); }
    public void setCorreo(String value) { correo.set(value); }
    public StringProperty correoProperty() { return correo; }

    public int getEdad() { return edad.get(); }
    public void setEdad(int value) { edad.set(value); }
    public IntegerProperty edadProperty() { return edad; }

    @Override
    public String toString() {
        return String.format("Usuario[nombre=%s, correo=%s, edad=%d]", getNombre(), getCorreo(), getEdad());
    }
}

```



**MainView.fxml**

La vista (layouts combinados). Un layout principal tipo *BorderPane* con:

* **Top**: barra de menú (HBox)
* **Center**: formulario con GridPane
* **Bottom**: botones con HBox

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.layout.*?>
<?import javafx.scene.control.*?>
<?import javafx.geometry.Insets?>

<BorderPane xmlns="http://javafx.com/javafx"
            xmlns:fx="http://javafx.com/fxml"
            fx:controller="com.example.app.MainController">

    <!-- Cabecera -->
    <top>
        <HBox spacing="10" style="-fx-background-color: #2c3e50; -fx-padding: 10;">
            <Label text="Gestor de Usuarios" style="-fx-text-fill: white; -fx-font-size: 16px;"/>
        </HBox>
    </top>

    <!-- Centro: Formulario -->
    <center>
        <VBox alignment="CENTER" spacing="20" padding="20">
            <Label text="Formulario de registro"
                   style="-fx-font-size: 14px; -fx-font-weight: bold;" />

            <GridPane hgap="10" vgap="10">
                <columnConstraints>
                    <ColumnConstraints halignment="RIGHT" prefWidth="100" />
                    <ColumnConstraints prefWidth="200" />
                </columnConstraints>

                <Label text="Nombre:" GridPane.rowIndex="0" GridPane.columnIndex="0"/>
                <TextField fx:id="nombreField"
                           promptText="Introduce tu nombre"
                           GridPane.rowIndex="0" GridPane.columnIndex="1"/>

                <Label text="Correo:" GridPane.rowIndex="1" GridPane.columnIndex="0"/>
                <TextField fx:id="correoField"
                           promptText="usuario@correo.com"
                           GridPane.rowIndex="1" GridPane.columnIndex="1"/>

                <Label text="Edad:" GridPane.rowIndex="2" GridPane.columnIndex="0"/>
                <TextField fx:id="edadField"
                           promptText="Ej. 30"
                           GridPane.rowIndex="2" GridPane.columnIndex="1"/>
            </GridPane>

            <Label fx:id="mensajeLabel"
                   textFill="green"
                   style="-fx-font-weight: bold;"/>
        </VBox>
    </center>

    <!-- Pie -->
    <bottom>
        <HBox alignment="CENTER_RIGHT" spacing="10" padding="10">
            <Button text="Guardar" onAction="#onGuardar"/>
            <Button text="Limpiar" onAction="#onLimpiar"/>
        </HBox>
    </bottom>

</BorderPane>
```

**MainController.java**

El controlador con bindings bidireccionales y lógica simple para mostrar datos al guardar y limpiar los campos. 

```java
package com.example.app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class MainController {

    @FXML private TextField nombreField;
    @FXML private TextField correoField;
    @FXML private TextField edadField;

    private Usuario usuario = new Usuario();

    @FXML
    public void initialize() {
        // Enlazamos directamente los campos a las propiedades del modelo
        nombreField.textProperty().bindBidirectional(usuario.nombreProperty());
        correoField.textProperty().bindBidirectional(usuario.correoProperty());
        edadField.textProperty().bindBidirectional(usuario.edadProperty());
    }

    @FXML
    private void onGuardar() {
        if (usuario.getNombre().isEmpty() || usuario.getCorreo().isEmpty() || usuario.getEdad().isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor, rellena todos los campos.");
        } else {
            mostrarAlerta("Datos guardados", String.format(
                "Nombre: %s\nCorreo: %s\nEdad: %s",
                usuario.getNombre(), usuario.getCorreo(), usuario.getEdad()
            ));
        }
    }

    @FXML
    private void onLimpiar() {
        usuario.setNombre("");
        usuario.setCorreo("");
        usuario.setEdad("");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
```

**MainApp.java**

Clase principal que carga el FXML y lanza la aplicación.

```java
package com.example.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Scene scene = new Scene(loader.load(), 450, 350);

        stage.setTitle("Ejemplo de Layouts y Property Bindings");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```
La ejecución de la aplicación debe mostrar un formulario de usuario que:

* Al escribir en los campos, el modelo *Usuario* se actualiza automáticamente.
* El botón *“Guardar”* muestra los datos del modelo en un *Alert*.
* El botón *“Limpiar”* borra el modelo y la vista de una sola vez.
