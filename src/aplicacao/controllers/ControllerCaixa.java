package aplicacao.controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import aplicacao.Main;
import aplicacao.FuncaoScrollPane;
import aplicacao.Caixa.DeliveryPane;
import aplicacao.Caixa.Login;
import aplicacao.Caixa.Mesa;
import aplicacao.Caixa.MesasPane;

public class ControllerCaixa {

	private BooleanProperty login = new SimpleBooleanProperty();

	@FXML
	ImageView img;

	@FXML
	private Button btnVoltar;
	@FXML
	ScrollBar scroll;

	@FXML
	VBox vobx2;

	@FXML
	VBox vobx1;
	@FXML
	Tab tab1;

	@FXML
	Tab tab2;
	@FXML
	ImageView img1;
	@FXML
	ImageView img2;

	@FXML
	VBox vbox1;

	@FXML
	VBox vbox2;
	@FXML
	Button btnContas;

	@FXML
	Button btnBancoDeDados;

	private List<Mesa> list = new ArrayList<Mesa>();

	@FXML
	private void initialize() throws RemoteException {
		// TODO problemas com o scroll bar, após o valor ser redefinido para tab
		// delivery o mesmo não redefine de volta para a tab mesas

		btnBancoDeDados.disableProperty().bind(Login.getLogin().not());

		MesasPane.getPane(vbox1);
		DeliveryPane.getPane(vbox2);

		FuncaoScrollPane.organizar(vbox1, scroll);

	}

	@FXML
	public void onBack() {

		Main.loadStage("/aplicacao/FacaSeuPedidoFXML.fxml",
				"FacaSeuPedido");
	}

	@FXML
	public void onLogin() {

		Main.loadStage("/aplicacao/Caixa/User.fxml", "Login");
	}

	@FXML
	public void onDatabase() {
		Main.loadStage("/aplicacao/Caixa/Database.fxml",
				"FacaSeuPedido");
	}

	@FXML
	public void onMesas() {
		try {
			FuncaoScrollPane.organizar(vbox1, scroll);
		} catch (Exception e) {
		}
	}

	@FXML
	public void onDeliverys() {
		FuncaoScrollPane.organizar(vbox2, scroll);

	}

}
