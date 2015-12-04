package aplicacao.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import aplicacao.Main;

public class ControllerFacaSeuPedido {

	@FXML
	Button btnAtendimento;
	@FXML
	Button btnCaixa;
	@FXML
	Button btnCozinha;
	@FXML
	Button ono;

	@FXML
	private void initialize() {

	}

	@FXML
	public void onAtendimento() {

		Main.loadStage("/aplicacao/atendente/AtendimentoFXML.fxml",
				"Atendimento");
	}

	@FXML
	public void onCaixa() {

		Main.loadStage("/aplicacao/Caixa/CaixaFXML.fxml", "Caixa");
	}

	@FXML
	public void onCozinha() {

		Main.loadStage("/aplicacao/cozinheiro/CozinhaFXML.fxml", "Cozinha");
	}

}
