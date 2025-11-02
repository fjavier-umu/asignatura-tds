package es.um.tds.JavaFsShowcase;

import es.um.tds.JavaFsShowcase.contenedores.ContenedoresShowcase;
import es.um.tds.JavaFsShowcase.contenedores.GraficasShowcase;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class ShowcaseMain extends Application {

	private BorderPane root;
	private ContenedoresShowcase contenedoresShowcase;
	private GraficasShowcase graficasShowcase;

	@Override
	public void start(Stage stage) {
		root = new BorderPane();
		contenedoresShowcase = new ContenedoresShowcase();
		graficasShowcase = new GraficasShowcase();
		
		// Crear menú
		MenuBar menuBar = crearMenuBar(stage);
		root.setTop(menuBar);
		//Asigno la vista principal
		asignaVista(generaVistaInicio());

		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.setTitle("TDS: JavaFx showcase");
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

	/**
	 * Creacion del a barra de menu para el stage
	 * 
	 * @param stage
	 * @return
	 */
	private MenuBar crearMenuBar(Stage stage) {
		MenuBar menuBar = new MenuBar();

		Menu menuShowcase = new Menu("Showcase");

		Menu menuAyuda = new Menu("Ayuda");

		MenuItem menuPaneles = new MenuItem("Paneles");
		MenuItem menuGraficas = new MenuItem("Graficas");
		menuPaneles.setOnAction(evento -> asignaVista(contenedoresShowcase.generaPanel()));
		menuGraficas.setOnAction(evento -> asignaVista(graficasShowcase.generaPanel()));
		menuShowcase.getItems().add(menuPaneles);
		menuShowcase.getItems().add(menuGraficas);

		MenuItem menuDetalle = new MenuItem("Sobre showcase");
		menuAyuda.getItems().add(menuDetalle);

		menuBar.getMenus().addAll(menuShowcase, menuAyuda);

		return menuBar;
	}

	/**
	 * Asigna una vista pasada como parametro a la parte central del panel
	 */
	private void asignaVista(Node vista) {
		root.setCenter(vista);
	}

	private VBox generaVistaInicio() {
		VBox vista = new VBox(15);
		// Alineo al centro y pongo el color de fondo verde
		vista.setStyle("-fx-alignment: center; -fx-background-color: lightgreen;");
		// Aniado titulo
		Text titulo = new Text("Bienvenido al showcase de JavaFx");
		titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		vista.getChildren().addAll(titulo);

		Image image = new Image(getClass().getResourceAsStream("/img/showcase.png"));

		// Creo el elemento JavaFx
		ImageView imageView = new ImageView(image);
		vista.getChildren().add(imageView);

		// Hacer que se expanda completamente
		vista.setFillWidth(true);
		return vista;
	}

}