package aplicacao.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import aplicacao.Main;
import aplicacao.Caixa.Login;
import aplicacao.Caixa.BancoDeDados.LoginXML;
import aplicacao.Caixa.BancoDeDados.UserDatabase;

public class ControllerUser {

	@FXML
	TextField txtNome;
	@FXML
	PasswordField txtSenha;

	@FXML
	GridPane interfaceUser;
	@FXML
	Label isAdmin;
	@FXML
	Label userName;

	@FXML
	Button btnAlterSenha;
	@FXML
	Button btnAlterPermissoes;

	@FXML
	Button btnSair;
	private UserDatabase userDatabase = new UserDatabase();

	@FXML
	GridPane interfaceLogin;
	@FXML
	Button btnVoltar;
	@FXML
	Button btnEntrar;
	@FXML
	BorderPane BorderPane;

	@FXML
	public void initialize() {

		BorderPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {

					onLogin();

				}
			}
		});
		interfaceUser.disableProperty().bind(Login.getLogin().not());
		interfaceLogin.disableProperty().bind(Login.getLogin());
		btnAlterPermissoes.disableProperty().bind(
				userDatabase.adminProperty().not());
		isAdmin.disableProperty().bind(userDatabase.adminProperty().not());
		userName.textProperty().bind(userDatabase.nomeProperty());
	}

	@FXML
	public void onLogin() {
		// TODO fazer o binding para ver se o administrador está logado

		boolean login = Login.getLogin(txtNome.getText(),
				txtSenha.getText(), userDatabase);

		if (!login) {
			Main.setDialog(AlertType.ERROR, "Error", "Erro ao fazer login",
					"Verifique seu nome e senha");
		}

	}

	@FXML
	public void onBack() {
		Main.loadStage("/aplicacao/Caixa/CaixaFXML.fxml", "Caixa");
	}

	@FXML
	public void onAlterSenha() {
		Main.loadStage("/aplicacao/Caixa/user/AlterPass.fxml", "Alterar Senha");

	}

	@FXML
	public void onAlterPermissoes() {
		Main.loadStage("/aplicacao/Caixa/user/AlterUser.fxml",
				"Alterar Permissões");
	}

	@FXML
	public void onSair() {

		txtNome.setText("");
		txtSenha.setText("");

		userDatabase.clear();
		Login.setLogin(false);
	}

}
