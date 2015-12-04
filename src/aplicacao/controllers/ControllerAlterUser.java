package aplicacao.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import aplicacao.Main;
import aplicacao.Caixa.BancoDeDados.LoginXML;
import aplicacao.Caixa.BancoDeDados.UserDatabase;
import aplicacao.Caixa.BancoDeDados.UserDatabase.UserForList;

public class ControllerAlterUser {
	@FXML
	TextField textNome;
	@FXML
	PasswordField textSenha;
	@FXML
	PasswordField textConfirmarSenha;
	@FXML
	Button btnCancelar;
	@FXML
	Button btnSalvar;
	@FXML
	Button btnRemover;
	@FXML
	Button btnAdcionar;
	@FXML
	TableView table;

	@FXML
	public void initialize() {

		table.setItems(FXCollections.observableList(LoginXML.getUsers()));
		// Ablita o botão só quando todos os campos estiverem preenchidos e
		// quando a senha estiver de acordo com a confirmação da senha
		btnAdcionar
				.disableProperty()
				.bind(textConfirmarSenha
						.textProperty()
						.isEmpty()
						.or(textNome
								.textProperty()
								.isEmpty()
								.or(textSenha
										.textProperty()
										.isEmpty()
										.or(textSenha
												.textProperty()
												.isNotEqualTo(
														textConfirmarSenha
																.textProperty())))));
		btnRemover.disableProperty().bind(
				table.focusedProperty().not());
	}

	@FXML
	public void onCancelar() {
		Main.loadStage("/aplicacao/Caixa/User.fxml", "Login");
	}

	@FXML
	public void onSalvar() {
		LoginXML.savePermissions(table.getItems());

		Main.loadStage("/aplicacao/Caixa/User.fxml", "Login");

	}

	@FXML
	public void onRemover() {
		table.getItems().remove(table.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void onAdcionar() {
		UserForList user = UserDatabase.newUserForList();
		user.setName(textNome.getText());
		user.setSenha(textSenha.getText());
		user.setPermissao("permissao" + (table.getItems().size() + 1));
		table.getItems().add(user);

		textNome.setText("");
		textSenha.setText("");
		textConfirmarSenha.setText("");
	}
}
