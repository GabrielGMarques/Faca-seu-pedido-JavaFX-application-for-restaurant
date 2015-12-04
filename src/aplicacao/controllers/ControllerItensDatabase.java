package aplicacao.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import aplicacao.FuncaoScrollPane;
import aplicacao.Main;
import aplicacao.Caixa.BancoDeDados.Itens.Categoria;
import aplicacao.Caixa.BancoDeDados.Itens.ItensList;
import javafx.scene.control.ScrollBar;

public class ControllerItensDatabase {

	@FXML
	VBox vboxBebidas;
	@FXML
	VBox vboxPratosSimples;
	@FXML
	VBox vboxOutros;
	@FXML
	VBox vboxPratosCompostos;
	@FXML
	ScrollBar scroll;

	private ItensList listBebidas = new ItensList(Categoria.BEBIDA);
	private ItensList listPratosCompostos = new ItensList(
			Categoria.PRATO_COMPOSTO);
	private ItensList listPratosSimples = new ItensList(Categoria.PRATO_SIMPLES);
	private ItensList listOutros = new ItensList(Categoria.OUTRO);

	@FXML
	public void initialize() {

		FuncaoScrollPane.organizar(vboxBebidas, scroll);
		listBebidas.getItensList(vboxBebidas);
		listPratosCompostos.getItensList(vboxPratosCompostos);
		listPratosSimples.getItensList(vboxPratosSimples);
		listOutros.getItensList(vboxOutros);

	}

	@FXML
	public void onSalvar() {
		listBebidas.salvarAlteracoes();
		listOutros.salvarAlteracoes();
		listPratosCompostos.salvarAlteracoes();
		listPratosSimples.salvarAlteracoes();

		Main.loadStage("/aplicacao/Caixa/Database.fxml", "Banco de Dados");

	}

	@FXML
	public void onCancelar() {
		Main.loadStage("/aplicacao/Caixa/Database.fxml", "Banco de Dados");
	}

	@FXML
	public void onOutros() {

		FuncaoScrollPane.organizar(vboxOutros, scroll);
	}

	@FXML
	public void onPratosS() {
		FuncaoScrollPane.organizar(vboxPratosSimples, scroll);

	}

	@FXML
	public void onPratosC() {
		FuncaoScrollPane.organizar(vboxPratosCompostos, scroll);

	}

	@FXML
	public void onBebidas() {
		try {
			FuncaoScrollPane.organizar(vboxBebidas, scroll);
		} catch (Exception e) {

		}
	}

}
