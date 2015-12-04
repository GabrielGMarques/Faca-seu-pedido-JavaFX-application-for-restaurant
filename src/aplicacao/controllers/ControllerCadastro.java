package aplicacao.controllers;

import java.rmi.RemoteException;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import aplicacao.BancoConnection;
import aplicacao.Main;
import aplicacao.Caixa.BancoDeDados.BancoDeDados;
import aplicacao.Caixa.BancoDeDados.DatabaseException;
import aplicacao.Caixa.BancoDeDados.Itens.Bebida;
import aplicacao.Caixa.BancoDeDados.Itens.OutroItem;
import aplicacao.Caixa.BancoDeDados.Itens.PratoComposto;
import aplicacao.Caixa.BancoDeDados.Itens.PratoSimples;

public class ControllerCadastro {

	@FXML
	TextField txtNumItens;
	@FXML
	TextField txtPreco;
	@FXML
	TextField txtNome;
	@FXML
	TextArea txtDescricao;
	@FXML
	Label numItens;

	@FXML
	ComboBox comboBox;

	@FXML
	public void initialize() {
		comboBox.setItems(FXCollections.observableArrayList(Arrays.asList(
				"Bebida", "Prato Composto", "Prato Simples", "Outro")));
		txtNumItens.disableProperty().bind(
				comboBox.valueProperty().isEqualTo("Prato Composto").not());
		numItens.disableProperty().bind(
				comboBox.valueProperty().isEqualTo("Prato Composto").not());
	}

	@FXML
	public void onCancelar() {

		Main.loadStage("/aplicacao/Caixa/Database.fxml",
				"Banco de Dados");
	}

	@FXML
	public void onSalvar() {
		// TODO limpar os campos quando salvar, e encerrar a implementação da
		// classe
		boolean noContains = true;
		if (verificar()) {
			try {
				BancoDeDados database = BancoConnection.getConnection();

				if (comboBox.getValue().equals("Bebida")) {
					noContains = database.addItem(new Bebida(txtNome.getText(),
							Double.parseDouble(txtPreco.getText()),
							txtDescricao.getText()));

				} else if (comboBox.getValue().equals("Prato Composto")) {
					noContains = database.addItem(new PratoComposto(txtNome
							.getText(), Double.parseDouble(txtPreco.getText()),
							txtDescricao.getText(), Integer
									.parseInt(txtNumItens.getText())));

				} else if (comboBox.getValue().equals("Prato Simples")) {
					noContains = database.addItem(new PratoSimples(txtNome
							.getText(), Double.parseDouble(txtPreco.getText()),
							txtDescricao.getText()));

				} else if (comboBox.getValue().equals("Outro")) {
					noContains = database.addItem(new OutroItem(txtNome
							.getText(), Double.parseDouble(txtPreco.getText()),
							txtDescricao.getText()));

				}

			} catch (DatabaseException | NumberFormatException
					| RemoteException e) {

				noContains = false;
				System.out.println(e.getMessage());
			}
			if (noContains) {
				Main.loadStage("/aplicacao/Caixa/Database.fxml",
						"Banco de Dados");
			} else {
				Main.setDialog(AlertType.ERROR, "Erro",
						"Erro ao cadastrar item", "Item já está cadastrado");
			}
		} else {
			Main
					.setDialog(
							AlertType.ERROR,
							"Erro",
							"Erro ao cadastrar item",
							"Preencha todos os campos para prosseguir e verifique se o valor do produto é valido");

		}
	}

	private boolean verificar() {
		// Verifica se todos os itens estão preenchidos e adequadamente
		// implementados

		try {
			Double.parseDouble(txtPreco.getText());
		} catch (Exception e) {
			return false;
		}
		if (comboBox.getValue() != null) {
			if (!comboBox.getValue().equals("Prato Composto")) {
				if (!txtNome.getText().isEmpty()
						&& !txtPreco.getText().isEmpty()
						&& !txtDescricao.getText().isEmpty()) {
					return true;
				}
			} else {
				if (!txtNome.getText().isEmpty()
						&& !txtPreco.getText().isEmpty()
						&& !txtDescricao.getText().isEmpty()
						&& !txtNumItens.getText().isEmpty()) {
					System.out.println("Prato composto");
					return true;
				}
			}
		}
		return false;
	}

}
