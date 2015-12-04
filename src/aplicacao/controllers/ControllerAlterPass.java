package aplicacao.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import aplicacao.Main;
import aplicacao.Caixa.BancoDeDados.LoginXML;
import aplicacao.Caixa.BancoDeDados.UserDatabase;

public class ControllerAlterPass {

	@FXML
	PasswordField textSenhaAtual;
	@FXML
	PasswordField textNewSenha;
	@FXML
	PasswordField textConfirmaSenha;
	@FXML
	Button btnCancelar;
	@FXML
	Button btnSalvar;

	@FXML
	public void initialize() {
		// Verifica se os campos estão preenchidos e os campos de senha e
		// confirmar senha estão com seus textos iguais antes de abilitar o
		// botão
		btnSalvar
				.disableProperty()
				.bind(textSenhaAtual
						.textProperty()
						.isEmpty()
						.or(textNewSenha
								.textProperty()
								.isEmpty()
								.or(textConfirmaSenha
										.textProperty()
										.isEmpty()
										.or(textNewSenha
												.textProperty()
												.isNotEqualTo(
														textConfirmaSenha
																.textProperty())))));
	}

	@FXML
	public void onCancelar() {
		Main.loadStage("/aplicacao/Caixa/User.fxml", "Login");

	}

	@FXML
	public void onSalvar() {
		UserDatabase user = new UserDatabase();
		boolean change = LoginXML.newPassWord(user.nomeProperty().getValue(),
				textSenhaAtual.getText(), textNewSenha.getText());
		if (!change) {
			Main.setDialog(AlertType.ERROR, "Erro", "Erro ao mudar a senha",
					"Verifique se a senha atual é correspondente");
		} else {
			Main.loadStage("/aplicacao/Caixa/User.fxml", "Login");
		}
	}
}
