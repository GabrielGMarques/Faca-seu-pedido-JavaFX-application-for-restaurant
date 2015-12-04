package aplicacao.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import aplicacao.Main;
import aplicacao.FuncaoScrollPane;
import aplicacao.cozinheiro.PedidoPane;

public class ControllerCozinha {

	@FXML
	ScrollBar scrollBar;

	@FXML
	VBox tablePedidos;

	@FXML
	private void initialize() {
		System.out.println("Iniciou");
		FuncaoScrollPane.organizar(tablePedidos, scrollBar);

		PedidoPane.getPedido(tablePedidos);

	}

	@FXML
	public void onBack() {

		Main.loadStage("/aplicacao/FacaSeuPedidoFXML.fxml", "Faca Seu Pedido");
	}
}
