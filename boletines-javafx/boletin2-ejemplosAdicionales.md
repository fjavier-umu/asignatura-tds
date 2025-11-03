# Prácticas de JavaFX - Sesión 2 - Ejemplos adicionales

## 'Showcase' de contenedores en JavaFX

El siguiente código crea una ventana con múltiples contenedores y layouts para que se pueda visualizar su funcionamiento

```java
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ContenedoresShowcase extends Application{

	@Override
    public void start(Stage primaryStage) {
		
		// -- TabPane --
		TabPane root = new TabPane();
		root.getTabs().addAll(
				new Tab("HBox", creaHBoxDemo()),
				new Tab("VBox", creaVBoxDemo()),
				new Tab("BorderPane", creaBorderPaneDemo()),
				new Tab("GridPane", creaGridPaneDemo()),
				new Tab("StackPane", creaStackPaneDemo()),
				new Tab("FlowPane", creaFlowPaneDemo()),
				new Tab("TilePane", creaTilePaneDemo()),
				new Tab("AchorPane", creaAnchorPaneDemo()),
				new Tab("SplitPane", creaSplitPaneDemo()),
				new Tab("ScrollPane", creaScrollPaneDemo())
		);
		root.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); // Para que no se puedan cerrar tabs
		// Crear la escena con el contenedor
        Scene scene = new Scene(root, 800, 400);

        // Configurar la ventana principal (Stage)
        primaryStage.setTitle("Ejemplos de contenedores en JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
		
		
	}
	
	private HBox creaHBoxDemo() {
		HBox hbox = new HBox(10); // 10 px de espacio
		hbox.getChildren().addAll(new Button("A"), new Button("B"), new Button("C"));
		return hbox;
	}
	
	private VBox creaVBoxDemo() {
		VBox vbox = new VBox(5);
		vbox.getChildren().addAll(new Label("Nombre:"), new TextField(), new Button("Aceptar"));
		return vbox;
	}
	
	private BorderPane creaBorderPaneDemo() {
		BorderPane bpane = new BorderPane();
		bpane.setPadding(new Insets(10));
		bpane.setTop(new Label("Parte de arriba del panel (top)"));
		bpane.setLeft(new Label("Parte \n iquierda (left) \n del panel"));
		bpane.setCenter(new TextArea("Esta es la parte del centro (center)"));
		bpane.setRight(new Label("Parte \n derecha (right) \n del panel"));
		bpane.setBottom(new Button("Este botón está en la parte de abajo (bottom)"));
		return bpane;
	}
	
	private GridPane creaGridPaneDemo() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		grid.add(new Label("Usuario:"), 0, 0);
		grid.add(new TextField(), 1, 0);
		grid.add(new Label("Contraseña:"), 0, 1);
		grid.add(new PasswordField(), 1, 1);
		return grid;
	}
	
	private StackPane creaStackPaneDemo() {
		StackPane stack = new StackPane();
		stack.getChildren().addAll(new Rectangle(200, 100, Color.LIGHTGRAY), new Label("Encima"));
		return stack;
	}
	
	private FlowPane creaFlowPaneDemo() {
		FlowPane flow = new FlowPane();
		flow.getChildren().addAll(new Button("Uno"), new Button("Dos"), new Button("Tres"), new Button("Cuatro"),new Button("Ciiiiiiiiiiiiiinco"), new Button("Seis"));
		return flow;
	}
	
	private TilePane creaTilePaneDemo() {
		TilePane tile = new TilePane();
		tile.getChildren().addAll(
			new Button("1"), new Button("2"), new Button("3"), new Button("4"),new Button("Cinco"), new Button("Seis")
		);		
		return tile;
	}
	
	private AnchorPane creaAnchorPaneDemo() {
		AnchorPane anchor = new AnchorPane();
		Button boton = new Button("Guardar");
		AnchorPane.setTopAnchor(boton, 10.0);
		AnchorPane.setRightAnchor(boton, 20.0);
		anchor.getChildren().add(boton);		
		return anchor;
	}
	
	private SplitPane creaSplitPaneDemo() {
		Label labelIzq = new Label("(O_o)");
		labelIzq.setFont(Font.font(40));
				
		Label labelDcha = new Label("(0\u203F0)");
		labelDcha.setFont(Font.font(40));

		SplitPane spPane = new SplitPane();
		spPane.getItems().addAll(new BorderPane(labelIzq), new BorderPane(labelDcha));
		
		return spPane;
	}
	
	private ScrollPane creaScrollPaneDemo() {
		Label label = new Label("(0\u203F0)");
		label.setFont(Font.font(400));
		label.setWrapText(false);
				
		ScrollPane scPane = new ScrollPane(label);
		return scPane;
	}
	
    public static void main(String[] args) {
        launch(args);
    }
}

```

