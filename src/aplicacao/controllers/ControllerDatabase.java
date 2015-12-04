package aplicacao.controllers;

import javafx.fxml.FXML;
import aplicacao.Main;

public class ControllerDatabase {

	@FXML
	public void initialize() {

	}

	@FXML
	public void onCadastrar() {
		Main.loadStage("/aplicacao/Caixa/BancoDeDados/Cadastro.fxml",
				"Cadastrar item");

	}

	@FXML
	public void onAlterar() {

		Main.loadStage(
				"/aplicacao/Caixa/BancoDeDados/Itens/AlterItensDatabase.fxml",
				"Alterar Itens");
	}

	@FXML
	public void onBack() {

		Main.loadStage("/aplicacao/Caixa/CaixaFXML.fxml", "Caixa");
	}

}
