package es.um.tds.JavaFsShowcase.contenedores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

public class GraficasShowcase {

	public TabPane generaPanel() {

		TabPane root = new TabPane();

		root.getTabs().add(new Tab("Barras", crearBarChart()));
		root.getTabs().add(new Tab("Líneas", crearLineChart()));
		root.getTabs().add(new Tab("Tarta", crearPieChart()));
		root.getTabs().add(new Tab("Area", crearAreaChart()));
		root.getTabs().add(new Tab("Dispersion", crearScatterChart()));
		root.getTabs().add(new Tab("Burbujas", crearBubbleChart()));

		// Evita que las pestañas se cierren
		root.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); // Para que no se puedan cerrar tabs

		return root;
	}

	// Grafico de barras
	private static BarChart<String, Number> crearBarChart() {
		// Ejes X e Y, hay que definirlos y del tipo que son
		// Defino el eje X como categorias (texto)
		CategoryAxis x = new CategoryAxis();
		// Defino el eje Y como numero
		NumberAxis y = new NumberAxis();

		// Grafico de barras
		BarChart<String, Number> chart = new BarChart<>(x, y);

		chart.setTitle("Ventas por Producto");
		// Defino la serie 2025
		XYChart.Series<String, Number> serieTablets = new XYChart.Series<>();
		serieTablets.setName("Tablets");
		// Indico los valores para la serie
		serieTablets.getData().add(new Data<>("1ºtimestre", 23));
		serieTablets.getData().add(new Data<>("2ºtimestre", 14));
		serieTablets.getData().add(new Data<>("3ºtimestre", 15));
		serieTablets.getData().add(new Data<>("4ºtimestre", 19));

		XYChart.Series<String, Number> seriePortatiles = new XYChart.Series<>();
		seriePortatiles.setName("Portatiles");
		// Indico los valores para la serie
		seriePortatiles.getData().add(new Data<>("1ºtimestre", 13));
		seriePortatiles.getData().add(new Data<>("2ºtimestre", 34));
		seriePortatiles.getData().add(new Data<>("3ºtimestre", 25));
		seriePortatiles.getData().add(new Data<>("4ºtimestre", 12));

		// Aniado las series al grafico
		chart.getData().add(seriePortatiles);
		chart.getData().add(serieTablets);

		return chart;
	}

	// Grafico de lineas
	private static LineChart<String, Number> crearLineChart() {
		// Ejes X e Y, hay que definirlos y del tipo que son
		// Defino el eje X como categorias (texto)
		CategoryAxis x = new CategoryAxis();
		// Defino el eje Y como numero
		NumberAxis y = new NumberAxis();

		LineChart<String, Number> chart = new LineChart<>(x, y);
		chart.setTitle("Ventas por Producto");
		// Defino la serie 2025
		XYChart.Series<String, Number> serieTablets = new XYChart.Series<>();
		serieTablets.setName("Tablets");
		// Indico los valores para la serie
		serieTablets.getData().add(new Data<>("1ºtimestre", 23));
		serieTablets.getData().add(new Data<>("2ºtimestre", 14));
		serieTablets.getData().add(new Data<>("3ºtimestre", 15));
		serieTablets.getData().add(new Data<>("4ºtimestre", 19));

		XYChart.Series<String, Number> seriePortatiles = new XYChart.Series<>();
		seriePortatiles.setName("Portatiles");
		// Indico los valores para la serie
		seriePortatiles.getData().add(new Data<>("1ºtimestre", 13));
		seriePortatiles.getData().add(new Data<>("2ºtimestre", 34));
		seriePortatiles.getData().add(new Data<>("3ºtimestre", 25));
		seriePortatiles.getData().add(new Data<>("4ºtimestre", 12));

		// Aniado las series al grafico
		chart.getData().add(seriePortatiles);
		chart.getData().add(serieTablets);
		return chart;
	}

	// Grafico de tarta
	private static PieChart crearPieChart() {

		// Defino las series de datos
		PieChart.Data chrome = new PieChart.Data("Chrome", 64);
		PieChart.Data edge = new PieChart.Data("Edge", 19);
		PieChart.Data firefox = new PieChart.Data("Firefox", 10);
		PieChart.Data safari = new PieChart.Data("Safari", 7);

		// Creamos el conjunto de datos
		ObservableList<PieChart.Data> data = FXCollections.observableArrayList(chrome, edge, firefox, safari);
		// Cargamos el grafico de tarta
		PieChart chart = new PieChart(data);
		chart.setTitle("Uso de Navegadores");
		return chart;
	}

	// Grafico de area
	private static AreaChart<String, Number> crearAreaChart() {
		// Ejes X e Y, hay que definirlos y del tipo que son
		// Defino el eje X como categorias (texto)
		CategoryAxis x = new CategoryAxis();
		// Defino el eje Y como numero
		NumberAxis y = new NumberAxis();

		AreaChart<String, Number> chart = new AreaChart<>(x, y);
		chart.setTitle("Producción anual");

		// Creamos las series
		XYChart.Series<String, Number> molina = new XYChart.Series<>();
		molina.setName("Fabrica de Molina");
		molina.getData().add(new XYChart.Data<>("2019", 50));
		molina.getData().add(new XYChart.Data<>("2020", 80));
		molina.getData().add(new XYChart.Data<>("2021", 65));
		molina.getData().add(new XYChart.Data<>("2022", 90));

		XYChart.Series<String, Number> rSur = new XYChart.Series<>();
		rSur.setName("Fabrica de Ronda Sur");
		rSur.getData().add(new XYChart.Data<>("2019", 30));
		rSur.getData().add(new XYChart.Data<>("2020", 100));
		rSur.getData().add(new XYChart.Data<>("2021", 35));
		rSur.getData().add(new XYChart.Data<>("2022", 70));

		chart.getData().add(molina);
		chart.getData().add(rSur);
		return chart;
	}

	// Graficon de dispersion
	private static ScatterChart<Number, Number> crearScatterChart() {
		// Ejes numericos en ambos casos
		// Establecemos rango de valores en los ejes
		NumberAxis ejeX = new NumberAxis(0, 10, 1);
		ejeX.setLabel("Horas de estudio");
		NumberAxis ejeY = new NumberAxis(0, 10, 1);
		ejeY.setLabel("Rendimiento en examen");

		// Crear gráfico
		ScatterChart<Number, Number> chart = new ScatterChart<>(ejeX, ejeY);
		chart.setTitle("Relación entre estudio y resultado");

		// Alumnos que estudian por el dia
		XYChart.Series<Number, Number> diurno = new XYChart.Series<>();
		diurno.setName("Grupo diurno");
		diurno.getData().add(new XYChart.Data<>(1, 2));
		diurno.getData().add(new XYChart.Data<>(2, 3));
		diurno.getData().add(new XYChart.Data<>(3, 4));
		diurno.getData().add(new XYChart.Data<>(4, 6));
		diurno.getData().add(new XYChart.Data<>(5, 7));

		// Alumnos que estudian por la noche
		XYChart.Series<Number, Number> nocturno = new XYChart.Series<>();
		nocturno.setName("Grupo nocturno");
		nocturno.getData().add(new XYChart.Data<>(1, 1.2));
		nocturno.getData().add(new XYChart.Data<>(2, 2));
		nocturno.getData().add(new XYChart.Data<>(3, 5.8));
		nocturno.getData().add(new XYChart.Data<>(4, 5.5));
		nocturno.getData().add(new XYChart.Data<>(5, 8));

		// Aniadimos las series
		chart.getData().add(diurno);
		chart.getData().add(nocturno);
		return chart;
	}

	// Grafico de burbujas
	private static BubbleChart<Number, Number> crearBubbleChart() {
		// Ejes numericos en ambos casos
		// Establecemos rango de valores en los ejes
		NumberAxis ejeX = new NumberAxis(0, 200, 50);
		ejeX.setLabel("Peso promedio (kg)");

		NumberAxis ejeY = new NumberAxis(0, 80, 10);
		ejeY.setLabel("Esperanza de vida (anios)");

		BubbleChart<Number, Number> chart = new BubbleChart<>(ejeX, ejeY);
		chart.setTitle("Animales del Zoologico");

		// Mamiferos
		XYChart.Series<Number, Number> mamiferos = new XYChart.Series<>();
		mamiferos.setName("Mamiferos");
		//El tercer valor equivaldria al numero de animales en ese rango
		mamiferos.getData().add(new XYChart.Data<>(90, 70,10));
		mamiferos.getData().add(new XYChart.Data<>(190, 25,7));
		mamiferos.getData().add(new XYChart.Data<>(120, 20,3));
		mamiferos.getData().add(new XYChart.Data<>(80, 15,9));

		// Aves
		XYChart.Series<Number, Number> aves = new XYChart.Series<>();
		aves.setName("Aves");
		aves.getData().add(new XYChart.Data<>(3, 50,2)); 
		aves.getData().add(new XYChart.Data<>(1.5, 60,10));
		aves.getData().add(new XYChart.Data<>(0.5, 10,5));

		// Reptiles
		XYChart.Series<Number, Number> reptiles = new XYChart.Series<>();
		reptiles.setName("Reptiles");
		reptiles.getData().add(new XYChart.Data<>(180, 70,7)); 
		reptiles.getData().add(new XYChart.Data<>(90, 50,5)); 
		reptiles.getData().add(new XYChart.Data<>(30, 60,3)); 

		// Aniadimos las series
		chart.getData().add(mamiferos);
		chart.getData().add(aves);
		chart.getData().add(reptiles);
		return chart;
	}

}