## Ejemplo de uso de Properties con bindings y listeners

Este pequeño ejemplo muestra los tipos de binding unidireccional y bidireccional entre propiedades de JavaFX, asi como el uso de listeners para observar sus cambios.

```java
package org.umu.tds.pruebaFx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EjemploProperties extends Application{

	TextField origenBindTF         = new TextField();
	TextField destinoBindTF        = new TextField();
	TextField bindBidireccional1TF = new TextField();
	TextField bindBidireccional2TF = new TextField();
	TextArea  salidaListenersTA    = new TextArea();
	ToggleButton bindBT            = new ToggleButton();
	Label unidireccionalL          = new Label();
	Label bidireccionalL           = new Label();
	
	@Override
    public void start(Stage primaryStage) throws Exception {
		
		VBox panelGeneral = new VBox(20);
		GridPane panelCampos = new GridPane();
		panelGeneral.setPadding(new Insets(10,10,10,10));
		panelCampos.setHgap(10);
		panelCampos.setVgap(10);
		
		unidireccionalL.setText("----- binding unidireccional ---->");
		bidireccionalL.setText("<---- binding bidireccional ----->");
		
		panelCampos.add(origenBindTF, 0, 0);
		panelCampos.add(unidireccionalL, 1, 0);
		panelCampos.add(destinoBindTF, 2, 0);
		
		panelCampos.add(bindBidireccional1TF, 0, 1);
		panelCampos.add(bidireccionalL, 1, 1);
		panelCampos.add(bindBidireccional2TF, 2, 1);
		
		bindBT.setText("Bind/Unbind");
		bindBT.setOnAction(e -> {cambiarBindings();});
		
		panelGeneral.getChildren().addAll(panelCampos, salidaListenersTA, bindBT);
				
		// Change listeners
		origenBindTF.textProperty().addListener(
				(prop, oldVal, newVal) -> {salidaListenersTA.appendText("origenBindTF cambiado de "+oldVal+" a "+newVal+"\n");}
		);
		destinoBindTF.textProperty().addListener(
				(prop, oldVal, newVal) -> {salidaListenersTA.appendText("destinoBindTF cambiado de "+oldVal+" a "+newVal+"\n");}
		);
		bindBidireccional1TF.textProperty().addListener(
				(prop, oldVal, newVal) -> {salidaListenersTA.appendText("bindBidireccional1TF cambiado de "+oldVal+" a "+newVal+"\n");}
		);
		bindBidireccional2TF.textProperty().addListener(
				(prop, oldVal, newVal) -> {salidaListenersTA.appendText("bindBidireccional2TF cambiado de "+oldVal+" a "+newVal+"\n");}
		);
		
		// Otro ejemplo de binding: 
		// Estado visible de las etiquetas enlazado con el estado del botón
		// De esta forma ahorramos implementar varios listeners
		unidireccionalL.visibleProperty().bind(bindBT.selectedProperty());
		bidireccionalL.visibleProperty().bind(bindBT.selectedProperty());

		// Crear la escena con el contenedor
        Scene scene = new Scene(panelGeneral, 550, 200);
        // Configurar la ventana principal (Stage)
        primaryStage.setTitle("Ejemplo sencillo de JavaFX properties");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	private void cambiarBindings() {
		if (bindBT.isSelected()) {
			// Binding unidirectional
			destinoBindTF.textProperty().bind(origenBindTF.textProperty());
			
			// Binding bidirectional
			bindBidireccional1TF.textProperty().bindBidirectional(bindBidireccional2TF.textProperty());
			
			// Avisamos en la salida 
			salidaListenersTA.appendText("---- BINDINGS ACTIVOS ---\n");
		} else {
			// Quitamos los bindings
			destinoBindTF.textProperty().unbind();
			bindBidireccional1TF.textProperty().unbindBidirectional(bindBidireccional2TF.textProperty());

			// Avisamos en la salida 			
			salidaListenersTA.appendText("---- BINDINGS DESACTIVADOS ---\n");
		}		
	}
	
    public static void main(String[] args) {
        launch(args);
    }
}
```
