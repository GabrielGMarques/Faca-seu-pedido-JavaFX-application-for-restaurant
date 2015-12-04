package aplicacao;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import aplicacao.Caixa.BancoDeDados.ServidorRMI;

public class Main extends Application {

	public static void main(String[] args) {

		launch(args);

	}

	private static Class clas = null;

	/**
	 * This is main class
	 */
	public Main() {
		this.clas = getClass();

	}

	private static Stage stage = null;
	private static Scene scene = null;
	private ServidorRMI rmi = null;
	private static boolean thread = true;

	public void start(Stage stage) {
		this.stage = stage;

		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(
					"FacaSeuPedidoFXML.fxml"));

		} catch (IOException e) {
			e.printStackTrace();

		}

		// Check if the server is connect
		try {
			rmi = new ServidorRMI();
			boolean iniciou = rmi.aniciarServidor();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Inflates the scene
		scene = new Scene(root, 550, 550);
		stage.setMinHeight(550);
		stage.setMinWidth(550);
		stage.setMaxHeight(550);
		stage.setMaxWidth(550);

		// Get the css file
		scene.getStylesheets().add(
				getClass().getResource("Layout.css").toExternalForm());

		stage.setScene(scene);
		stage.setTitle("Faca seu pedido");
		stage.show();
		stage.onCloseRequestProperty().set(e -> {
			encerrarServidor();
		});
	}

	public static boolean isThread() {
		return thread;
	}

	public static void setThread(boolean thread) {
		Main.thread = thread;
	}

	/**
	 * Inflates the file fxml
	 * 
	 * @param file
	 * @param nameFrame
	 */
	public static void loadStage(String file, String nameFrame) {
		Parent root = null;
		try {
			root = FXMLLoader.load(clas.getResource(file));

		} catch (Exception e) {

			e.printStackTrace();
		}

		scene.setRoot(root);
		stage.setTitle(nameFrame);
		stage.setScene(scene);
	}

	/**
	 * Inflates the scene of alert
	 * 
	 * @param type
	 * @param title
	 * @param headerText
	 * @param contentText
	 */
	public static void setDialog(AlertType type, String title,
			String headerText, String contentText) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();

	}

	/**
	 * The server shuts
	 */

	public void encerrarServidor() {

		rmi.closeServer();
		Main.setThread(false);
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
